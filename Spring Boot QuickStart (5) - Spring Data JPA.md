# Spring Boot QuickStart (5) - Spring Data JPA

接(4) - Database 系列.

Java Persistence API，可以理解就是 Java 一个持久化标准或规范，Spring Data JPA 是对它的实现。并且提供多个 JPA 厂商适配，如 Hibernate、Apache 的 OpenJpa、Eclipse的EclipseLink等。

spring-boot-starter-data-jpa 默认使用的是 Hibernate 实现。

<!-- more -->

直接引入 JPA 和 MySQL 依赖：

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

application.properties 开启 SQL 调试：

```
spring.jpa.database=mysql
spring.jpa.show-sql=true
```

在 SpringBoot + Spring Data Jpa 中，不需要额外的配置什么，只需要编写实体类（Entity）与数据访问接口（Repository）就能开箱即用，Spring Data JPA 能基于接口中的方法规范命名自动的帮你生成实现（根据方法命名生成实现，是不是很牛逼？）

Spring Data JPA 还默认提供了几个常用的Repository接口：

* Repository： 仅仅是一个标识，没有任何方法，方便 Spring 自动扫描识别
* CrudRepository： 继承 Repository，实现了一组 CRUD 相关的方法
* PagingAndSortingRepository： 继承 CrudRepository，实现了一组分页排序相关的方法
* JpaRepository： 继承 PagingAndSortingRepository，实现一组JPA规范相关的方法

推荐教程：Spring Data JPA实战入门训练 https://course.tianmaying.com/spring-data-jpa

## Entity 实体和 Respository 接口

根据 user 表结构，我们定义好 User 实体类与 UserRespository 接口类。

这里，还自定义了一个 @Query 接口，为了体验下自定义查询。因为使用了 lombok，所以实体类看起来很干净。稍微注意一点，一般实体类我们可以定义为包装类型，因为比如 Integer 可以是 null，而 int 就不可以。所以需要自己好好把握。

User.java

```
@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    @JsonProperty(value = "email")
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonIgnore
    private String salt;

    @Column(nullable = true)
    private Date birthday;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp access;

    @Column(nullable = true)
    @JsonFormat(pattern="HH:mm:ss")
    private Time accessTime;

    @Column(nullable = false)
    private Integer state;

    @Column(nullable = false, insertable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @Column(nullable = false, insertable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp updated;
}
```

* @Data 是 lombok 的注解，自动生成Getter，Setter，toString，构造函数等
* @Entity 注解 Spring Data JPA 实体类
* @Table 注解表相关选项，如表别名等，当你的表(实体类)名是 MySQL 关键词时可能会中枪，通过 @Table(name = "`order`") 解决
* @Id 注解表主键
* @GeneratedValue 注解字段为自动生成，如 MySQL AUTO_INCREMENT
* @Column 注解字段属性，如是否允许为空，是否唯一，是否进行插入和更新
* @Transient 标识该字段并非数据库字段的实体映射
* @DynamicUpdate 注解动态生成UPDATE SQL语句，同理还有 @DynamicInsert
* @Generated 数据库的自动维护字段，插入不回写，之前通过@PrePersist，现在这个注解可以解决，原理是又回查了一次
* @JsonProperty JSON 注解，比如配置别名
* @JsonIgnore JSON 注解，在 JSON 时忽略
* @JsonFormat JSON 注解，在 JSON 时转换格式
* @JsonProperty JSON 注解，在 JSON 时转换属性名
* 对于JPA而言，字段名 accessTime 默认对应的是 access_time，当然可以通过Column自定义。

UserRepository.java

```
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User SET state = ?2 WHERE id = ?1 ")
    Integer saveState(Long id, Integer state);
}
```

* @Transactional 用来标识事务，一般修改、删除会用到， @Modifying 标识这是个修改、删除的Query
* @Param 标注在参数上，可用于标识参数式绑定（不使用 ?1 而使用 :param）

好了，接下来我们就可以进行单表的增、删、改、查分页排序操作了：

```
@Autowired
private UserRepository userRepository;
    
User user = new User();
userRepository.save(user); // 插入或保存
userRepository.saveFlush(user); // 保存并刷新
userRepository.exists(1) // 主键查询是否存在
userRepository.findById(1); // 主键查询单条
userRepository.delete(1); // 主键删除
userRepository.findByUsername("a@b.com"); // 查询单条
userRepository.findAll(pageable); // 带排序和分页的查询列表
userRepository.saveState(1, 0); // 更新单个字段
```

通常，exist()，delete()之类的方法，我们可能直接会操作 UserRepository，但是一般情况下，在 UserRepository 上面还会提供一个 UserService 来进行一系列的操作（比如数据校验，逻辑判断之类）

## 分页和排序

PagingAndSortingRepository 和 JpaRepository 接口都具有分页和排序的功能。因为后者继承自前者。比如下面这个方法：

```
Page<T> findAll(Pageable var1);
```

Pageable 是Spring Data库中定义的一个接口，该接口是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息（例如pageNumber、pageSize等），这样，Jpa就能够通过pageable参数来组装一个带分页信息的SQL语句。

Page 类也是Spring Data提供的一个接口，该接口表示一部分数据的集合以及其相关的下一部分数据、数据总数等相关信息，通过该接口，我们可以得到数据的总体信息（数据总数、总页数...）以及当前数据的信息（当前数据的集合、当前页数等）

Pageable 只是一个抽象的接口。可以通过两种途径生成 Pageable 对象：

1. 通过参数，自己接收参数，自己构造生成 Pageable 对象

```
@RequestMapping(value = "", method = RequestMethod.GET)
public Object page(@RequestParam(name = "page", required = false) Integer page,
                  @RequestParam(name="size", required = false) Integer size) {
                  
   Sort sort = new Sort(Sort.Direction.DESC, "id");
   Pageable pageable = new PageRequest(page, size, sort);

   Page<User> users = userRepository.findAll(pageable);

   return this.responseData(users);
}
```

这种方式你可以灵活的定义传参。

2. 通过 @PageableDefault 注解，会把参数自动注入成 Pageable 对象，默认是三个参数值：

page=，第几页，从0开始，默认为第0页
size=，每一页的大小
sort=，排序相关的信息，例如sort=firstname&sort=lastname,desc

```
@RequestMapping(value = "/search", method = RequestMethod.GET)
public Object search(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
   Page<User> users = userRepository.findAll(pageable);

   return this.responseData(users);
}
```

看起来，这种方式更优雅一些。

# 关联关系

Spring Data JPA 的关联关系定义上，感觉并不是很灵活，姿势也比较难找。

视频教程：http://www.jikexueyuan.com/course/807_4.html

## OneToOne 一对一

一对一的关系，拿 user，user_detail 来说，一般应用起来，有以下几种情况：

* 主键直接关联：user(id, xx)；user_profile(id, xx) 或 user(id, xx)，user_profile(user_id, xx) 其中 id, userid 为主键
* 主表含外键的关联：user(id, role_id, xx)；role(id, xx) 。 其中 id 为自增主键
* 附表含外键的关联：user(id, xx)；user_detail(id, user_id, xx) 。其中 id 为自增主键

主表含外键的关联：用户->角色是一对一，而角色->用户是多对一，而大部分情况，我们是通过 user 表来查询某个角色的列表，而通过 role 来查询某个角色的列表可能性很小。

附表表含外键的关联：其实和主表含外键的关联完全相反，关联的定义也是相反的。

### 主键直接关联

示例：user(id, xx)，user_profile(id, xx) 或 user(id, xx)，user_profile(user_id, xx)

单向关联，直接在 User 上定义 @OneToOne 与 @PrimaryKeyJoinColumn 即可完成

PrimaryKeyJoinColumn 默认使用当前表 @Id 关联附表 @Id 的主键

```
@Entity
@Data
public class User {
...
	@OneToOne(cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private UserProfile profile;
...
}

// 获取的user，会包含detail属性
userRepository.findById(userId);
```

双向关联，除了要定义 User 的 @OneToOne，还需要定义 UserDetail 的 @OneToOne，用 mappedBy 指示 User 表的属性名。

```
@Entity
@Data
public class UserProfile {
...
	@OneToOne(mappedBy = "detail")
    private User user;
...
}
```

出问题了，双向关联，涉及到一个循环引用无限递归的问题，这个问题会发生在 toString、 JSON 转换上。可能这只是个基础问题，但对于我这个入门汉，抓瞎了好长时间。

解决办法：
1. 分别给User、UserProfile 的关联属性加上：@JsonManagedReference、@JsonBackReference注解，解决 JSON 问题
2. 给 UserProfile 实体类加上 @ToString(exclude = "user") 注解，解决 toString 的问题。

所以 UserProfile 最终造型应该是这样的：

```
@Entity
@Data
@ToString(exclude = "user")
public class UserProfile {
...

    @OneToOne(mappedBy = "detail")
    @JsonBackReference
    private User user;
}

// 现在可以进行双向查询了
userRepository.findById(userId);
userProfileRepository.findById(userId);
userProfileRepository.getUser();
```

### 主表含外键

示例：user(id, role_id, xx)，role(id, xx)

使用 @JoinColumn 注解即可完成，默认使用的外键是（属性名+下划线+id)。可以通过 name= 属性重新自定义

默认关联附表的标注主键 @Id，可以通过 referencedColumnName= 属性重新自定义。

```
@Entity
@Data
public class User {
...
    // 属性名为role，所以 @JoinColumn 会默认外键是 role_id
	@OneToOne
    @JoinColumn
    private Role role;
...
}
```

对于 user->role 的表关联需求，我们不需要定义 OneToOne 反向关系，并且 role->user 其实是个一对多关系。

### 附表含外键

示例：user(id, xx)；user_detail(id, user_id, xx)

这种情况一般也会经常出现，它可以保证每个表都有一个自增主键的id，这种情况一般在一对多的场景中。

因为外键在附表上，所以需要反过来，在 User 上定义 mapped。

如果是双向关联，同样需要加上忽略 toString()，JSON 的注解

```
@Entity
@Data
public class User {
...
	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private UserDetail detail;
...
}

@Entity
@Data
@ToString(exclude = "user")
public class UserDetail {
...
	@OneToOne
    @JoinColumn
    @JsonBackReference
    private User user;
...
}

User user1 = userRepository.findOne(userId);

// 给 UserDetail 定义一个独立的 findByUserId 接口，这样可以通过操作 UserDetail 反向获取到 user 的数据
userDetail userdetail = userDetailRepository.findByUserId(userId);
User user2 = userdetail.getUser();
```



实际上，在上面的例子里面，考虑实际的场景，几乎不需要定义 OneToOne 的反向关联（伪需求），这样就不用解决循环引用的问题了。这里只是意淫，不是吗？

现在有个问题出现了，这种情况下（附表含外键），我如何定义 User->UserDetail 的单向关系呢？

貌似没有找到好的解决办法，所以设计时OneToOne尽量避免，采用主键直接关联

## 关联关系：一对多

示例：user(id)，order(id, user_id)

接着上面的例子，Role -> User 实际上是个一对多的关系。但我们一般不会这么做。直接通过 User 就可以查询嘛。所以这里演示另一个例子。

User->Order 是一对多，Order->User 是多对一，定义 Order 实体，注意 @Table 注解，因为 order 是 MySQL 关键词（此处中枪）

在 User 中定义 @OneToMany，因为是一对多，所以返回的是 List<Order>，并且一般设置为 LAZY，在这里 JoinColumn 中的 name 属性表示外键字段名。

```
@Entity
@Data
public class User {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private List<Order> orders;
}
```

在 Order 中不能定义 user_id 这个字段，否则会报错，报错类似：

`Table [order] contains physical column name [user_id] referred to by multiple physical column names: [user_id]`

```
@Entity
@Data
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
}
```

测试一下：

```
user = userService.findById(userId);
List<Order> orders = user.getOrders();
}
```

再看看双向关联，也就是 @ManyToOne，稍作调整

```
User 实体类
@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
private List<Order> orders;

Order 实体类
@ManyToOne
private User user;
``` 

再测试一下：

```
Order order = orderRepository.findOne(orderId);
if (order != null) {
	User user = order.getUser();
	return this.responseData(user);
}
```

### 总结

想想实际场景，我们不太需要定义 User->Order 这种关联，因为用户可能有很多订单，这个量是无可预测的。这时候这种关联查询，不能分页，没有意义（也可能是我姿势不对）。

如果是有限的 XToMany 关联，是有意义的。比如配置管理。一个应用拥有有限的多项配置？

Order->User 这种关联是有意义的。拿到一个 order_id 去反查用户信息。但一般我们也不会直接放到关联查询里面这么做，而是这样直接查询出来：

`SELECT * FROM order WHERE user_id = ?`


## 关联关系：多对多

Order <-> Product 是多对多的关系，关联表是 order_product，

Order 实体配置 @ManyToMany 属性，不需要定义关联表的 OrderProduct 实体类，

```
// @JoinTable 实际可以省略，因为使用的是默认配置
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
   name = "order_product",
   joinColumns = @JoinColumn(name = "order_id"),
   inverseJoinColumns = @JoinColumn(name = "product_id"))
@JsonManagedReference
private List<Product> products;
```

这样就定义了单向关联，双向关联类似在 Product 实体配置：

```
@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
@JsonIgnore
private List<Order> orders;
```

好了，这样就OK了，实际按照上面的解释，Product -> Order 是不太有意义的。


# 属性参数

@OneToOne的属性：

* cascade 属性表示级联操作策略，有 CascadeType.ALL 等值。
* fetch 属性表示实体的加载方式，有 FetchType.LAZY 和 FetchType.EAGER 两种取值，默认值为 EAGER

拿OneToOne来说，如果是 EAGER 方式，那么会产生一个连接查询，如果是 LAZY 方式，则是两个查询。并且第二个查询会在用的时候才会触发（仅仅.getXXX是不够的）。

# 级联

经过一番研究，这部分暂时我还没搞明白正确姿势，玩不转，但是级联删除是可以通过 cascade = CascadeType.REMOVE 来配置的，试想：

user, user_profile, user_detail 表，他们是一个整体，在 user 表删除的时候肯定要同步删除 user_profile, user_detail表

实际上应用层实现了 MySQL 外键特性

比如为 profile 表设置级联删除：

```
@OneToOne(cascade = CascadeType.REMOVE)
@PrimaryKeyJoinColumn
private UserProfile profile;
```

定义在关联关系上的 cascade 参数可以设置级联的相关东西。


# 复杂的查询

# 总结

一句话：使用 JPA 定义少量的关联，谨慎使用。

# 问题总结

## 数据库默认值字段，插入后不会回写。Entity not return default value after insert

一般关键表会记录创建、更新时间，满足基本审计需求，以前我喜欢使用 MySQL 默认值特性，这样应用层就可以不用管他们了，如：

```
`created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
```

在实体中，我们要忽略插入和更新对他们的操作。

```
@Column(nullable = false, insertable = false, updatable = false)
@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
private Timestamp created;

@Column(nullable = false, insertable = false, updatable = false)
@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
private Timestamp updated;
```

看起来不错哦，工作正常，但是：

Spring Data Jpa 在 save() 完成以后，对于这种数据库默认插入的值，拿不到回写的数据啊，无论我尝试网上的方法使用 saveAndFlush() 还是手动 flush() 都是扯淡。

这个坑，我踩了好久，到现在，依然不知道这种情况怎么解决。

临时解决方案：

抛弃数据库默认值特性，在实体类借助 @PrePersist、@PreUpdate 手动实现，如果有多个表，遵循同一规范，可以搞个基类，虽然不太爽，但是能正常工作。

```
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
	@Column(nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp updated;

	@PrePersist
	public void basePrePersist() {
	   long timestamp = new java.util.Date().getTime();
	   created = new Timestamp(timestamp);
	   updated = new Timestamp(timestamp);
	}
	
	@PreUpdate
	public void basePreUpdate() {
	   updated = new Timestamp(new java.util.Date().getTime());
	}
}

```

其中 @MappedSuperclass 表示可以将超类的JPA注解传递给子类

> @update 20180604 目前这个问题已经找到了解决方案，可以通过 @Generated 注解 *

```
@Generated(GenerationTime.INSERT)
@Column(nullable = false, insertable = false, updatable = false)
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime created;

@Generated(GenerationTime.ALWAYS)
@Column(nullable = false, insertable = false, updatable = false)
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime updated;
```

观察下实际的运行原理，JPA 是在插入后又查询了这两个字段达到回写数据的目的。


## OneToOne 关联关系，指定 FetchType.LAZY，JSON 时会出错，得不到数据

原因大概是，JSON序列化的时候，数据还没有fetch到，出错信息如下：

```
Could not write JSON: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer
```

解决方法：

application.properties 增加配置项：

```
spring.jackson.serialization.fail-on-empty-beans=false
```

然而你会发现最终的 JSON 多出来两个key，分别是handler、hibernateLazyInitializer

所以还需要在实体类上增加注解：

```
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
```
搞定!

这可能是个Bug，可能最近已经修复了，因为 XToOne 现在默认是 FetchType.EAGER，但是改变了查询方式（一条SQL查询转换为多条SQL查询）

## 关于循环引用和 Lazy Fetch

接上个问题，这里要思考一个问题场景：

1. Lazy Fetch 在 JSON 序列化实体类时失效 

我们定义了一个 User 实体类，这个实体类有几个关联，比如 OneToOne和 OneToMany，并且设置了Lazy，我们执行了 findById 查询并返回结果，在 RestController 的时候，会默认执行 Jackson 的序列化JSON 操作。

因为序列化会涉及到实体类关联对象的获取，会触发所有的关联关系。生成一大堆的查询 SQL， 这样 LAZY 就失去意义了啊，比如我只想要 User 单表的基本信息怎么办？

stackoverflow 可以搜到了好多类似问题，我目前还没找到正确的姿势。

可以想象的是，不应当将实体类直接返回给客户端，应该再定义一个返回数据的DTO，将实体类的数据复制到DTO，然后返回并JSON。然而这样好蛋疼，随便一个项目你至少需要定义实体类，输入参数的DTO，输出参数的DTO。

2. 循环引用

我们虽然通过 @JsonBackReference 和 JsonManagedReference 来解决。但是有时候，对于两个 OneToOne 实体，我们都需要 JSON 序列化怎么办？如 User 与 UserDetail

另一个办法，给实体类加上 @JsonIdentityInfo：

```
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="@id")
```

这样好处是显而易见的，只需要在实体类上注解一下即可。

猜测原理是引入了id，检测了主键是否一致，决定是否引用下去。如 User->UserDetail->User。

所以他还会多一次查询，并且关联数据上会多一个关联关系的 @id 的字段，还是比较怪异，并且也解决不了循环 toString() 的循环引用问题



