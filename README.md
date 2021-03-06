# learning-springboot

基于 spring boot 2.1.X （2.0.X 可参考 2.0.X 分支）

spring boot 自学笔记及实践，充分参考学习了 GitHub 上现存 springboot 学习项目：

[springboot-learning-example](https://github.com/JeffLi1993/springboot-learning-example) (star 5191)

[spring-boot-examples](https://github.com/ityouknow/spring-boot-examples) (star 4447)

[SpringBoot-Learning](https://github.com/dyc87112/SpringBoot-Learning) (star 3524)

增加了不少自己对业务场景的思考与幻想。（2019-06-14更新）

## 项目目录

### quickstart-hello 

* springboot helloworld
* 使用 springboot 以及 maven、pom.xml
* 命令行运行程序
* application.yml 的常规配置、多配置文件的加载（spring.profiles.active 与 spring.profiles.include）
* 配置日志
* 通过 Java Properties 类读取自定义配置

### quickstart-mvc

* Spring MVC 与 Restful 示例
* 定义路由，定义 HTTP 方法
* 请求中获取 Header、GET、POST、路径等参数
* Cookie、Session 常规操作示例
* 模板引擎 Thymeleaf 操作示例
* ScheduledTask 示例
* 使用 spring-boot-devtools 热更新（代码更新后修改自动编译）
* 404，500 页面自定义

### quickstart-jpa

* JPA 相关操作，定义实体，定义 Repository，CURD 以及分页操作示例
* 模拟业务场景中的 Controller -> Service -> Repository -> Entity 操作流程
* 通过定义 DTO 接受参数映射
* JPA 一对一，一对多，多对多关联示例

### quickstart-mybatis (原生)

* Mybatis 以及 Mybatis Generator 常规操作示例
* Mapper 与 XML Mapper 操作示例

### quickstart-mybatis-common

* Mybatis 集成通用 Mapper 与分页插件 pagehelper
* 使用 Mybatis 方式实现上面 JPA 同样的示例
* 如何实现使用多条件查询与关联查询

### quickstart-redis

* Redis 集成示例，Redis JavaConfig 配置key与value
* stringRedisTemplate 与 RedisTemplate
* 操作 Redis 的 string, list, set, hash 类型
* Cache 与 Redis 的集成，Cache注解
* 使用 Redis 托管 Session

### quickstart-rabbitmq

* RabbitMQ 集成示例

### quickstart-quartz

* quartz 集成示例

### quickstart-security

* Security 集成示例

### quickstart-admin

* springboot-admin 提供应用注册监控管理界面，含登陆认证

### quickstart-client

* springboot-client 注册到admin，然后通过admin进行管理


## 文章目录

[Spring Boot QuickStart (1) - 介绍](http://www.niuchaoqun.com/14963868024588.html)

[Spring Boot QuickStart (2) - 基础](http://www.niuchaoqun.com/14968999112830.html)

[Spring Boot QuickStart (3) - Web & Restful](http://www.niuchaoqun.com/14969970515462.html)

[Spring Boot QuickStart (4) - Database](http://www.niuchaoqun.com/14988948908551.html)

[Spring Boot QuickStart (5) - Spring Data JPA](http://www.niuchaoqun.com/14982055707598.html)

[Spring Boot QuickStart (6) - MyBatis](http://www.niuchaoqun.com/14992154022184.html)
