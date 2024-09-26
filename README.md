# spring-cloud-parent

    基于jdk17的spring-cloud微服务框架，整合nacos作为注册中心、配置中心、整合mybatis、shardingsphere作为数据访问层支持分库分表和基础CRUD操作。
    spring-cloud microservice framework based on jdk17 integrates nacos as the registry and configuration center, integrates mybatis and shardingsphere as the data access layer to support sharding-database、sharding-table and basic CRUD operations.
## project structure

    spring-cloud-core           core code   
    spring-cloud-api            common code,base on core, all biz code dependency this module
    spring-cloud-gateway        gateway service
    spring-cloud-user-service   user service
    spring-cloud-order-service  order service

## core code
    * base dao,service,controller,result abstract
    * code generator, see com.u8d75.core.codegen.CodeGenerator
    * base crud, see com.u8d75.user.CrudTest or com.u8d75.core.base.dao.BaseDao or com.u8d75.core.mybatis.BaseSqlProvider
    * use MDC print user id and path
    * slow sql log print, sql time cost print, sql comment add user id and path
    * shardingsphere intergration, use nacos config center（for compatibility of spring fremework, replace !XXX with #!XXX and trun it back） see com.u8d75.core.shardingsphere.SpringNacosURLLoader
    * swagger intergration

## reference

shardingsphere:  
<https://shardingsphere.apache.org>

spring-cloud:  
<https://spring.io/projects/spring-cloud>

nacos:  
<https://nacos.io>

## usage

    * clone project
    * init database test.sql
    * download naocs and start it, init nacos config
    * run mvn clean install -Dmaven.test.skip=true
    * start spring-cloud-gateway,spring-cloud-user-service,spring-cloud-order-service
    * access swagger doc to show demo api

## others
nacos console:  
<http://localhost:8848/nacos/>

swagger:  
<http://127.0.0.1:29080/webjars/swagger-ui/index.html>

enjoy it!

