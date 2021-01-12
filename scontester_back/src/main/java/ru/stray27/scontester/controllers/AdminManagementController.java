package ru.stray27.scontester.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.scontester.dto.TaskDto;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.entities.Test;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.SenderRepository;
import ru.stray27.scontester.repositories.TaskRepository;
import ru.stray27.scontester.repositories.TestRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin/api/")
public class AdminManagementController {

    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AttemptRepository attemptRepository;
    @Autowired
    private TestRepository testRepository;


    @PostMapping(value = "deleteSender")
    public ResponseEntity<?> deleteSender(@RequestBody Sender sender) {
        try {
            senderRepository.deleteById(sender.getUID());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "createSender")
    public ResponseEntity<?> createSender(@RequestBody Sender sender) {
        try {
            senderRepository.save(sender);
            return new ResponseEntity<>(sender, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @JsonView(TaskDto.Input.class)
    @PostMapping(value = "createTask")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskInput) {
        try {
            Task task = new Task();
            task.setTitle(taskInput.getTitle());
            task.setDescription(taskInput.getDescription());
            task.setInputType(taskInput.getInputType());
            task.setTimeLimit(taskInput.getTimeLimit());
            task.setMemoryLimit(taskInput.getMemoryLimit());
            taskRepository.save(task);
            for (Test input_test : taskInput.getTests()) {
                Test test = new Test();
                test.setTask(task);
                test.setInput(input_test.getInput());
                test.setOutput(input_test.getOutput());
                testRepository.save(test);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "getAttempts")
    public ResponseEntity<Iterable<Attempt>> getAllAttempts() {
        return new ResponseEntity<>(attemptRepository.findAll(), HttpStatus.OK);
    }
}
