package ru.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.example.entities.ExampleEntity;

@Repository
public interface ExampleEntityRepository extends JpaRepository<ExampleEntity,Long>, JpaSpecificationExecutor<ExampleEntity> {
}
