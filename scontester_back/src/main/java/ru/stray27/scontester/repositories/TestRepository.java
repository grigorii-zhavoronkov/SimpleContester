package ru.stray27.scontester.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.stray27.scontester.entities.Test;

public interface TestRepository extends CrudRepository<Test, Long> {
}
