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
public class HelloServerConfiguration {

    @Bean(value = "helloServer")
    public String helloServer() {
        return "hello server!";
    }
}
