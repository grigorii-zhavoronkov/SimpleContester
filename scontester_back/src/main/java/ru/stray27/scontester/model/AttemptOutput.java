package ru.stray27.scontester.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.scontester.entities.AttemptStatus;

@Getter
@Setter
@NoArgsConstructor
public class AttemptOutput {
    private Long id;
    private String senderName;
    private AttemptStatus attemptStatus;
    private Integer lastTestNumber;
}
