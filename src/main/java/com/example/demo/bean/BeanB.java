package com.example.demo.bean;

import com.example.demo.events.EventSender;

public class BeanB {
    private final BeanA beanA;
    private final EventSender sender;

    public BeanB(BeanA beanA, EventSender sender) {
        this.beanA = beanA;
        this.sender = sender;
        System.out.println("BeanB");
    }

    public boolean valueEquals(String field) {
        return (field == null && beanA.getField() == null) || (field != null && field.equals(beanA.getField()));
    }

    public void sendEvent() {
        new Thread(() -> {
            while (true) {
                this.sender.sendEvent(this, this.beanA.getField());
                this.sender.sendEvent(this.beanA, this.beanA.getField());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public String toString() {
        return "BeanB{" +
                "beanA=" + beanA +
                '}';
    }
}
