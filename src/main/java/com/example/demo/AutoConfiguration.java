package com.example.demo;

import com.example.demo.bean.BeanA;
import com.example.demo.bean.BeanB;
import com.example.demo.events.EventSender;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {

    @Bean
    BeanA beanA() {
        return new BeanA();
    }

    @Bean(initMethod = "sendEvent")
    BeanB beanB(BeanA beanA, EventSender eventSender) {
        return new BeanB(beanA, eventSender);
    }

    @Bean
    EventSender eventSender(ApplicationEventPublisher publisher) {
        return new EventSender(publisher);
    }
}
