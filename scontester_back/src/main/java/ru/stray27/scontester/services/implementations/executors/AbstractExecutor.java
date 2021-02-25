package ru.stray27.scontester.services.implementations.executors;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.Test;
import ru.stray27.scontester.services.ExecutorService;
import ru.stray27.scontester.services.ProcessBuilderService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Log4j2
public abstract class AbstractExecutor implements ExecutorService {

    @Value("${test-dir}")
    protected String testDirectoryPath;

    protected AttemptStatus attemptStatus;

    @Autowired
    protected ProcessBuilderService processBuilderService;

    @PostConstruct
    @SneakyThrows
    public void postConstruct() {
        Files.createDirectories(Paths.get(testDirectoryPath));
    }

    /**
     * Used to copy source code to test directory and compile your program
     * @param attempt - current user attempt
     * @return AttemptStatus.COMPILED or AttemptStatus.COMPILATION_ERROR. If compilation error returned then
     * no tests will be provided
     */
    protected abstract AttemptStatus preExecute(Attempt attempt);

    /**
     * Used to delete created files under directory and do some post execution process. Called after tests and if
     * preExecute fails (AttemptStatus.COMPILATION_ERROR)
     * @param attempt - current attempt
     */
    protected abstract void postExecute(Attempt attempt);

    /**
     * Used to run your program.
     * @param inputs - values, which should be written in STDIN
     * @return true - if exit code of process is 0, false otherwise
     */
    protected abstract boolean runWithStdInput(String inputs);

    /**
     * Used to run your program.
     * @return true - if exit code of process is 0, false otherwise
     */
    protected abstract boolean runWithFileInput();

    @Override
    public int execute(List<Test> tests, Attempt attempt) {
        attemptStatus = preExecute(attempt);
        if (attemptStatus == AttemptStatus.COMPILATION_ERROR) {
            postExecute(attempt);
            attempt.setAttemptStatus(AttemptStatus.COMPILATION_ERROR);
            return 0;
        }
        int testsPassed = 0;
        switch (attempt.getTask().getInputType()) {
            case STDIN:
                testsPassed = runStdInTests(tests);
                break;
            case FILE:
                testsPassed = runFileInputTests(tests);
                break;
            default:
        }
        if (testsPassed == tests.size() && attemptStatus == AttemptStatus.COMPILED) {
            attemptStatus = AttemptStatus.OK;
        }
        postExecute(attempt);
        return testsPassed;
    }

    protected int runStdInTests(List<Test> tests) {
        int testsCount = 0;
        for (Test test: tests) {
            testsCount++;
            String input = test.getInput();
            String output = test.getOutput();
            if (!runWithStdInput(input)) {
                attemptStatus = AttemptStatus.RUNTIME_EXCEPTION;
                return testsCount;
            }
            try {
                if (!checkOutput(processBuilderService.getOutput(), output)) {
                    attemptStatus = AttemptStatus.WRONG_ANSWER;
                    return testsCount;
                }
            } catch (NullPointerException e) {
                attemptStatus = AttemptStatus.TIME_LIMIT_EXCEED;
                return testsCount;
            }

        }
        return testsCount;
    }

    protected int runFileInputTests(List<Test> tests) {
        int testsCount = 0;
        for (Test test: tests) {
            createInputOutputFiles();
            testsCount++;
            String input = test.getInput();
            String output = test.getOutput();
            try (FileWriter inputWriter = new FileWriter(testDirectoryPath + "input.txt", false)) {
                inputWriter.write(input);
                inputWriter.flush();
            } catch (IOException e) {
                log.error("Can't open/write to file " + testDirectoryPath + "input.txt");
                log.error(e.getLocalizedMessage());
                attemptStatus = AttemptStatus.INTERNAL_SERVER_ERROR;
                return testsCount;
            }
            if (!runWithFileInput()) {
                attemptStatus = AttemptStatus.RUNTIME_EXCEPTION;
                return testsCount;
            }
            StringBuilder programOutput = new StringBuilder();
            try (Scanner outputReader = new Scanner(new File(testDirectoryPath + "output.txt"))) {
                while (outputReader.hasNextLine()) {
                    programOutput.append(outputReader.nextLine()).append("\n");
                }
            } catch (IOException e) {
                log.error("Can't open/read from file " + testDirectoryPath + "output.txt");
                log.error(e.getLocalizedMessage());
                attemptStatus = AttemptStatus.INTERNAL_SERVER_ERROR;
                return testsCount;
            }
            if (!checkOutput(programOutput.toString(), output)) {
                attemptStatus = AttemptStatus.WRONG_ANSWER;
                return testsCount;
            }
            deleteInputOutputFiles();
        }
        return 0;
    }

    @SneakyThrows
    protected void createInputOutputFiles() {
        Files.createFile(Paths.get(testDirectoryPath + "input.txt"));
        Files.createFile(Paths.get(testDirectoryPath + "output.txt"));
    }

    @SneakyThrows
    protected void deleteInputOutputFiles() {
        Files.deleteIfExists(Paths.get(testDirectoryPath + "input.txt"));
        Files.deleteIfExists(Paths.get(testDirectoryPath + "output.txt"));
    }

    protected void copySourceFile(String source, String destination) {
        processBuilderService.startProcess("cp", source, destination);
    }

    protected boolean checkOutput(String programOutput, String possibleOutput) {
        return programOutput.trim().equals(possibleOutput.trim());
    }

    @Override
    public AttemptStatus getAttemptStatus() {
        return attemptStatus;
    }
}
