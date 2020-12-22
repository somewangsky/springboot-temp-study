package com.zxelec.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Java 7 for 循环实现 {@link CalculateService}
 *
 * @author vimicro
 */
@Profile("Java7")
@Service
public class Java7CalculateService implements CalculateService {

    @Override
    public Integer sum(Integer... values) {
        System.out.println("Java 7 for 循环实现 ");
        int sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum;
    }
}
