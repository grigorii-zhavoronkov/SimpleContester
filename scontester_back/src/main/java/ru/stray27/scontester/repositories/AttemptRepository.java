package ru.stray27.scontester.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.stray27.scontester.entities.Attempt;

import java.util.Optional;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
    @Query("SELECT a FROM Attempt a ORDER BY a.id")
    Optional<Iterable<Attempt>> findAllOrderById();
}
