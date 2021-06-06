package ru.stray27.scontester.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.dto.AttemptDto;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.repositories.SenderRepository;
import ru.stray27.scontester.repositories.TaskRepository;

@Service
public class AttemptConverter implements EntityConverter<Attempt, AttemptDto>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SenderRepository senderRepository;

    @Override
    public Attempt convertToEntity(AttemptDto dto) {
        Attempt attempt = modelMapper.map(dto, Attempt.class);
        Task task = taskRepository.findById(dto.getTaskId()).orElse(null);
        Sender sender = senderRepository.findByUID(dto.getUid()).orElse(null);
        attempt.setTask(task);
        attempt.setSender(sender);
        return attempt;
    }

    @Override
    public AttemptDto convertToDto(Attempt entity) {
        return modelMapper.map(entity, AttemptDto.class);
    }
}
