package ru.stray27.scontester.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stray27.scontester.dto.SenderDto;
import ru.stray27.scontester.entities.Attempt;
import ru.stray27.scontester.entities.Sender;
import ru.stray27.scontester.repositories.AttemptRepository;
import ru.stray27.scontester.repositories.SenderRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class SenderConverter implements EntityConverter<Sender, SenderDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AttemptRepository attemptRepository;

    @Override
    public Sender convertToEntity(SenderDto dto) {
        Sender sender = modelMapper.map(dto, Sender.class);
        Set<Attempt> attempts = new HashSet<>((Collection<? extends Attempt>) attemptRepository.findAllBySenderUID(dto.getUID()));
        sender.setAttempts(attempts);
        return sender;
    }

    @Override
    public SenderDto convertToDto(Sender entity) {
        return modelMapper.map(entity, SenderDto.class);
    }
}