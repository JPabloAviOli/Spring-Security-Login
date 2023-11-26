package com.pavila.TaskProyect.repository;

import com.pavila.TaskProyect.model.dto.TaskResponse;
import com.pavila.TaskProyect.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<TaskResponse> findBy();
    Optional<TaskResponse> findTaskById(Long id);
}
