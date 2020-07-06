# proxys

java 代理模式进行数据源的切换

##  代理实现目的

现在项目上实现的是多数据源的情况，但是当我的数据源比较多的时候，我每实现一个数据源就要写一个config去配置，但是我并不想这么配置，我想的是能不能够当我使用同一个config的情况下，在配置文件中配置了不同的数据源的时候，~~使用不同的数据源的时候能不能根据dao层路径实现动态数据源的切换呢~~ 使用注解的形式用代理进行自动切换

## 接入步骤

- 引入maven
```xml
<dependency>
    <groupId>com.fulln</groupId>
    <artifactId>datasource-switch-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
- 在启动类上加载`@EnableDynamicSource`

- 在需要切换的查询方法上面加上对应注解`@DataSourceComponent`

## TODO

 - [x] 将当前通过aop的切换模式改成cglib的模式进行切换(一个工具类的包太大，去掉不必要的aop依赖)
 - [x] 支持在类上直接加注解，从而每次访问这个类的所有方法的时候都是可以直接切换

## 流程图
- 启动的相关流程

![pic](./pic/lct.png)

- 使用相关流程图

![pic](./pic/sy.png)
