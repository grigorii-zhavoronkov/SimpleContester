package ru.stray27.scontester.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stray27.scontester.dto.SenderDto;
import ru.stray27.scontester.entities.Sender;

public class SenderConverter implements EntityConverter<Sender, SenderDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Sender convertToEntity(SenderDto dto) {
        return modelMapper.map(dto, Sender.class);
    }

    @Override
    public SenderDto convertToDto(Sender entity) {
        return modelMapper.map(entity, SenderDto.class);
    }
}