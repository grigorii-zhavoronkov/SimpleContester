package ru.stray27.scontester.services.implementations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.entities.Test;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.services.ExecutorService;
import ru.stray27.scontester.services.annotations.InjectExecutors;
import ru.stray27.scontester.services.TaskExecutorService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class DefaultTaskExecutorService implements TaskExecutorService {

    @Autowired
    private AttemptRepository attemptRepository;

    @InjectExecutors
    private Map<ProgrammingLanguage, ExecutorService> executors;

    private Attempt attempt;

    @PostConstruct
    public void printExecutors() {
        log.info("Executors count: " + executors.size());
    }

    @Override
    @Async
    synchronized public CompletableFuture<Integer> runTests() {
        List<Test> tests = findTests(attempt);
        ExecutorService executor = executors.get(attempt.getProgrammingLanguage());
        attempt.setAttemptStatus(AttemptStatus.RUNNING);
        attemptRepository.save(attempt);
        return CompletableFuture.completedFuture(executor.execute(tests, attempt));
    }

    @Override
    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    protected List<Test> findTests(Attempt attempt) {
        return new ArrayList<>(attempt.getTask().getTests());
    }

    @SneakyThrows
    @Override
    public void run() {
        attempt.setLastTestNumber(runTests().get());
        ExecutorService executor = executors.get(attempt.getProgrammingLanguage());
        attempt.setAttemptStatus(executor.getAttemptStatus());
        attemptRepository.save(attempt);
    }
}
