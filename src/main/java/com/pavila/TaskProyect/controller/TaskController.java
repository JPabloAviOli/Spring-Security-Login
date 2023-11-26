package com.pavila.TaskProyect.controller;

import com.pavila.TaskProyect.model.dto.TaskRequest;
import com.pavila.TaskProyect.model.dto.TaskResponse;
import com.pavila.TaskProyect.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping("all")
    public ResponseEntity<List<TaskResponse>> findAll(){
        List<TaskResponse> taskList = taskService.findAll();
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<TaskResponse>> findById(@PathVariable Long id){
       Optional <TaskResponse> task = taskService.findById(id);
       return ResponseEntity.ok(task);
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid TaskRequest taskRequest){
        taskService.save(taskRequest);
    }


    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateById(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest){
       taskService.update(id, taskRequest);
    }

    @PutMapping("/{id}/completed")
    @ResponseStatus(HttpStatus.OK)
    public void completedTaskById(@PathVariable Long id){
        taskService.completedTask(id);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }
}
