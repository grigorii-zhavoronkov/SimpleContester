package ru.stray27.scontester.services.implementations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.services.ProcessBuilderService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Log4j2
@Service
public class DefaultProcessBuilderService implements ProcessBuilderService {

    Process process;
    InputStream processInputStream;
    OutputStream processOutputStream;

    @SneakyThrows
    @Override
    public void startProcess(String... args) {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        this.process = processBuilder.start();
        this.processInputStream = process.getInputStream();
        this.processOutputStream = process.getOutputStream();
    }

    @Override
    public void writeInput(String input) {
        try {
            process.getOutputStream().write(input.getBytes(StandardCharsets.UTF_8));
            process.getOutputStream().write('\n');
            process.getOutputStream().flush();
        } catch (IOException e) {
            log.error("Couldn't write input to process");
            log.error(e.getLocalizedMessage());
        }
    }

    @SneakyThrows
    @Override
    public boolean isError() {
        return process.waitFor() != 0;
    }

    @Override
    public String getOutput() {
        try {
            return new String(processInputStream.readAllBytes());
        } catch (IOException e) {
            log.error("Couldn't read from process");
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
