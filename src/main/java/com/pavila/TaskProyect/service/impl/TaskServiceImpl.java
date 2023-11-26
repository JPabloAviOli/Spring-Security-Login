package com.pavila.TaskProyect.service.impl;

import com.pavila.TaskProyect.exception.ObjectNotFoundException;
import com.pavila.TaskProyect.model.dto.TaskRequest;
import com.pavila.TaskProyect.model.dto.TaskResponse;
import com.pavila.TaskProyect.model.dto.UserProfileResponse;
import com.pavila.TaskProyect.model.entity.Task;
import com.pavila.TaskProyect.model.entity.security.User;
import com.pavila.TaskProyect.repository.ITaskRepository;
import com.pavila.TaskProyect.service.ITaskService;
import com.pavila.TaskProyect.service.IUserService;
import com.pavila.TaskProyect.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    IUserService userService;

    @Override
    public void save(TaskRequest taskRequest) {

        UserProfileResponse findLoggedInUser = authenticationService.findLoggedInUser();
        User user = userService.findOneByUsername(findLoggedInUser.getUsername()).get();

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .createdDate(new Date())
                .finishedDate(taskRequest.getFinishedDate())
                .priority(taskRequest.getPriority())
                .completed(false)
                .assignee(taskRequest.getAssignee())
                .category(taskRequest.getCategory())
                .createdByUser(user)

                .build();

        taskRepository.save(task);

    }

    @Override
    public List<TaskResponse> findAll() {
      List<TaskResponse> taskList = taskRepository.findBy();
      if(taskList.isEmpty()){
          throw new ObjectNotFoundException("The task list is empty");
      }
        return taskList;
    }

    @Override
    public Optional<TaskResponse> findById(Long id) {
        Optional<TaskResponse> taskDtoResponse = taskRepository.findTaskById(id);
        if(taskDtoResponse.isPresent()){

            return taskDtoResponse;
        }
        throw new ObjectNotFoundException("Task not found with id: " + id);

    }

    @Override
    public void delete(Long id) {
    Optional<Task> task = taskRepository.findById(id);
    if(task.isPresent()){
        taskRepository.deleteById(id);
    }
        throw new ObjectNotFoundException("Task not found with id: " + id);
    }


    @Override
    public void update(Long id, TaskRequest taskRequest) {
        Task taskDB = taskRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Task not found with id: " + id));
        taskDB.setId(id);
        taskDB.setTitle(taskRequest.getTitle());
        taskDB.setDescription(taskRequest.getDescription());
        taskDB.setFinishedDate(taskRequest.getFinishedDate());
        taskDB.setPriority(taskRequest.getPriority());
        taskDB.setAssignee(taskRequest.getAssignee());
        taskDB.setCategory(taskRequest.getCategory());
        taskRepository.save(taskDB);

    }

    @Override
    public void completedTask(Long id) {
        Task taskFromDB = taskRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("Task not found with id " + id));
      taskFromDB.setCompleted(true);
      taskRepository.save(taskFromDB);
    }

}
