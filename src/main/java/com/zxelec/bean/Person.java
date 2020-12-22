package com.zxelec.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Person
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 *
 * @author vimicro
 * @ConfigurationProperties: 告诉springboot将本类中的所有属性和配置文件中相关的配置进行绑定
 * prefix = "person": 配置文件中哪个下面的所有属性进行一一映射
 * @Component: 只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties功能
 * @date 2020/12/18
 */
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

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birthday=" + birthday +
                ", map=" + map +
                ", list=" + list +
                ", dog=" + dog +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
