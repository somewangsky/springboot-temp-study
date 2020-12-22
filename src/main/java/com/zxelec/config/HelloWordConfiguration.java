package com.zxelec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HelloWordConfiguration
 *
 * @author vimicro
 * @date 2020/12/22
 */
@Configuration
public class HelloWordConfiguration {

    @Bean(value = "helloWorld")
    public String helloWorld() {
        return "hello world!";
    }
}
