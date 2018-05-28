package com.niuchaoqun.jpa.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Data  是 lombok 的注解，自动生成Getter，Setter，toString，构造函数等
 * @Entity   注解 Spring Data JPA 实体类
 * @Table  注解表相关选项，如表别名等
 * @Id  注解主键
 * @GeneratedValue  注解字段为自动生成
 * @Column  注解字段属性，如是否允许为空，是否唯一，是否进行插入和更新
 * @Transient 标识该字段并非数据库字段映射
 *
 * @JsonProperty  JSON 注解，比如配置别名
 * @JsonIgnore  JSON 注解，在 JSON 时忽略
 * @JsonFormat  JSON 注解，格式化
 */

@Data
@Entity
@Table(name = "`user`")
//@DynamicUpdate
//@ToString(exclude = {"role", "detail"})
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

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp updated;

    @Column(nullable = true)
    private Integer state;
//
//    @OneToOne
//    @JoinColumn
//    @JsonManagedReference
//    private Role role;
//
//    @OneToOne(mappedBy = "user")
//    @JsonManagedReference
//    private UserDetail detail;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    @JsonBackReference
//    private List<Order> orders;
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public Role getRole() {
//        return this.role;
//    }
//
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
