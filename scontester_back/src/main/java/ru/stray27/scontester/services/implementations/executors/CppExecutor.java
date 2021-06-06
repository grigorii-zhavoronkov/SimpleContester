package ru.stray27.scontester.services.implementations.executors;

import lombok.SneakyThrows;
import ru.stray27.scontester.annotations.CustomProperty;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.annotations.Executor;

import java.nio.file.Files;
import java.nio.file.Paths;

@Executor(language = ProgrammingLanguage.CPP)
public class CppExecutor extends AbstractExecutor {

    @CustomProperty("executors.home.gpp")
    private String compiler;

    private String sourceCodeFilename;
    private String compiledFilename;

    @Override
    public boolean testExecutablePath() {
        processBuilderService.startProcess(compiler, "--version");
        return !processBuilderService.isError();
    }

    @Override
    protected AttemptStatus preExecute(Attempt attempt) {
        this.sourceCodeFilename = testDirectoryPath + "main.cpp";
        this.compiledFilename = testDirectoryPath + "main";
        copySourceFile(attempt.getSourceCodeFilename(), sourceCodeFilename);
        if (compile()) {
            return AttemptStatus.COMPILED;
        }
        return AttemptStatus.COMPILATION_ERROR;
    }

    @SneakyThrows
    @Override
    protected void postExecute(Attempt attempt) {
        Files.deleteIfExists(Paths.get(this.sourceCodeFilename));
        Files.deleteIfExists(Paths.get(this.compiledFilename));
    }

    @Override
    protected boolean runWithStdInput(String input) {
        processBuilderService.startProcess(this.compiledFilename);
        processBuilderService.writeInput(input);
        return !processBuilderService.isError();
    }

    @Override
    protected boolean runWithFileInput() {
        processBuilderService.startProcess(this.compiledFilename);
        return !processBuilderService.isError();
    }

    private boolean compile() {
        processBuilderService.startProcess(compiler, this.sourceCodeFilename, "-o", compiledFilename);
        return !processBuilderService.isError();
    }

}
