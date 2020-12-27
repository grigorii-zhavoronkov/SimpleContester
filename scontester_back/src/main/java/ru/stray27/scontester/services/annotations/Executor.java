package ru.stray27.scontester.services.annotations;

import org.springframework.stereotype.Service;
import ru.stray27.scontester.entities.ProgrammingLanguage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Service
public @interface Executor {
    ProgrammingLanguage language();
}
