package ru.stray27.scontester.services.implementations.executors;

import ru.stray27.scontester.annotations.CustomProperty;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.annotations.Executor;

@Executor(language = ProgrammingLanguage.PYTHON3)
public class Python3Executor extends Python2Executor {

    @CustomProperty("executors.home.python3")
    private String runner;

    @Override
    public boolean testExecutablePath() {
        processBuilderService.startProcess(runner, "--version");
        return !processBuilderService.isError();
    }

    @Override
    protected boolean runWithStdInput(String input) {
        processBuilderService.startProcess(runner, sourceCodeFilename);
        processBuilderService.writeInput(input);
        return !processBuilderService.isError();
    }

    @Override
    protected boolean runWithFileInput() {
        processBuilderService.startProcess(runner, sourceCodeFilename);
        return !processBuilderService.isError();
    }
}
