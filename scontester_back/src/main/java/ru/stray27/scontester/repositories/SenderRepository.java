package ru.stray27.scontester.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stray27.scontester.entities.Sender;

import java.util.Optional;

@Repository
public interface SenderRepository extends CrudRepository<Sender, String> {
    Optional<Sender> findByUID(String UID);
    Optional<Sender> findByName(String name);
    @Transactional
    void deleteByUID(String UID);
}
