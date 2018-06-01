package com.niuchaoqun.springboot.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
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
 */

@Data
@Entity
@DynamicUpdate
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private LocalDate birthday;

    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime access;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime accessTime;

    @Generated(GenerationTime.INSERT)
    @Column(nullable = false, insertable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Generated(GenerationTime.ALWAYS)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    private Integer state;

    /**
     * 当前表 role_id 已经做了关联，所以需要定义 insert,update 为 false，否则会报列重复错误
     *
     * @Column(updatable = false, insertable = false)
     * private Short role_id;
     *
     */

    /**
     * 一对一关联(A)
     * <p>
     * user(id, role_id)，role(id)
     * user 表有 role_id 字段，关联 role 表 id 字段
     * <p>
     * JoinColumn：
     * 因为属性名为 role，所以 @JoinColumn 会默认使用当前 role_id 关联另一个表注解为 @Id 的主键 (如下方的属性可省略)
     * 如果不是默认这种约定，则需要通过 name 与 referencedColumnName 来指定关联关系，比如：
     * user(id, r_id)，role(id, r_id)
     */
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    /**
     * 一对一关联(B)
     * <p>
     * user(id)，user_detail(id, user_id)
     * user_detail 表有 user_id 字段，关联 user 表 id 字段
     * <p>
     * 这种情况，一般可以保证每个表都有一个自增主键，具体表现实际上就是上面的例子反过来
     * 然而 JPA 没办法实现 user -> user_detail 这种单向一对一关联（没找到姿势）
     * 只能进行双向关联，或者只在附表 user_detail 上定义单向，后续只通过 user_detail -> user 来进行操作，但这样很怪异
     * <p>
     * 双向关联以后，user_detail 是维护方，user 是被维护方，
     * <p>
     * 双向关联涉及到 JSON、toString() 的循环引用的问题，需要特殊处理
     * 在没找到解决办法的时候，只能避免这样设计表结构
     * <p>
     * cascade 属性表示级联操作，CascadeType.REMOVE 为级联删除
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private UserDetail detail;

    /**
     * 一对一关联(C)
     * <p>
     * user(id)， user_profile(user_id)
     * 两个表直接主键关联
     * <p>
     * PrimaryKeyJoinColumn：
     * 目前测试下来感觉等同于 JoinColumn(name="当前主键") 默认关联另一个表注解为 @Id 的主键
     * <p>
     * 关于 FetchType：
     * 所有的 XXXToOne 默认值是 FAGER，所有的 XXXToMany 默认值是 LAZY，所以这里设置 LAZY 不会延迟查询
     * 但是，测试会改变具体的查询方式，由组合查询->单条查询
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private UserProfile profile;


    /**
     * 一对多关联
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private List<Order> orders;


//    @PrePersist
//    public void prePersist() {
//        state = 1;
//        sex = "male";
//        long timestamp = new java.util.Date().getTime();
//        created = new Timestamp(timestamp);
//        updated = new Timestamp(timestamp);
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        updated = new Timestamp(new java.util.Date().getTime());
//    }


}
