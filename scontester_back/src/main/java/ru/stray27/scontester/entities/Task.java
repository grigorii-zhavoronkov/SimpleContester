package ru.stray27.scontester.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    private String title;

    @Column(columnDefinition = "varchar(2000)")
    private String description;

    @Enumerated(EnumType.STRING)
    private InputType inputType;

    private Integer timeLimit;

    private Integer memoryLimit;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "task")
    private Set<Attempt> attempts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Set<Test> tests = new HashSet<>();

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
