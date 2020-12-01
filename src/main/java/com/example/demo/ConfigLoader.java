package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigLoader implements BeanDefinitionRegistryPostProcessor {

    private final List<String> configurations;

    public ConfigLoader() {
        configurations = List.of("one", "two");
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String configName : this.configurations) {
            String beanNameB = configName + "_b";
            BeanDefinitionBuilder builderB = BeanDefinitionBuilder.genericBeanDefinition(BeanB.class)
                    .addConstructorArgReference(configName + "_a").setLazyInit(true);
            registry.registerBeanDefinition(beanNameB, builderB.getBeanDefinition());

            String beanNameA = configName + "_a";
            BeanDefinitionBuilder builderA = BeanDefinitionBuilder.genericBeanDefinition(BeanA.class).setLazyInit(true);
            registry.registerBeanDefinition(beanNameA, builderA.getBeanDefinition());
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
