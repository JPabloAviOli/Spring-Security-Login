package com.pavila.TaskProyect.service;


import com.pavila.TaskProyect.model.dto.TaskRequest;
import com.pavila.TaskProyect.model.dto.TaskResponse;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

    void save(TaskRequest taskRequest);
    List<TaskResponse> findAll();
    Optional<TaskResponse> findById(Long id);
    void delete(Long id);
    void update(Long id, TaskRequest taskRequest);
    void completedTask(Long id);

}
