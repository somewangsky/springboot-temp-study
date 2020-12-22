package com.zxelec.annotation;

import com.zxelec.selector.HelloWorldSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author vimicro
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = HelloWorldSelector.class)
public @interface EnableHelloWorld {
}
