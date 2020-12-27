package ru.stray27.scontester.services;

import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;

import java.util.concurrent.CompletableFuture;

public interface TaskExecutorService extends Runnable {
    CompletableFuture<Integer> runTests();
    void setAttempt(Attempt attempt);
}
