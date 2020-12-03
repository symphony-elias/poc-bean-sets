package com.example.demo.events;

import com.example.demo.bean.BeanA;
import com.example.demo.bean.BeanB;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @EventListener(condition = "#applicationEvent.source.valueEquals('one_a')")
    public void listen(CustomEvent<BeanB> applicationEvent) {
        System.out.println("Event received: " + applicationEvent.getSource());
    }

    @CustomEventListener(account = "one_a")
    public void listenOneB(CustomEvent<BeanB> event) {
        System.out.println("Event B one_a received with custom annotation: " + event.getSource());
    }

    @CustomEventListener(account = "two_a")
    public void listenTwoB(CustomEvent<BeanB> event) {
        System.out.println("Event B two_a  received with custom annotation: " + event.getSource());
    }

    @CustomEventListener(account = "one_a")
    public void listenOneA(CustomEvent<BeanA> event) {
        System.out.println("Event A one_a received with custom annotation: " + event.getSource());
    }

    @CustomEventListener(account = "two_a")
    public void listenTwoA(CustomEvent<BeanA> event) {
        System.out.println("Event A two_a received with custom annotation: " + event.getSource());
    }
}
