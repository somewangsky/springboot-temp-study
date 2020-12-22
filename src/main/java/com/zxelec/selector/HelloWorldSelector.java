package com.zxelec.selector;


import com.zxelec.config.HelloWordConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * HelloWorldSelector
 *
 * @author vimicro
 * @date 2020/12/22
 */
public class HelloWorldSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{new HelloWordConfiguration().getClass().getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }
}
