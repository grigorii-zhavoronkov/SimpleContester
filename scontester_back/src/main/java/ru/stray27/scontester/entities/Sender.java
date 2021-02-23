package ru.stray27.scontester.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Sender {
    @Id
    @GenericGenerator(name = "uid_generator", strategy = "ru.stray27.scontester.utils.UIDGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uid_generator")
    private String UID;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_uid")
    private Set<Attempt> attempts = new HashSet<>();
}
