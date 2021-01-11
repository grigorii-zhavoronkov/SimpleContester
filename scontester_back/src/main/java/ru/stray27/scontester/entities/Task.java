package ru.stray27.scontester.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private InputType inputType;

    private Integer timeLimit;

    private Integer memoryLimit;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private Collection<Attempt> attempts;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private Collection<Test> tests;

    @PrePersist
    private void prePersist() {
        if (this.timeLimit == null) {
            this.timeLimit = 1;
        }
        if (this.memoryLimit == null) {
            this.memoryLimit = 64;
        }
        if (this.inputType == null) {
            this.inputType = InputType.STDIN;
        }
    }
}
