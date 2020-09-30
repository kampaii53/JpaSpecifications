package ru.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entities.ExampleSecondChildEntity;

@Repository
public interface ExampleSecondChildEntityRepository extends JpaRepository<ExampleSecondChildEntity,Long> {
}
