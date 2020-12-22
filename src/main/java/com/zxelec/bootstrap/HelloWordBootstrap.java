package com.zxelec.bootstrap;

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
