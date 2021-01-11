package ru.stray27.scontester.services;


import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.Test;

import java.util.List;

public interface ExecutorService {
    int execute(List<Test> tests, Attempt attempt);

    boolean testExecutablePath();
    AttemptStatus getAttemptStatus();
}
