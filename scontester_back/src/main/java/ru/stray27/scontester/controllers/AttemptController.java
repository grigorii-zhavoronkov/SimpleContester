package ru.stray27.scontester.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.scontester.dto.AttemptDto;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.SenderRepository;
import ru.stray27.scontester.repositories.TaskRepository;
import ru.stray27.scontester.services.FileManagementService;
import ru.stray27.scontester.services.TaskExecutorService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
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

    @JsonView(AttemptDto.AttemptInput.class)
    @PostMapping("sendAttempt")
    public ResponseEntity<Object> sendAttempt(@RequestBody AttemptDto attemptDto,
                                      HttpServletRequest request) {
        Sender sender;
        try {
            sender = senderRepository.findByUID(attemptDto.getUid()).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("Sender for UID " + attemptDto.getUid() + " wasn't found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        Task task;
        try {
            task = taskRepository.findById(attemptDto.getTaskId()).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("Can't find task with id " + attemptDto.getTaskId());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        String ipAddr = request.getHeader("X-FORWARDED-FOR");
        ipAddr = (ipAddr == null || ipAddr.equals("")) ? request.getRemoteAddr() : ipAddr;
        Attempt attempt = new Attempt();
        attempt.setSender(sender);
        attempt.setTask(task);
        attempt.setIpAddr(ipAddr);
        attempt.setAttemptStatus(AttemptStatus.IN_QUEUE);
        attempt.setProgrammingLanguage(attemptDto.getProgrammingLanguage());
        attemptRepository.save(attempt);
        attempt.setSourceCodeFilename(
                fileManagementService.saveSourceCodeFile(
                        attemptDto.getTaskId(),
                        attemptDto.getUid(),
                        attempt.getId(),
                        attemptDto.getCode()
                )
        );
        attemptRepository.save(attempt);
        taskExecutorService.setAttempt(attempt);
        asyncExecutor.execute(taskExecutorService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonView(AttemptDto.AttemptOutput.class)
    @GetMapping("getAttempts")
    public ResponseEntity<?> getAttempts() {
        try {
            List<Attempt> attempts = (List<Attempt>) attemptRepository.findAllOrderById().orElseThrow();
            attempts = attempts.stream().limit(30).collect(Collectors.toList());
            List<AttemptDto> outputAttempts = new ArrayList<>();
            for (Attempt attempt : attempts) {
                AttemptDto attemptOutput = new AttemptDto();
                attemptOutput.setId(attempt.getId());
                attemptOutput.setTaskId(attempt.getTask().getId());
                attemptOutput.setTaskTitle(attempt.getTask().getTitle());
                attemptOutput.setStatus(attempt.getAttemptStatus());
                attemptOutput.setLastTestNumber(attempt.getLastTestNumber());
                attemptOutput.setSenderName(attempt.getSender().getName());
                outputAttempts.add(attemptOutput);
            }
            return new ResponseEntity<>(outputAttempts, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Can't resolve attempts from database");
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getSourceCode")
    public ResponseEntity<?> getSourceCode(@RequestBody Attempt attempt) {
        try {
            attempt = attemptRepository.findById(attempt.getId()).orElseThrow();
            return new ResponseEntity<>(fileManagementService.readFile(attempt.getSourceCodeFilename()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
