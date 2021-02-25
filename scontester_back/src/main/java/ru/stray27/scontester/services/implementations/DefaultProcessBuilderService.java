package ru.stray27.scontester.services.implementations;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.services.ProcessBuilderService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DefaultProcessBuilderService implements ProcessBuilderService {

    @Getter
    private Process process;
    @Getter
    private InputStream processInputStream;
    @Getter
    private OutputStream processOutputStream;

    @Value("${test-dir}")
    protected String testDirectoryPath;

    private String output;

    private Thread killThread;

    @SneakyThrows
    @Override
    public void startProcess(String... args) {
        ProcessBuilder processBuilder = new ProcessBuilder(args).redirectOutput(new File(testDirectoryPath + "output"));
        log.info("CREATING PROCESS");
        StringBuilder command = new StringBuilder();
        for (String arg : args) {
            command.append(arg).append(" ");
        }
        log.info(command.toString());
        this.process = processBuilder.start();
        this.processInputStream = process.getInputStream();
        this.processOutputStream = process.getOutputStream();
        this.killThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                process.waitFor(5, TimeUnit.SECONDS);
                process.destroy();
                process.waitFor();
            }
        });
    }

    @Override
    public void writeInput(String input) {
        try {
            process.getOutputStream().write(input.getBytes(StandardCharsets.UTF_8));
            process.getOutputStream().write('\n');
            process.getOutputStream().flush();
            killThread.start();
        } catch (IOException e) {
            log.error("Couldn't write input to process");
            log.error(e.getLocalizedMessage());
            process.destroy();
        }
    }


    @SneakyThrows
    @Override
    public boolean isError() {
        if (killThread.getState() == Thread.State.NEW) {
            return process.waitFor() != 0;
        } else {
            killThread.join();
            return process.exitValue() != 0;
        }
    }

    @SneakyThrows
    @Override
    public String getOutput() {
        killThread.join();
        List<String> outputs = Files.readAllLines(Path.of(testDirectoryPath + "output"));
        return String.join("", outputs);
    }
}
