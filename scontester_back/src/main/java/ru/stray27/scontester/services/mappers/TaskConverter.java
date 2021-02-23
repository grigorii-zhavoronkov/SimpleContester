package ru.stray27.scontester.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stray27.scontester.dto.TaskDto;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.TestRepository;

public class TaskConverter implements EntityConverter<Task, TaskDto>{

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AttemptRepository attemptRepository;
    @Autowired
    private TestRepository testRepository;

    @Override
    public Task convertToEntity(TaskDto dto) {
        return null;
    }

    @Override
    public TaskDto convertToDto(Task entity) {
        return mapper.map(entity, TaskDto.class);
    }
}
