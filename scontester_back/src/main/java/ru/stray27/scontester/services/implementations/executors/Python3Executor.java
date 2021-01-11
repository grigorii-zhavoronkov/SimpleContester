package ru.stray27.scontester.services.implementations.executors;

import org.springframework.beans.factory.annotation.Value;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.services.annotations.Executor;

@Executor(language = ProgrammingLanguage.PYTHON3)
public class Python3Executor extends Python2Executor {

    @Value("${python3}")
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
