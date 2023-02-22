package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.entity.User;
import com.researchecosystems.todoapp.exception.BusinessException;
import com.researchecosystems.todoapp.exception.ErrorCode;
import com.researchecosystems.todoapp.model.request.user.CreateUserRequest;
import com.researchecosystems.todoapp.model.request.user.UpdateUserRequest;
import com.researchecosystems.todoapp.model.response.task.TaskResponse;
import com.researchecosystems.todoapp.model.response.user.UserResponse;
import com.researchecosystems.todoapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TaskService taskService;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserResponse::fromEntity);
    }

    public UserResponse getUser(String id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user by that ID."));

        return UserResponse.fromEntity(existingUser);
    }

    public Page<TaskResponse> getTasksByUserId(Pageable pageable, String id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user by that ID."));

        return taskService.getAllTasksByUserId(pageable, id);
    }

    public void updateUser(UpdateUserRequest updateUserRequest, String id) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        currentUser.setEmail(updateUserRequest.getEmail());
        currentUser.setName(updateUserRequest.getName());
        currentUser.setSurname(updateUserRequest.getSurname());

        userRepository.save(currentUser);
    }

    public void addUser(CreateUserRequest createUserRequest) {
        User existingUser = userRepository.findUserByEmail(createUserRequest.getEmail());
        if (existingUser != null) {
            throw new BusinessException(ErrorCode.forbidden, "There is already a registered email like that.");
        }

        User newUser = new User();

        newUser.setName(createUserRequest.getName());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setSurname(createUserRequest.getSurname());

        userRepository.save(newUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));
        userRepository.delete(user);
    }

}