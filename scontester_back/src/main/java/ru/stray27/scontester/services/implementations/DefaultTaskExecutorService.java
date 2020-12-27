package ru.stray27.scontester.services.implementations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.services.ExecutorService;
import ru.stray27.scontester.services.annotations.InjectExecutors;
import ru.stray27.scontester.services.TaskExecutorService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
        List<String> tests = findTests(attempt);
        ExecutorService executor = executors.get(attempt.getProgrammingLanguage());
        attempt.setAttemptStatus(AttemptStatus.RUNNING);
        attemptRepository.save(attempt);
        return CompletableFuture.completedFuture(executor.execute(tests, attempt));
    }

    @Override
    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    protected List<String> findTests(Attempt attempt) {
        File testsDescriptionFile = new File(attempt.getTask().getTestsFile());
        try (Scanner scanner = new Scanner(testsDescriptionFile)) {
            List<String> tests = new ArrayList<>();
            while (scanner.hasNextLine()) {
                tests.add(scanner.nextLine());
            }
            return tests;
        } catch (FileNotFoundException e) {
            log.error("csv for task " + attempt.getTask().getTitle() + " isn't present. Can't run tests for task");
            return null;
        }

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
