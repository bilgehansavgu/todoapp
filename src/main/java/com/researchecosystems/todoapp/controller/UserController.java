package com.researchecosystems.todoapp.controller;


import com.researchecosystems.todoapp.model.request.user.CreateUserRequest;
import com.researchecosystems.todoapp.model.request.user.UpdateUserRequest;
import com.researchecosystems.todoapp.model.response.task.TaskResponse;
import com.researchecosystems.todoapp.model.response.user.UserResponse;
import com.researchecosystems.todoapp.service.AuthenticationService;
import com.researchecosystems.todoapp.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;


    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", paramType = "query",
                    value = "Number of records per page."),
            /*
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")

             */
    })
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping("/me")
    public UserResponse getUser() {
        return userService.getUser(authenticationService.getAuthenticatedUserId());
    }

    @GetMapping("/{id}/task")
    public Page<TaskResponse> getTasks(Pageable pageable, @PathVariable String id) {
        return userService.getTasksByUserId(pageable, authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("/me")
    public void updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(updateUserRequest, authenticationService.getAuthenticatedUserId());
    }

    @PostMapping
    public void addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.addUser(createUserRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}