package ru.stray27.scontester.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.stray27.scontester.beanpostprocessors.ExecutorTestBeanPostProcessor;
import ru.stray27.scontester.beanpostprocessors.InjectExecutorsBeanPostProcessor;

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
}
