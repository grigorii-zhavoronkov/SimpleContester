package ru.stray27.scontester.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.dto.TaskDto;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.entities.Test;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.TestRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class TaskConverter implements EntityConverter<Task, TaskDto>{

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AttemptRepository attemptRepository;
    @Autowired
    private TestRepository testRepository;

    @Override
    public Task convertToEntity(TaskDto dto) {
        Task task = mapper.map(dto, Task.class);

        Set<Attempt> attempts = new HashSet<>((Collection<? extends Attempt>) attemptRepository.findAllByTaskId(dto.getId()));
        if (dto.getTests().length == 0) {
            Set<Test> tests = new HashSet<>((Collection<? extends Test>) testRepository.findAllByTaskId(dto.getId()));
            task.setTests(tests);
        }

        task.setAttempts(attempts);
        return task;
    }

    @Override
    public TaskDto convertToDto(Task entity) {
        return mapper.map(entity, TaskDto.class);
    }
}
