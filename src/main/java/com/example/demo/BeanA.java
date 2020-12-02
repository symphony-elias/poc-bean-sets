package com.example.demo;

public class BeanA {
    private String field;

    public BeanA() {
        System.out.println("BeanA");
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "field='" + field + '\'' +
                '}';
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
