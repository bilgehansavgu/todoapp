package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.exception.BusinessException;
import com.researchecosystems.todoapp.exception.ErrorCode;

import com.researchecosystems.todoapp.entity.Task;
import com.researchecosystems.todoapp.entity.User;

import com.researchecosystems.todoapp.model.request.task.CreateTaskRequest;
import com.researchecosystems.todoapp.model.request.task.UpdateTaskRequest;
import com.researchecosystems.todoapp.model.response.task.TaskResponse;
import com.researchecosystems.todoapp.repository.TaskRepository;
import com.researchecosystems.todoapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Page<TaskResponse> getAllTasks(Pageable pageable) {

        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.map(TaskResponse::fromEntity);
    }

    public Page<TaskResponse> getAllTasksByOwnerId(Pageable pageable, String ownerId) {
        User existingUser = userRepository.findById(ownerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that."));

        Page<Task> tasks = taskRepository.findAllByOwnerId(pageable,ownerId);
        return tasks.map(TaskResponse::fromEntity);
    }

    public TaskResponse getTask(String id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no task by that ID."));

        return TaskResponse.fromEntity(existingTask);
    }

    public void addTask(CreateTaskRequest createTaskRequest, String ownerId) {
        User existingUser = userRepository.findById(ownerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that."));

        Task newTask = new Task();

        newTask.setDescription(createTaskRequest.getDescription());
        newTask.setDue(createTaskRequest.getDue());
        newTask.setCompleted(false);
        newTask.setCreatedDate(ZonedDateTime.now());
        newTask.setModifiedDate(ZonedDateTime.now());
        newTask.setOwner(existingUser);

        taskRepository.save(newTask);
    }
    public void updateTask(UpdateTaskRequest updateTaskRequest, String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no task like that"));

        task.setDescription(updateTaskRequest.getDescription());
        task.setDue(updateTaskRequest.getDue());
        task.setCompleted(updateTaskRequest.isCompleted());
        task.setModifiedDate(ZonedDateTime.now());

        taskRepository.save(task);
    }

    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no task like that."));

        taskRepository.delete(task);
    }
}
