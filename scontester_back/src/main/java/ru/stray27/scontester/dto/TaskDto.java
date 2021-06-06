package ru.stray27.scontester.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.scontester.entities.InputType;
import ru.stray27.scontester.entities.Test;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private InputType inputType;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Test[] tests;
}
