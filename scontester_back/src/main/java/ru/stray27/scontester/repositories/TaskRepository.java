package ru.stray27.scontester.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.stray27.scontester.entities.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
