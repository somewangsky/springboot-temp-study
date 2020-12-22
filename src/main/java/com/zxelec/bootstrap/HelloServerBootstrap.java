package com.zxelec.bootstrap;

import com.zxelec.annotation.EnableHelloServer;
import com.zxelec.annotation.EnableHelloWorld;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * HelloWordBootStrap
 *
 * @author vimicro
 * @date 2020/12/22
 */
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
