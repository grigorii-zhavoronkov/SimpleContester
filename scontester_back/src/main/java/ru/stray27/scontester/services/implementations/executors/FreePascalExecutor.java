package ru.stray27.scontester.services.implementations.executors;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;
import ru.stray27.scontester.services.annotations.Executor;

import java.nio.file.Files;
import java.nio.file.Paths;

@Executor(language = ProgrammingLanguage.FREE_PASCAL)
public class FreePascalExecutor extends AbstractExecutor {

    @Value("${FPC}")
    private String compiler;

    private String sourceCodeFilename;
    private String compiledFilename;

    @Override
    public boolean testExecutablePath() {
        processBuilderService.startProcess(compiler, "-iW");
        return !processBuilderService.isError();
    }

    @Override
    protected AttemptStatus preExecute(Attempt attempt) {
        this.sourceCodeFilename = testDirectoryPath + "program.pas";
        this.compiledFilename = testDirectoryPath + "program";
        copySourceFile(attempt.getSourceCodeFilename(), sourceCodeFilename);
        if (!compile()) {
            return AttemptStatus.COMPILATION_ERROR;
        }
        return AttemptStatus.COMPILED;
    }

    @SneakyThrows
    @Override
    protected void postExecute(Attempt attempt) {
        Files.deleteIfExists(Paths.get(sourceCodeFilename));
        Files.deleteIfExists(Paths.get(compiledFilename));
        Files.deleteIfExists(Paths.get(testDirectoryPath + "program.o"));
    }

    @Override
    protected boolean runWithStdInput(String[] inputs) {
        processBuilderService.startProcess(compiledFilename);
        for (String input : inputs) {
            processBuilderService.writeInput(input);
        }
        return !processBuilderService.isError();
    }

    @Override
    protected boolean runWithFileInput() {
        processBuilderService.startProcess(compiledFilename);
        return !processBuilderService.isError();
    }

    protected boolean compile() {
        processBuilderService.startProcess(compiler, sourceCodeFilename);
        return !processBuilderService.isError();
    }
}
