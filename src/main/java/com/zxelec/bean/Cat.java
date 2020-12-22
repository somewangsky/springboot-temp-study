package com.zxelec.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Cat
 *
 * @author vimicro
 * @date 2020/12/18
 */
@Component
@PropertySource(value = {"classpath:cat.properties"})
public class Cat {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private Integer age;
    @Value("${color}")
    private String color;

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
