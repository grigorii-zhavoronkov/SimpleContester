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

    @JsonView({Output.class, OutputList.class})
    private Long id;
    @JsonView({Input.class, Output.class, OutputList.class})
    private String title;
    @JsonView({Input.class, Output.class})
    private String description;
    @JsonView({Input.class, Output.class})
    private InputType inputType;
    @JsonView({Input.class, Output.class})
    private Integer timeLimit;
    @JsonView({Input.class, Output.class})
    private Integer memoryLimit;
    @JsonView({Input.class, Output.class})
    private Test[] tests;
}
