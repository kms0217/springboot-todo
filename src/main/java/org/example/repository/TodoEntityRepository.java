package org.example.repository;

import org.example.domain.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoEntityRepository extends JpaRepository<TodoEntity, Long> {
}
