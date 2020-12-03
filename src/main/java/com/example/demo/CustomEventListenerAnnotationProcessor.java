package com.example.demo;

import com.example.demo.events.CustomEvent;
import com.example.demo.events.CustomEventListener;
import com.example.demo.events.GenericListener;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotationUtils.isCandidateClass;

@Component
public class CustomEventListenerAnnotationProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;
    private GenericListener listener;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        this.listener = this.applicationContext.getBean(GenericListener.class);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //here: register listener
        if (!ScopedProxyUtils.isScopedTarget(beanName)) {
            final Class<?> type = this.determineTargetClass(beanName);
            if (type != null && isCandidateClass(type, CustomEventListener.class)) {
                try {
                    this.processBean(bean, beanName, type);
                } catch (Throwable ex) {
                    // just alert the developer
                }
            }
        }
        return bean;
    }

    private void processBean(Object bean, String beanName, Class<?> targetType) {
        final Map<Method, CustomEventListener> annotatedMethods = this.getSlashAnnotatedMethods(beanName, targetType);

        for (final Method m : annotatedMethods.keySet()) {
            final CustomEventListener annotation = annotatedMethods.get(m);
            this.listener.addListener(bean, m, annotation.account());
        }
    }

    private Class<?> determineTargetClass(String beanName) {
        Class<?> type = null;
        try {
            type = AutoProxyUtils.determineTargetClass(this.applicationContext.getBeanFactory(), beanName);
        } catch (Throwable ex) {
            // An unresolvable bean type, probably from a lazy bean - let's ignore it.
        }

        if (type != null && ScopedObject.class.isAssignableFrom(type)) {
            try {
                Class<?> targetClass = AutoProxyUtils.determineTargetClass(
                        this.applicationContext.getBeanFactory(),
                        ScopedProxyUtils.getTargetBeanName(beanName)
                );
                if (targetClass != null) {
                    type = targetClass;
                }
            } catch (Throwable ex) {
                // An invalid scoped proxy arrangement - let's ignore it.
            }
        }

        return type;
    }

    private Map<Method, CustomEventListener> getSlashAnnotatedMethods(String beanName, Class<?> targetType) {
        Map<Method, CustomEventListener> annotatedMethods = null;

        try {
            annotatedMethods = MethodIntrospector.selectMethods(
                    targetType,
                    (MethodIntrospector.MetadataLookup<CustomEventListener>) method -> findMergedAnnotation(method, CustomEventListener.class)
            );
        } catch (Throwable ex) {
            // An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
        }
        return annotatedMethods == null ? Collections.emptyMap() : annotatedMethods;
    }

    private static boolean isMethodPrototypeValid(Method m) {
        return m.getParameterCount() == 1 && m.getParameters()[0].getType().equals(CustomEvent.class);
    }
}
