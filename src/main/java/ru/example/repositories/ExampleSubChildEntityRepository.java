package ru.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entities.ExampleSubChildEntity;

@Repository
public interface ExampleSubChildEntityRepository extends JpaRepository<ExampleSubChildEntity,Long> {
}
