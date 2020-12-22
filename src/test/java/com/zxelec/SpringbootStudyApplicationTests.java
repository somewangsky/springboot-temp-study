package com.zxelec;

import com.zxelec.bean.Cat;
import com.zxelec.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringbootStudyApplicationTests {

    @Autowired
    private Person person;

    @Autowired
    private Cat cat;

    @Autowired
    private ApplicationContext ioc;

    @Test
    void contextLoads() {

        boolean helloService = ioc.containsBean("helloService");
        System.out.println(helloService);

        System.out.println(cat);
        System.out.println(person);
    }
}
