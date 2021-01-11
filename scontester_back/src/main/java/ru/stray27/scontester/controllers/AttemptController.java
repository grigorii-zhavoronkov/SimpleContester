package ru.stray27.scontester.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.dto.AttemptInput;
import ru.stray27.scontester.dto.AttemptOutput;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.SenderRepository;
import ru.stray27.scontester.repositories.TaskRepository;
import ru.stray27.scontester.services.FileManagementService;
import ru.stray27.scontester.services.TaskExecutorService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.Executor;

@Log4j2
@RestController
@CrossOrigin("*")
@RequestMapping("/attempt/")
public class AttemptController {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private TaskExecutorService taskExecutorService;

    @Autowired
    private FileManagementService fileManagementService;

    @Autowired
    private SenderRepository senderRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Executor asyncExecutor;

    @PostMapping("sendAttempt")
    public ResponseEntity<Object> sendAttempt(@RequestBody AttemptInput attemptInput,
                                      HttpServletRequest request) {
        Sender sender;
        try {
            sender = senderRepository.findByUID(attemptInput.getUid()).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("Sender for UID " + attemptInput.getUid() + " wasn't found");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        Task task;
        try {
            task = taskRepository.findById(attemptInput.getTaskId()).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("Can't find task with id " + attemptInput.getTaskId());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        Attempt attempt = new Attempt();
        attempt.setSender(sender);
        attempt.setTask(task);
        attempt.setIpAddr(request.getRemoteAddr());
        attempt.setProgrammingLanguage(attemptInput.getProgrammingLanguage());
        attemptRepository.save(attempt);
        attempt.setSourceCodeFilename(
                fileManagementService.saveSourceCodeFile(
                        attemptInput.getTaskId(),
                        attemptInput.getUid(),
                        attempt.getId(),
                        attemptInput.getCode()
                )
        );
        attemptRepository.save(attempt);
        taskExecutorService.setAttempt(attempt);
        asyncExecutor.execute(taskExecutorService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getAttempts")
    public ResponseEntity<Iterable<AttemptOutput>> getAttempts() {
        try {
            Iterable<Attempt> attempts = attemptRepository.findAllOrderById().orElseThrow();
            List<AttemptOutput> outputAttempts = new ArrayList<>();
            int attemptCounter = 0;
            for (Attempt attempt : attempts) {
                if (attemptCounter++ < 30) {
                    AttemptOutput attemptOutput = new AttemptOutput();
                    attemptOutput.setId(attempt.getId());
                    attemptOutput.setAttemptStatus(attempt.getAttemptStatus());
                    attemptOutput.setLastTestNumber(attempt.getLastTestNumber());
                    attemptOutput.setSenderName(attempt.getSender().getName());
                    outputAttempts.add(attemptOutput);
                } else {
                    break;
                }
            }
            return new ResponseEntity<>(outputAttempts, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Can't resolve attempts from database");
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getSourceCode")
    public ResponseEntity<List<String>> getSourceCode(@RequestBody Attempt attempt) {
        try {
            attempt = attemptRepository.findById(attempt.getId()).orElseThrow();
            return new ResponseEntity<>(fileManagementService.readFile(attempt.getSourceCodeFilename()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
