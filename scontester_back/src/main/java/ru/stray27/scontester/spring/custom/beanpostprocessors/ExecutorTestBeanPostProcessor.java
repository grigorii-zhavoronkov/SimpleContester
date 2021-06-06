package ru.stray27.scontester.spring.custom.beanpostprocessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.stray27.scontester.services.ExecutorService;
import ru.stray27.scontester.annotations.Executor;

public class ExecutorTestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Executor.class)) {
            ExecutorService executor = (ExecutorService) bean;
            if (!executor.testExecutablePath()) {
                throw new RuntimeException("Executor " + beanName + " doesn't set properly");
            }
        }
        return bean;
    }
}
