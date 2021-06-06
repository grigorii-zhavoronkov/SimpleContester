package ru.stray27.scontester.services.implementations.executors;

import lombok.SneakyThrows;
import ru.stray27.scontester.annotations.CustomProperty;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.annotations.Executor;

import java.nio.file.Files;
import java.nio.file.Paths;

@Executor(language = ProgrammingLanguage.C)
public class CExecutor extends AbstractExecutor {

    @CustomProperty("executors.home.gcc")
    private String GCC;

    private String srcFilename;
    private String compiledFilename;

    @Override
    public boolean testExecutablePath() {
        processBuilderService.startProcess(GCC, "--version");
        return !processBuilderService.isError();
    }

    @Override
    protected AttemptStatus preExecute(Attempt attempt) {
        this.srcFilename = testDirectoryPath + "main.c";
        this.compiledFilename = testDirectoryPath + "main";
        copySourceFile(attempt.getSourceCodeFilename(), srcFilename);
        if (!compile()) {
            return AttemptStatus.COMPILATION_ERROR;
        }
        return AttemptStatus.COMPILED;
    }

    @SneakyThrows
    @Override
    protected void postExecute(Attempt attempt) {
        Files.deleteIfExists(Paths.get(testDirectoryPath + "main.c"));
        Files.deleteIfExists(Paths.get(testDirectoryPath + "main"));
    }

    protected boolean compile() {
        processBuilderService.startProcess(GCC, srcFilename, "-o", compiledFilename);
        return !processBuilderService.isError();
    }

    protected boolean runWithStdInput(String input) {
        processBuilderService.startProcess(compiledFilename);
        processBuilderService.writeInput(input);
        return !processBuilderService.isError();
    }

    @Override
    protected boolean runWithFileInput() {
        processBuilderService.startProcess(compiledFilename);
        return !processBuilderService.isError();
    }
}
