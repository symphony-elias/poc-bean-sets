package com.example.demo.events;

import com.example.demo.bean.BeanB;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BeanListener implements ApplicationListener<CustomEvent<BeanB>> {

    @Override
    public void onApplicationEvent(CustomEvent<BeanB> event) {
        System.out.println("Event received from BeanListener: " + event.getSource());
    }
}
