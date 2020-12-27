package ru.stray27.scontester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
public class SContesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SContesterApplication.class, args);
    }

}
