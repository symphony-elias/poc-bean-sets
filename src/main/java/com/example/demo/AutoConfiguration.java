package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {

    @Bean
    BeanA beanA() {
        return new BeanA();
    }

    @Bean
    BeanB beanB(BeanA beanA) {
        return new BeanB(beanA);
    }
}
