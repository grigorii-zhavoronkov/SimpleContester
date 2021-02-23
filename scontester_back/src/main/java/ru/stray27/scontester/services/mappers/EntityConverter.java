package ru.stray27.scontester.services.mappers;

import org.springframework.stereotype.Service;

@Service
public interface EntityConverter<E, D> {
    E convertToEntity(D dto);
    D convertToDto(E entity);
}
