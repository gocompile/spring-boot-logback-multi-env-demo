spring-boot-logback-multi-env-demo spring-boot下logback多环境配置示例
==

## 实现
spring-boot的application.properites配置文件中可以指定日志配置文件，利用maven的profiles插件功能实现在打包是指定配置文件

+ logback-base.xml 基础配置，多环境公用配置
+ logback-{env} 针对不同环境的配置

## 额外功能

1. jmx 集成