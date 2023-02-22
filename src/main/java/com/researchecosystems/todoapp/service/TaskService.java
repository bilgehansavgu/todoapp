package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.exception.BusinessException;
import com.researchecosystems.todoapp.exception.ErrorCode;

import com.researchecosystems.todoapp.entity.Task;

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

    public Page<TaskResponse> getAllTasksByUserId(Pageable pageable, String id) {

        Page<Task> tasks = taskRepository.findAllByUserId(pageable,id);
        return tasks.map(TaskResponse::fromEntity);
    }
    public TaskResponse getTask(String id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no task by that ID."));

        return TaskResponse.fromEntity(existingTask);
    }


    public void addTask(CreateTaskRequest createTaskRequest) {
        Task newTask = new Task();

        newTask.setDescription(createTaskRequest.getDescription());
        newTask.setDue(createTaskRequest.getDue());
        newTask.setCompleted(createTaskRequest.isCompleted());

        taskRepository.save(newTask);
    }
    public void updateTask(UpdateTaskRequest updateTaskRequest, String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no task like that"));

        task.setDescription(updateTaskRequest.getDescription());
        task.setDue(updateTaskRequest.getDue());
        task.setCompleted(updateTaskRequest.isCompleted());

        taskRepository.save(task);
    }

    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no task like that."));

        taskRepository.delete(task);
    }
}
