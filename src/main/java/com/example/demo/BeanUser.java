package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanUser {

    private final BeanB one;
    private final BeanB two;
    private final BeanA oneA;


    public BeanUser(@Qualifier("one") BeanB one, @Qualifier("two") BeanB two, @Qualifier("one") BeanA oneA) {
        this.one = one;
        this.two = two;
        this.oneA = oneA;
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "BeanUser{" +
                "one=" + one +
                ", two=" + two +
                ", oneA=" + oneA +
                '}';
    }
}
