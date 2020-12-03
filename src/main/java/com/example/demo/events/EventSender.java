package com.example.demo.events;

import org.springframework.context.ApplicationEventPublisher;

public class EventSender {

    private final ApplicationEventPublisher publisher;

    public EventSender(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendEvent(Object event, String account) {
        publisher.publishEvent(new CustomEvent<>(event, account));
    }
}
