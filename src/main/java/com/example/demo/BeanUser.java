package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanUser {

    private final BeanB one;
    private final BeanB two;


    public BeanUser(@Qualifier("one_b") BeanB one, @Qualifier("two_b") BeanB two) {
        this.one = one;
        this.two = two;
        System.out.println("Bean user injected: " + one + " and " + two);
    }
}
