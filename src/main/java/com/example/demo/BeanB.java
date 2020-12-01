package com.example.demo;

public class BeanB {
    private BeanA beanA;

    public BeanB(BeanA beanA) {
        this.beanA = beanA;
        System.out.println("BeanB");
    }
}
