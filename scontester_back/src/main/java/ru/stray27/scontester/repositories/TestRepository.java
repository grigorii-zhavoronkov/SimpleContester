package ru.stray27.scontester.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.stray27.scontester.entities.Task;
import ru.stray27.scontester.entities.Test;

public interface TestRepository extends CrudRepository<Test, Long> {
    @Query("SELECT t FROM Test t WHERE t.task.id = :taskId")
    Iterable<Test> findAllByTaskId(Long taskId);
}
