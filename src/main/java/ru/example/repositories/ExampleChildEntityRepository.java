package ru.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entities.ExampleChildEntity;

@Repository
public interface ExampleChildEntityRepository extends JpaRepository<ExampleChildEntity,Long> {
}
