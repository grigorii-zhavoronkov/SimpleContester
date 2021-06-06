package ru.stray27.scontester.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.stray27.scontester.entities.Attempt;

import java.util.Optional;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
    @Query("SELECT a FROM Attempt a ORDER BY a.id DESC")
    Optional<Iterable<Attempt>> findAllOrderById();

    @Query("SELECT a FROM Attempt a WHERE a.sender.UID = :senderUID AND a.attemptStatus='OK'")
    Iterable<Attempt> findAllOkBySenderUID(String senderUID);

    @Query("SELECT a FROM Attempt a WHERE a.sender.UID = :senderUID")
    Iterable<Attempt> findAllBySenderUID(String senderUID);

    @Query("SELECT a FROM Attempt a WHERE a.task.id = :taskId")
    Iterable<Attempt> findAllByTaskId(Long taskId);
}
