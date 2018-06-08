# Spring Boot QuickStart (6) - MyBatis

接(4) - Database 系列.

MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以对配置和原生Map使用简单的 XML 或注解，将接口和 Java 的 POJOs映射成数据库中的记录。

官网：http://www.mybatis.org/

<!-- more -->

传统的 MyBatis 使用需要一个 XML 或 Java 配置，而 mybatis-spring-boot-starter 解决了自动配置的问题

并且可以在 application.properties 中定义一些配置参数，比如 mybatis.config-location 定义一个 XML 配置文件的路径

直接引入依赖：

```
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.3.0</version>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

与 JPA 不同的是，在 SpringBoot + MyBatis 中，除了需要编写实体类（Entity），还需要写数据访问接口（Mapper）

早期的 MyBatis 只能通过 XML 来进行 Mapper 映射，MyBatis 3 开始，Mapper 可以通过注解、XML 以及注解与XML混合定义。

其实就是需要手写一些 SQL 完成接口的部分，好处是这样显的更灵活一些，因为 SQL 大家都懂，并且让程序员更接近底层一些

缺点是面对基本的单表查询以及未来数据库表的变更，增加了不少的工作量。

推荐教程：https://course.tianmaying.com/spring-mvc-mybatis-qa

## Entity 实体与 Mapper 接口

同样使用 lombok 可以很轻松的定义很简单的实体类（略），定义 Mapper。

通常 MyBatis 与 Spring 的整合需要一些配置，而在Spring Boot 中 mybatis-spring-boot-starter 把这些消除了，可以在 application.properties 修改一些默认的配置。

定义 Mapper 只需要一个 @Mapper 注解，也可以在入口处引入 @MapperScan("com.niuchaoqun.springboot.mapper") 扫描包。

先来个快速的体验，完全使用注解进行单表的基本操作：

mybatis 表结构 (id, name, birthday, sex, access, access_time, created, updated, state)

```
@Mapper
@Repository
public interface MybatisMapper {
    @Select("SELECT * FROM mybatis WHERE id = #{id}")
    Mybatis findById(long id);

    @Select("SELECT * FROM mybatis WHERE name = #{useranme}")
    Mybatis findByName(String username);

    @Select("SELECT * FROM mybatis")
    List<Mybatis> findAll();

    @Insert({
            "INSERT INTO mybatis",
            "(name, birthday, sex, access, access_time, state)",
            "VALUES",
            "(#{name}, #{birthday}, #{sex}, #{access}, #{access_time}, #{state})"
    })
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(Mybatis mybatis);

    @Delete("DELETE FROM mybatis WHERE id = #{id}")
    int deleteById(long id);
}
```

@Mapper 注解表示这是一个 Mapper，SpringBoot 可以自动扫描生成实例。类似 JPA 的 Repository。
@Repository 注解仅仅是为了提示 IDEA 在自动注入的时候不会报错
@Options 属性配置了一些参数，这里是保证插入的主键能回写。

然后我们就可以像 JPA 一样使用了：
 
```
@Autowired
MybatisMapper mybatisMapper;
 
mybatisMapper.findById(1);
mybatisMapper.findAll();
mybatisMapper.findByName("");
mybatisMapper.delete(1);
mybatisMapper.insert(mybatis);
```

然而我们注意到，默认情况下 access_time 在查询结果中不能自动的映射到实体，因为我们的 Mybatis 实体类中使用的是驼峰式 accessTime

解决这个问题大概有以下方式：


1. 修改 `mybatis.configuration.map-underscore-to-camel-case` 为 true，(但是无法解决字段名完全异构的情况)
2. 在实体类中直接定义成 access_time （不够优雅）
3. 在 Mapper 中定义 @Results 结果集映射（这有点扯，它肯定不是用来干这个，因为可能有很多个 SELECT 语句，会累死）
4. 使用 XML 配置的 ResultMap 来定义

```
@Select("SELECT * FROM mybatis WHERE id = #{id}")
@Results({
        @Result(column="access_time", property="accessTime"),
})
Mybatis findById(long id); 
```



### 问题

1. 与 JPA 同样的问题，由数据库自己维护的 created 字段插入后数据不能回写

貌似没有什么好办法，只能再查一次

2. insertSelective，updateSelective

也就是选择性插入与更新操作，上面的注解示例，不能很好的实现 ，一般需要这么做：

定义一个 MybatisSqlProvider：

```
public class MybatisSqlProvider {
    public String updateSelectiveById(Mybatis record) {
        SQL sql = new SQL();
        sql.UPDATE("mybatis");

        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday}");
        }

        if (record.getBirthday() != null) {
            sql.SET("sex = #{sex}");
        }

        if (record.getName() != null) {
            sql.SET("name = #{name}");
        }

        sql.WHERE("id = #{id}");

        return sql.toString();
    }
}
```

然后再在 Mapper 上使用 @UpdateProvider 定义：

```
@UpdateProvider(type = MybatisSqlProvider.class, method = "updateSelectiveById")
int updateSelectiveById(Mybatis mybatis);
```

## XML 配置

除了使用注解以外，还可以通过 XML 定义数据库相关操作，这样 Mapper 中只留下接口部分，在 SpringBoot 中 XML 文件默认是放到 resources 目录下的。

一般项目会采用 XML 的方式，因为可以定义 ResultMap （可以实现字段名异构映射，还可以定义关联关系）

可以相对方便的实现 insertSelective，updateSelective

由于 XML 存放的是 SQL 逻辑，所以可以方便的做 SQL review

在 applications.properties 增加配置项，配置 XML 路径，以及别名实体类路径：

```
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.niuchaoqun.springboot.entity
```

UserMapper:

```
@Mapper
public interface MybatisMapper {
    User findById(long id);
    User findByName(String name);
    List<User> findAll();
    int insert(Mybatis mybatis);
    int delete(long id);
}
```

UserMapper.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.niuchaoqun.springboot.mapper.MybatisMapper">
    <resultMap id="BaseResultMap" type="Mybatis">
        <id property="id" column="id" />
    </resultMap>

    <select id="findById" parameterType="long" resultMap="BaseResultMap">
        SELECT * FROM mybatis WHERE id = #{id}
    </select>

    <select id="findAll" resultMap="BaseResultMap">
      SELECT * FROM mybatis
    </select>

    <select id="findByName" parameterType="string" resultMap="BaseResultMap">
        SELECT * FROM mybatis WHERE name = #{name}
    </select>
    
    <insert id="insert" parameterType="Mybatis" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
      INSERT INTO mybatis
      (name, birthday, sex, access, access_time, state)
      VALUES
      (#{name}, #{birthday}, #{sex}, #{access}, #{accessTime}, #{state})
    </insert>

    <delete id="deleteById" parameterType="long">
      DELETE FROM mybatis WHERE id = #{id}
    </delete>

    <update id="update" parameterType="Mybatis">
        UPDATE mybatis
        <set>
            <if test="birthday != null">birthday = #{birthday},</if>
            <if test="name != null">name = #{name},</if>
            <if test="sex != null">sex = #{sex},</if>
        </set>
        WHERE id = #{id}
    </update>
</mapper>
```

## 等一下
 
看到这里，感觉上使用起来还不如 JPA 爽，并且你需要熟悉很多的配置，无论是注解还是 XML 映射文件。

丝毫提不起再接着搞下去的兴趣，光玩这个 MyBatis 配置，然后做个 CURD，估计都能玩一上午，并且一旦修改表结构，表字段名，可以想象有多心痛，一定是我姿势不对。

开始找轮子。

## MyBatis Generator 插件

这是个 Maven 插件，大概功能就是通过一定的前期配置，帮你生成 MyBatis 需要的实体类、Mapper、或 Mapper with XML，另外还可以生成一些常用的查询接口。

官网：http://www.mybatis.org/generator/running/runningWithMaven.html

在 pom.xml 插件中引入:

```
<plugin>
	<groupId>org.mybatis.generator</groupId>
	<artifactId>mybatis-generator-maven-plugin</artifactId>
	<version>1.3.5</version>
	<configuration>
		<overwrite>true</overwrite>
	</configuration>
</plugin>
```

插件默认会加载 resources/generatorConfig.xml 配置文件，关于此配置文件可以参考这个地址的详细解释：

https://github.com/yyqian/spring-boot-mybatis-generator/blob/master/src/main/resources/generatorConfig-fullAndWithComments.xml

大概就是配置了一些，是否生成注释啊，生成哪些表啊，是否生成 example 示例啊等等。留意一个配置项，<javaClientGenerator> 可以制定生成模式：注解版，XML 版，注解XML混合版。

可以再增加一个 resources/generatorConfig.properties 进行一些变量的配置：

```
mbg.type=MIXEDMAPPER
mbg.jdbc.url=jdbc:mysql://localhost:3306/springboot
mbg.jdbc.username=root
mbg.jdbc.password=123123
mbg.jdbc.driver-class-name=com.mysql.jdbc.Driver
mbg.jdbc.driver-location=/usr/local/server/maven/repo/mysql/mysql-connector-java/5.1.42/mysql-connector-java-5.1.42.jar
mbg.target-package.mapper=com.niuchaoqun.springboot.mapper
mbg.target-package.entity=com.niuchaoqun.springboot.entity
mbg.target-project.java=src/main/java
mbg.target-project.resources=src/main/resources
```

最后，通过 Maven 执行生成：

```
mvn mybatis-generator:generate
```

生成的代码，同时也是一个很好的 MyBatis 学习示例。

* ANNOTATEDMAPPER 纯注解版，一个接口 Mapper（包含基本的注解实现），一个接口实现 SqlProvider（复杂的实现）
* XMLMAPPER 纯XML版，一个接口 Mapper（只有接口），一个XML映射文件（所有的实现）
* MIXEDMAPPER 混合版，一个接口 Mapper（包含基本的注解实现），一个 XML 映射文件（复杂的实现）

为了学习，我们还是采用混合方式好了，默认还会生成了一些 CURD 接口

```
int deleteByPrimaryKey(Long id);
int insert(User record);
int insertSelective(User record);
User selectByPrimaryKey(Long id);
int updateByPrimaryKey(User record);
```

其中xml映射文件，需要指定生成在resources目录，SpringBoot 才可以自动装载。

这个插件可以减少一些工作量，但是想要完全满足我们的需求，仍然需要大量的配置和修改。

比如默认生成的id是 Integer，而我们数据库中设置的是无符号的int（应该是Long），再比如对于与一切时间，默认均生成为 java.util.Date 类型。

并且冗余信息特别多，所以搞搞快速的测试还可以，用起来差强人意啊。

## MyBatis 通用 Mapper 与 PageHelper 分页插件

文档：https://mapperhelper.github.io/docs/

文档：https://pagehelper.github.io/

### 通用 Mapper

通用Mapper，已经默认内置了一些单表的增删改查操作，对于基础的需求，不需要手写接口和XML了。而分页插件提供了强大的分页功能。

pom.xml 引入：

```
<dependency>
  <groupId>tk.mybatis</groupId>
  <artifactId>mapper-spring-boot-starter</artifactId>
  <version>1.1.1</version>
</dependency>
<dependency>
  <groupId>com.github.pagehelper</groupId>
  <artifactId>pagehelper-spring-boot-starter</artifactId>
  <version>1.1.2</version>
</dependency>
```

application.yml 增加一些配置：

```
mapper:
  mappers: com.niuchaoqun.springboot.core.CustomMapper
  not-empty: false
  identity: MYSQL
pagehelper:
  helperDialect: mysql
  reasonable: true
  supportMethodsArguments: true
```

其中 com.niuchaoqun.springboot.core.CustomMapper 是自己根据提供的通用 Mapper 自由组合自定义的。

现在 UserMapper 只剩下一个定义：

```
public interface UserMapper extends CustomMapper<User> {
}
```

UserMapper.xml 也很干净了，

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.niuchaoqun.springboot.mapper.UserMapper">
</mapper>
```

它提供了几乎和 MyBatis Generator 几乎相同的 API（但是并没有生成一坨内容），又扩展了一些API。并且它还自动扩展了 MyBatis Generator 用来自动生成实体类等等。测试一下：

```
User user = userMapper.selectByPrimaryKey(1);

// 条件查询
Condition condition = new Condition(User.class);
condition.createCriteria().andEqualTo("username", email);

List<User> users = userMapper.selectByCondition(condition);
```

### PageHelper 分页

使用起来还是比较简单的，主要有两种姿势。

1. 每次查询之前使用：

```
// page = 1 为起始页，PageInfo用来包装分页信息
PageHelper.startPage(page, size);
List<User> users = userMapper.selectAll();
PageInfo pageUsers = new PageInfo(users);
```
	
2. 接口定义是使用系统配置的分页参数

```
// pageNum 与 pageSize，会进行自动识别，也可以是包含这两个参数的对象
List<User> selectBySex(@Param("sex") String sex,
                             @Param("pageNum") int pageNum,
                             @Param("pageSize") int pageSize);

// 直接调用
List<User> users2 = userMapper.selectBySex("female", page, size);
PageInfo pageInfo2 = new PageInfo(users2);                                                        
```

## 关联关系



## 其他


### IDEA MyBatis Plugin

一个 MyBatis 收费 IDEA 插件，官网售价 40 美刀，目的是简化 Mapper 接口与 XML 的书写，有兴趣可以关注。

https://www.codesmagic.com/mybatis

### MyBatis-Plus 

闲逛了一下，发现了这么一个项目 MyBatis-Plus：

http://mp.baomidou.com/




 

