package com.zxelec.annotation;

import com.zxelec.config.HelloServerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author vimicro
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = HelloServerConfiguration.class)
public @interface EnableHelloServer {
}
