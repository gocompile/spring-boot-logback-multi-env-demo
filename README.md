spring-boot-logback-multi-env-demo spring-boot下logback多环境配置示例
==

## 实现
spring-boot的application.properites配置文件中可以指定日志配置文件，利用maven的profiles插件功能实现在打包是指定配置文件

+ logback-base.xml 基础配置，多环境公用配置
+ logback-{env} 针对不同环境的配置

## 额外功能

1. jmx 集成

## 使用
```
mvn -P{env}
```

## 详解
+ logback-base.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<included>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <jmxConfigurator/>
    <property name="LOG_FILE" value="/tmp"/>
    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径 -->
    <property name="LOG_HOME" value="${LOG_FILE:-${LOG_PATH:-${LOG_TEMP:-${java.io.tmpdir:-./tmp}}/}}"/>
    <!-- 最大保存历史日志天数 -->
    <property name="LOG_MAX_HISTORY" value="15"/>
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
    <property name="FILE_LOG_PATTERN" value="${FILE_LOG_PATTERN:-%d{yyyy-MM-dd HH:mm:ss.SSS} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>


    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!--<withJansi>true</withJansi>-->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符 -->
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>

    <!-- 消息日志，记录项目所有消息记录 -->
    <appender name="infoLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 如果指定了file属性，当天的文件名为file属性值 -->
        <file>${LOG_HOME}/info.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名 -->
            <FileNamePattern>${LOG_HOME}/log.info.%d{yyyy-MM-dd}.log</FileNamePattern>
            <maxHistory>${LOG_MAX_HISTORY}</maxHistory>
        </rollingPolicy>
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符 -->
            <pattern>${FILE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>

    <!-- 错误日志，记录项目标识的错误级别信息 -->
    <appender name="errorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_HOME}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_HOME}/log.error.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>${LOG_MAX_HISTORY}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
        </encoder>

        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level><!-- 只接收错误级别的日志 -->
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>


</included>
```

+ logback-{env}.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration debug="true">

    <include resource="logback/logback-base.xml" />
    <!-- 日志输出级别 -->
    <root level="INFO">
        <!-- 错误日志 -->
        <appender-ref ref="errorLog"/>
        <!-- 消息日志 -->
        <appender-ref ref="infoLog"/>
    </root>

</configuration>
```