package ru.stray27.scontester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.dto.TaskInput;
import ru.stray27.scontester.entities.Test;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.SenderRepository;
import ru.stray27.scontester.repositories.TaskRepository;
import ru.stray27.scontester.repositories.TestRepository;
import ru.stray27.scontester.services.FileManagementService;

@RestController
@CrossOrigin("*")
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
    public ResponseEntity deleteSender(@RequestBody Sender sender) {
        senderRepository.deleteById(sender.getUID());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "createSender")
    public ResponseEntity<Sender> createSender(@RequestBody Sender sender) {
        try {
            senderRepository.save(sender);
            return new ResponseEntity<>(sender, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "createTask")
    public ResponseEntity createTask(@RequestBody TaskInput taskInput) {
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
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "getAttempts")
    public ResponseEntity<Iterable<Attempt>> getAllAttempts() {
        return new ResponseEntity<>(attemptRepository.findAll(), HttpStatus.OK);
    }
}
