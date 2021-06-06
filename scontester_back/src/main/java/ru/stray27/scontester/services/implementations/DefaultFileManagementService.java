package ru.stray27.scontester.services.implementations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.annotations.CustomProperty;
import ru.stray27.scontester.services.FileManagementService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
@Service
public class DefaultFileManagementService implements FileManagementService {

    @CustomProperty("dir.src")
    private String srcDir;

    @PostConstruct
    @SneakyThrows
    private void postConstruct() {
        Files.createDirectories(Paths.get(srcDir));
    }

    @Override
    public String saveSourceCodeFile(Long taskId, String uid, Long attemptId, String sourceCode) {
        File file = new File(constructSourceCodeFilename(taskId, uid, attemptId));
        return writeToFile(file, sourceCode);
    }

    @Override
    public List<String> readFile(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            return null;
        }
    }

    private String writeToFile(File file, String content) {
        try {
            if (!file.exists() && !file.createNewFile()) {
                throw new IOException("Can't create file " + file.getName());
            }
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            log.error("Can't create/write to file for attempt");
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    private String constructSourceCodeFilename(Long taskId, String uid, Long attemptId) {
        return srcDir + taskId + "_" + uid + "_" + attemptId + ".txt";
    }
}
