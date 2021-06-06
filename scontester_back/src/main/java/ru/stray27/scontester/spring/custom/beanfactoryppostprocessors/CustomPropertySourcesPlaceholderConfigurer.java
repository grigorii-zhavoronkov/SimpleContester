package ru.stray27.scontester.spring.custom.beanfactoryppostprocessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import ru.stray27.scontester.annotations.CustomProperty;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class CustomPropertySourcesPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private final Properties customProperties = new Properties();

    {
        try {
            List<String> customPropertiesList = Files.readAllLines(Paths.get(
                    Path.of(".").toAbsolutePath().toString(),
                    "custom.properties"));
            for (String property : customPropertiesList) {
                if (!property.trim().isBlank() && !property.startsWith("#")) {
                    String[] tokens = property.split("=");
                    String propertyKey = tokens[0];
                    String propertyValue = tokens[1];
                    customProperties.setProperty(propertyKey, propertyValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        annotation
        for (String beanDefinitionName : beanDefinitionNames) {
            Class<?> beanClass = beanFactory.getType(beanDefinitionName);
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(CustomProperty.class)) {
                    CustomProperty customProperty = field.getAnnotation(CustomProperty.class);
                    String propertyName = customProperty.value();
                    if (customProperties.containsKey(propertyName)) {
                        String propertyValue = customProperties.getProperty(propertyName);
                        if (field.getType() == String.class) {
                            field.setAccessible(true);
                            field.set(bean, propertyValue);
                        } else {
                            log.warn("Field type is not String in bean " + beanName + ". Could cause unexpected behaviour.");
                        }
                    } else {
                        log.warn("There is no custom property named " + propertyName + " for bean " + beanName);
                    }
                }
            }
        }
    }
}
