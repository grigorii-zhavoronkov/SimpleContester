package ru.stray27.scontester.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.stray27.scontester.spring.custom.beanpostprocessors.ExecutorTestBeanPostProcessor;
import ru.stray27.scontester.spring.custom.beanpostprocessors.InjectCustomPropertyBeanPostProcessor;
import ru.stray27.scontester.spring.custom.beanpostprocessors.InjectExecutorsBeanPostProcessor;

@Configuration
public class BeanPostProcessorsConfiguration {
    @Bean
    public InjectExecutorsBeanPostProcessor injectExecutorsBeanPostProcessor() {
        return new InjectExecutorsBeanPostProcessor();
    }

    @Bean
    public ExecutorTestBeanPostProcessor executorTestBeanPostProcessor() {
        return new ExecutorTestBeanPostProcessor();
    }

    @Bean
    public InjectCustomPropertyBeanPostProcessor injectCustomPropertyBeanPostProcessor() {
        return new InjectCustomPropertyBeanPostProcessor();
    }
}
