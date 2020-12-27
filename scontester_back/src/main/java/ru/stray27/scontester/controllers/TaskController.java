package ru.stray27.scontester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.repositories.TaskRepository;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task/")
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        Iterable<Task> tasks = repository.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(@RequestParam Long id) {
        try {
            Task task = repository.findById(id).orElseThrow();
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
