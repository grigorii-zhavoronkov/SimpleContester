package ru.stray27.scontester.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.scontester.entities.AttemptStatus;
import ru.stray27.scontester.entities.ProgrammingLanguage;

@Getter
@Setter
@NoArgsConstructor
public class AttemptDto {
    public interface AttemptInput {}

    public interface AttemptOutput {}

    @JsonView({AttemptInput.class, AttemptOutput.class})
    private Long taskId;

    @JsonView({AttemptInput.class})
    private String uid;

    @JsonView({AttemptInput.class})
    private String code;

    @JsonView({AttemptOutput.class})
    private Long id;

    @JsonView({AttemptInput.class})
    private ProgrammingLanguage programmingLanguage;

    @JsonView({AttemptOutput.class})
    private String senderName;

    @JsonView({AttemptOutput.class})
    private String taskTitle;

    @JsonView({AttemptOutput.class})
    private AttemptStatus status;

    @JsonView({AttemptOutput.class})
    private Integer lastTestNumber;
}
