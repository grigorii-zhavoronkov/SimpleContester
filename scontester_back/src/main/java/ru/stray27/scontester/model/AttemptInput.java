package ru.stray27.scontester.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.scontester.entities.ProgrammingLanguage;

@Getter
@Setter
@NoArgsConstructor
public class AttemptInput {
    private String uid;
    private String[] src;
    private ProgrammingLanguage programmingLanguage;
    private Long taskId;
}
