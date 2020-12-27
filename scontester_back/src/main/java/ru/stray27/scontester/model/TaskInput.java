package ru.stray27.scontester.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.scontester.entities.InputType;

@Getter
@Setter
@NoArgsConstructor
public class TaskInput {
    private String title;
    private String description;
    private InputType inputType;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String[] tests;
}
