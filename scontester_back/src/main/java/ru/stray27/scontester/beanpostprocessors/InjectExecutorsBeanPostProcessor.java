package ru.stray27.scontester.beanpostprocessors;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.services.annotations.Executor;
import ru.stray27.scontester.services.ExecutorService;
import ru.stray27.scontester.services.annotations.InjectExecutors;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class InjectExecutorsBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext context;

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<Field> fields = Arrays.stream(bean
                .getClass()
                .getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectExecutors.class))
                .collect(Collectors.toList());
        for (Field field : fields) {
            List<ExecutorService> executors = context.getBeansWithAnnotation(Executor.class)
                    .values()
                    .stream()
                    .filter(it -> it instanceof ExecutorService)
                    .map(it -> (ExecutorService) it)
                    .collect(Collectors.toList());
            Map<ProgrammingLanguage, ExecutorService> executorServices = new HashMap<>();
            for (ExecutorService executor : executors) {
                Executor executorAnnotation = executor.getClass().getAnnotation(Executor.class);
                if (executorServices.containsKey(executorAnnotation.language())) {
                    log.warn("Found duplicated executors for language " + executorAnnotation.language());
                    log.warn(beanName + " will be used");
                }
                executorServices.put(executorAnnotation.language(), executor);
            }
            field.setAccessible(true);
            field.set(bean, executorServices);
        }
        return bean;
    }
}
