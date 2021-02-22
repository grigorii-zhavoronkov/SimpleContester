package ru.stray27.scontester.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.scontester.dto.TaskDto;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.entities.Test;
import ru.stray27.scontester.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/task/")
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @JsonView(TaskDto.OutputList.class)
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public ResponseEntity<Iterable<TaskDto>> getAllTasks() {
        Iterable<Task> tasksAll = repository.findAll();
        List<TaskDto> tasks = new ArrayList<>();
        for (Task task : tasksAll) {
            TaskDto dto = new TaskDto();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            tasks.add(dto);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<TaskDto> getTask(Long id) {
        try {
            Task task = repository.findById(id).orElseThrow();
            TaskDto dto = new TaskDto();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setTimeLimit(task.getTimeLimit());
            dto.setMemoryLimit(task.getMemoryLimit());
            dto.setInputType(task.getInputType());
            Test[] tests = task.getTests().stream().sorted((t1,t2) -> (int) (t1.getId() - t2.getId())).limit(2).toArray(Test[]::new);
            tests[0].setTask(null);
            tests[1].setTask(null);
            dto.setTests(tests);
            dto.setDescription(task.getDescription());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "delete")
    public ResponseEntity<TaskDto> deleteTask() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
