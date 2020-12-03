package com.example.demo.events;

import org.springframework.context.event.EventListener;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class GenericListener {
    public static class Listener {
        private Object bean;
        private Method method;
        private String account;
        private ResolvableType type;

        public Listener(Object bean, Method method, String account) {
            this.bean = bean;
            this.method = method;
            this.account = account;
            this.type = ResolvableType.forMethodParameter(method, 0);
            System.out.println("Registered listener: " + this);
        }

        @Override
        public String toString() {
            return "Listener{" +
                    "bean=" + bean +
                    ", method=" + method +
                    ", account='" + account + '\'' +
                    ", type=" + type +
                    '}';
        }

        public boolean applies(CustomEvent<?> event) {
            return type.isAssignableFrom(event.getResolvableType()) && Objects.equals(account, event.getAccount());
        }

        public void execute(CustomEvent<?> event) {
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Listener> listeners;

    public GenericListener() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(Object listener, Method method, String account) {
        listeners.add(new Listener(listener, method, account));
    }

    @EventListener
    public void listen(CustomEvent<?> event) {
        for (Listener listener : listeners) {
            if (listener.applies(event)) {
                listener.execute(event);
            }
        }
    }
}
