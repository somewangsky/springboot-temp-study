# springboot study

author：somewang

## 一.配置文件

### 1.配置文件

springboot使用一个全局的配置文件，配置文件名是固定的

- application.properties
- application.yml

配置文件的作用：修改springboot自动配置的默认值，springboot在底层都给我们自动配置好了。

​	properties：配置例子

```prop
server.port: 8081
```

​	yaml：配置例子

```yaml
server:
	port: 8081
```

​	xml：配置例子

```xml
<server>
	<port>8081</port>
</server>
```

### 2.yml语法

#### 1.基本语法

k:(空格)v :表示一对键值对（空格必须有）；以空格的缩进来控制层级关系；只要是左对齐的一列数据，都是同一个层级的。

```yaml
server:
	port: 8081
	path: /hello
```

属性和值也是大小写敏感。

#### 2.值的写法

##### 字面量：普通的值（数字，字符串，布尔）:

​	k: v --字面直接来写

​		字符串默认不用加上单引号或者双引号；

​		""：双引号不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思

​			name: "zhangsan \n lisi"：输出：zhangsan 换行 lisi

​		''：单引号；会转义特殊字符，特殊字符最终只是一个普通的字符串数据

​			name: "zhangsan \n lisi"：输出：zhangsan \n lisi

##### 对象、map（属性和值）（键值对）：

​	k: v --在下一行来写对象的属性和值的关系；注意缩进

​	k: v的方式

```yam
friends:
	lastName: zhangsan
	age: 20
```

​	行内写法

```yaml
friends:{lastName: zhangsan,age: 20}
```

##### 数组（List、Set）：

​	用-值表示数组中的一个元素

```yaml
pets:
  - cat
  - dog
  - pig
```

​	行内写法

```yaml
pets:[cat,dog,pig]
```

#### 3.配置文件值注入

配置文件

```yam
person:
  last-name: 张三
  age: 18
  boss: false
  birthday: 2020/12/18
  map: {k1: v1,k2: v2}
  list:
    - apple
    - banana
  dog:
    name: 大黄
    age: 3
    color: blue
```

javaBean

```java
/**
 * Person
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties: 告诉springboot将本类中的所有属性和配置文件中相关的配置进行绑定
 *      prefix = "person": 配置文件中哪个下面的所有属性进行一一映射
 *
 * @Component: 只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties功能
 *
 * @author vimicro
 * @date 2020/12/18
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String lastName;
    private Integer age;
    private boolean boss;
    private Date birthday;
    private Map<String,Object> map;
    private List<Object> list;
    private Dog dog;
```

我们可以导入配置文件处理器，以后编写配置就有提示了

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <version>2.3.6.RELEASE</version>
</dependency>
```

### 3.@Value与@ConfigurationProperties获取值的对比

|                | ConfigurationProperties  | Value        |
| -------------- | ------------------------ | ------------ |
| 功能上         | 批量注入配置文件中的属性 | 一个一个指定 |
| 松散绑定       | 支持                     | 不支持       |
| SpEL表达式     | 不支持                   | 支持         |
| JSR303数据校验 | 支持                     | 不支持       |
| 赋值类型封装   | 支持                     | 不支持       |

#### 1.松散绑定

```yaml
lastName -- last-name
			last_name
			lastName
```

#### 2.SpEL表达式

```java
@Value("#{18*10}")
private Integer size;
```

#### 3.JSR303数据校验

​	引入pom文件

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

 	添加注解

```java
@Component
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {
    @Email(message = "邮件格式不正确")
    private String lastName;
    private Integer age;
    private boolean boss;
    private Date birthday;
    private Map<String, Object> map;
    private List<Object> list;
    private Dog dog;
```

### 4.@PropertySource与@ImportResource

**@PropertySource**：加载指定的配置文件

```java
@Component
@PropertySource(value = {"classpath:cat.properties"})
public class Cat {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private Integer age;
    @Value("${color}")
    private String color;
```

**@ImportResource**：导入spring的配置文件，让配置文件中的内容生效

springboot里面没有spring的配置文件，我们自己编写的配置文件，也不能自动识别，想让spring的配置文件生效，加载进来@ImportResource加载到配置文件上

```java
//导入spring的配置文件，让其生效；后面会使用@Bean替代
@ImportResource(value = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringbootStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootStudyApplication.class, args);
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="helloService" class="com.zxelec.service.HelloService"></bean>
</beans>
```

### 5.配置文件占位符

#### 1.随机数

```yaml
${random.value}、${random.int}、${random.long}
${random.int(10)}、${random.int[1024,65536]}
```

#### 2.占位符获取之前配置的值，如果没有可以设置默认值

```yaml
person:
  last-name: 123@qq.com${random.uuid} #获取随机的uuid
  age: ${random.int} #获取随机的整数
  boss: false
  birthday: 2020/12/18
  map: {k1: v1,k2: v2}
  list:
    - apple
    - banana
  dog:
    name: ${person.last-name:hello}大黄
    age: 3
    color: blue
```

### 5.profile多环境配置

#### 1.多profile文件形式：

​	格式：application-{profile}.properties

​		application-dev.properties、application-prod.properties

#### 2.多profile文档快模式

```yaml
server:
  port: 8081
spring:
  profiles:
    active: dev
---
spring:
  config:
    activate:
      on-profile: dev
server:
  port: 8082
```

#### 3.激活方式：

- 命令行 --spring.profiles.active=dev
- 配置文件 spring.profiles.active=dev
- jvm参数 -Dspring.profiles.active=dev



## 二.自动装配

### 1.模式注解装配

#### 1.模式注解举例

| Spring Framework 注解 | 场景说明           | 起始版本 |
| --------------------- | ------------------ | -------- |
| @Repository           | 数据仓储模式注解   | 2.0      |
| @Component            | 通用组件模式注解   | 2.5      |
| @Service              | 服务模式注解       | 2.5      |
| @Controller           | Web 控制器模式注解 | 2.5      |
| @Configuration        | 配置类模式注解     | 3.0      |

##### **1.@ComponentScan**方式

```java
@ComponentScan(basePackages = "com.zxelec.dive.in.spring.boot")
public class SpringConfiguration {
	...
}
```

##### 2.自定义模式注解

**@Component“派生性”**

```java
/
**
* 一级 {@link Repository @Repository}
* 
*/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface FirstLevelRepository {
	String value() default "";
}
```

- @Component
  - @Repository
    - FirstLevelRepository

**@Component“层次性”**

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FirstLevelRepository
public @interface SecondLevelRepository {
	String value() default "";
}
```

- @Component
  - @Repository
    - @FirstLevelRepository
      - SecondLevelRepository

### 2.@Enable 模块装配

Spring Framework 3.1 开始支持”@Enable 模块驱动“。所谓“模块”是指具备相同领域的功能组件集合， 组合所形成一个独立的单元。比如 Web MVC 模块、AspectJ代理模块、Caching（缓存）模块、JMX（Java 管 理扩展）模块、Async（异步处理）模块等 。

#### 1.@Enable注解模块举例

| 框架实现                       | @Enable 注解模块         | 激活模块         |
| ------------------------------ | ------------------------ | ---------------- |
| Spring Framework               | @EnableWebMvc            | Web MVC 模块     |
| @EnableTransactionManagement   | 事务管理模块             |                  |
| @EnableCaching                 | Caching 模块             |                  |
| @EnableMBeanExport             | JMX 模块                 |                  |
| @EnableAsync                   | 异步处理模块             |                  |
| EnableWebFlux                  | Web Flux 模块            |                  |
| @EnableAspectJAutoProxy        | AspectJ 代理模块         |                  |
| Spring Boot                    | @EnableAutoConfiguration | 自动装配模块     |
| @EnableManagementContext       | Actuator 管理模块        |                  |
| @EnableConfigurationProperties | 配置属性绑定模块         |                  |
| @EnableOAuth2Sso               | OAuth2 单点登录模块      |                  |
| Spring Cloud                   | @EnableEurekaServer      | Eureka服务器模块 |
| @EnableConfigServer            | 配置服务器模块           |                  |
| @EnableFeignClients            | Feign客户端模块          |                  |
| @EnableZuulProxy               | 服务网关 Zuul 模块       |                  |
| @EnableCircuitBreaker          | 服务熔断模块             |                  |

##### 1.注解驱动方式

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
}
```

```java
@Configuration
public class DelegatingWebMvcConfiguration extends
WebMvcConfigurationSupport {
	...
}
```

##### 2.接口编程方式

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CachingConfigurationSelector.class)
public @interface EnableCaching {
	...
}
```

```java
public class CachingConfigurationSelector extends AdviceModeImportSelector<EnableCaching> {
        /**
         * {@inheritDoc}
         *
         * @return {@link ProxyCachingConfiguration} or {@code
         * AspectJCacheConfiguration} for
         * {@code PROXY} and {@code ASPECTJ} values of {@link
         * EnableCaching#mode()}, respectively
         */
        public String[] selectImports(AdviceMode adviceMode) {
            switch (adviceMode) {
                case PROXY:
                    return new String[]{
                            AutoProxyRegistrar.class.getName(), ProxyCachingConfiguration.class.getName()};
                case ASPECTJ:
                    return new String[]{
                            AnnotationConfigUtils.CACHE_ASPECT_CONFIGURATION_CLASS_NAME};
                default:
                    return null;
            }
        }
    }
```

#### 2.自定义@Enable模块

##### 1.基于注解驱动实现-@EnableHelloServer

1.启动类

```java
@EnableHelloServer
public class HelloServerBootstrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(HelloServerBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);

        String helloWorld = applicationContext.getBean("helloServer", String.class);

        System.out.println(helloWorld);
    }
}
```

2.编写@EnableHelloServer注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = HelloServerConfiguration.class)
public @interface EnableHelloServer {
}
```

3.编写HelloWordConfiguration类

```java
@Configuration
public class HelloServerConfiguration {

    @Bean(value = "helloServer")
    public String helloServer() {
        return "hello server!";
    }
}
```

##### 2.基于接口驱动实现-@EnableHelloWorld

1.启动类

```java
@EnableHelloWorld
public class HelloWordBootstrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(HelloWordBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);

        String helloWorld = applicationContext.getBean("helloWorld", String.class);

        System.out.println(helloWorld);
    }
}
```

2.编写@EnableHelloWorld注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = HelloWorldSelector.class)
public @interface EnableHelloWorld {
}
```

3.编写HelloWorldSelector类

```java
public class HelloWorldSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{new HelloWordConfiguration().getClass().getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }
}
```

4.编写HelloWordConfiguration类

```java
@Configuration
public class HelloWordConfiguration {

    @Bean(value = "helloWorld")
    public String helloWorld() {
        return "hello world!";
    }
}
```

流程总结：**@EnableHelloWorld**->**HelloWorldSelector**->**HelloWordConfiguration**->**@Bean**

### 3.条件装配

#### 1.条件注解举例

| Spring 注解  | 场景说明       | 起始版本 |
| ------------ | -------------- | -------- |
| @Profile     | 配置化条件装配 | 3.1      |
| @Conditional | 编程条件装配   | 4.0      |

#### 2.实现方式

##### 1.配置方式 - @Profile

##### 2.编程方式 - @Conditional 

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {

	/**
	 * The classes that must be present. Since this annotation is parsed by loading class
	 * bytecode, it is safe to specify classes here that may ultimately not be on the
	 * classpath, only if this annotation is directly on the affected component and
	 * <b>not</b> if this annotation is used as a composed, meta-annotation. In order to
	 * use this annotation as a meta-annotation, only use the {@link #name} attribute.
	 * @return the classes that must be present
	 */
	Class<?>[] value() default {};

	/**
	 * The classes names that must be present.
	 * @return the class names that must be present.
	 */
	String[] name() default {};

}
```

#### 3.自定义条件装配 

计算服务，多整数求和 sum
@Profile("Java7") : for 循环
@Profile("Java8") : Lambda

##### 1.基于配置方式实现 - @Profile

1.启动类

```java
@SpringBootApplication(scanBasePackages = "com.zxelec.service")
public class CalculateServiceBootstrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CalculateServiceBootstrap.class)
                .web(WebApplicationType.NONE)
                .profiles("Java8")
                .run(args);

        // CalculateService Bean 是否存在
        CalculateService calculateService = context.getBean(CalculateService.class);

        System.out.println("calculateService.sum(1...10) : " +
                calculateService.sum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        // 关闭上下文
        context.close();
    }
}
```

2.编写算术计算的接口类

```java
public interface CalculateService {

    /**
     * 从多个整数 sum 求和
     * @param values 多个整数
     * @return sum 累加值
     */
    Integer sum(Integer... values);
}
```

3.java7的实现

```java
@Profile("Java7")
@Service
public class Java7CalculateService implements CalculateService {

    @Override
    public Integer sum(Integer... values) {
        System.out.println("Java 7 for 循环实现 ");
        int sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum;
    }
}
```

4.java8的实现

```java
@Profile("Java8")
@Service
public class Java8CalculateService implements CalculateService {

    @Override
    public Integer sum(Integer... values) {
        System.out.println("Java 8 Lambda 实现");
        int sum = Stream.of(values).reduce(0, Integer::sum);
        return sum;
    }
}
```

##### 2.基于编程方式实现 - @ConditionalOnSystemProperty 

1.启动类

```java
public class ConditionalOnSystemPropertyBootstrap {

    @Bean
    @ConditionalOnSystemProperty(name = "user.name", value = "vimicro")
    public String helloWorld() {
        return "Hello，condition!!!";
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ConditionalOnSystemPropertyBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        // 通过名称和类型获取 helloWorld Bean
        String helloWorld = context.getBean("helloWorld", String.class);
        System.out.println("helloWorld Bean : " + helloWorld);
        // 关闭上下文
        context.close();
    }
}
```

2.实现@ConditionalOnSystemProperty注解

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnSystemPropertyCondition.class)
public @interface ConditionalOnSystemProperty {

    /**
     * Java 系统属性名称
     */
    String name();

    /**
     * Java 系统属性值
     */
    String value();
}
```

3.实现OnSystemPropertyCondition类

```java
public class OnSystemPropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnSystemProperty.class.getName());
        String propertyName = String.valueOf(attributes.get("name"));
        String propertyValue = String.valueOf(attributes.get("value"));
        String javaPropertyValue = System.getProperty(propertyName);

        return propertyValue.equals(javaPropertyValue);
    }
}
```

