package ru.stray27.scontester.services;

public interface ProcessBuilderService {
    void startProcess(String... args);
    void writeInput(String input);
    boolean isError();
    String getOutput();
}
