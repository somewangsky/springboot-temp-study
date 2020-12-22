package com.zxelec.annotation;

import com.zxelec.condition.OnSystemPropertyCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author vimicro
 */
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
