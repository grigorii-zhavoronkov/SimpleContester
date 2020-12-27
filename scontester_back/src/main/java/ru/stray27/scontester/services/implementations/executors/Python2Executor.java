package ru.stray27.scontester.services.implementations.executors;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.services.annotations.Executor;

import java.nio.file.Files;
import java.nio.file.Paths;

@Executor(language = ProgrammingLanguage.PYTHON2)
public class Python2Executor extends AbstractExecutor {

    @Value("${python2}")
    private String runner;

    protected String sourceCodeFilename;

    @Override
    public boolean testExecutablePath() {
        processBuilderService.startProcess(runner, "--version");
        return !processBuilderService.isError();
    }

    @Override
    protected AttemptStatus preExecute(Attempt attempt) {
        this.sourceCodeFilename = testDirectoryPath + "main.py";
        copySourceFile(attempt.getSourceCodeFilename(), sourceCodeFilename);
        return AttemptStatus.COMPILED;
    }

    @SneakyThrows
    @Override
    protected void postExecute(Attempt attempt) {
        Files.deleteIfExists(Paths.get(sourceCodeFilename));
    }

    @Override
    protected boolean runWithStdInput(String[] inputs) {
        processBuilderService.startProcess(runner, sourceCodeFilename);
        for (String input : inputs) {
            processBuilderService.writeInput(input);
        }
        return !processBuilderService.isError();
    }

    @Override
    protected boolean runWithFileInput() {
        processBuilderService.startProcess(runner, sourceCodeFilename);
        return !processBuilderService.isError();
    }
}
