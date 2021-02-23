package ru.stray27.scontester.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stray27.scontester.dto.AttemptDto;
import ru.stray27.scontester.entities.Attempt;

public class AttemptConverter implements EntityConverter<Attempt, AttemptDto>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Attempt convertToEntity(AttemptDto dto) {
        modelMapper.map(dto, Attempt.class);
        // add task, attempts, sender
        return null;
    }

    @Override
    public AttemptDto convertToDto(Attempt entity) {
        return modelMapper.map(entity, AttemptDto.class);
    }
}
