package ru.stray27.scontester.entities;

public enum AttemptStatus {
    OK,
    COMPILATION_ERROR,
    COMPILED,
    WRONG_ANSWER,
    TIME_LIMIT_EXCEED,
    MEMORY_LIMIT_EXCEED,
    RUNTIME_EXCEPTION,
    RUNNING,
    IN_QUEUE
}
