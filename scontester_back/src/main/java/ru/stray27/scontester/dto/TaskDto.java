package ru.stray27.scontester.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.scontester.entities.InputType;
import ru.stray27.scontester.entities.Test;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    public interface Input {}
    public interface OutputList {}
    public interface Output {}

    private Long id;
    private String title;
    private String description;
    private InputType inputType;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Test[] tests;
}
