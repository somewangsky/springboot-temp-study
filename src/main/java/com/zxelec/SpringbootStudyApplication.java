package com.zxelec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author vimicro
 */
//导入spring的配置文件，让其生效；后面会使用@Bean替代
@ImportResource(value = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringbootStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStudyApplication.class, args);
    }
}
