package com.researchecosystems.todoapp.controller;

import com.researchecosystems.todoapp.model.response.task.TaskResponse;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.researchecosystems.todoapp.service.TaskService;
import com.researchecosystems.todoapp.model.request.task.UpdateTaskRequest;
import com.researchecosystems.todoapp.model.request.task.CreateTaskRequest;

import com.researchecosystems.todoapp.service.AuthenticationService;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final AuthenticationService authenticationService;

    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable String id) {
        return taskService.getTask(id);
    }

    @PostMapping
    public void addTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        taskService.addTask(createTaskRequest,authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("/{id}")
    public void updateTask(@Valid @RequestBody UpdateTaskRequest updateTaskRequest, @PathVariable String id) {
        taskService.updateTask(updateTaskRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }
}
