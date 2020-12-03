package com.example.demo.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class CustomEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    private final T source;
    private final String account;

    public CustomEvent(T source, String account) {
        super(source);
        this.source = source;
        this.account = account;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(),
                ResolvableType.forInstance(source));
    }

    @Override
    public T getSource() {
        return source;
    }

    public String getAccount() {
        return account;
    }
}
