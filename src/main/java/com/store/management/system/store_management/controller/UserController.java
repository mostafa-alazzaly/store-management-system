package com.store.management.system.store_management.controller;


import com.store.management.system.store_management.dto.CreateUserRequest;
import com.store.management.system.store_management.dto.UpdateUserRequest;
import com.store.management.system.store_management.entity.User;
import com.store.management.system.store_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

        @GetMapping("/api/users")
        public List<User> getUser(){
            return userService.getAllUsers();
        }

        @GetMapping("/api/users/{id}")
        public User getUserByID(@PathVariable int id){
         return userService.getUserById(id);
        }

        @PostMapping("/api/users")
        public String createUser( @Valid @RequestBody CreateUserRequest request) {
            User savedUser = userService.createUser(request);
            return "User created successfully with id: " + savedUser.getId();
        }

        @PatchMapping("/api/users/{id}")
        public String updateUser(@PathVariable int id, @Valid @RequestBody UpdateUserRequest request){
            User updatedUser = userService.updateUser(id,request);
            return "User updated successfully with id : " + updatedUser.getId();
        }

        @DeleteMapping("/api/users/{id}")
        public String  deleteUser(@PathVariable int id){
            userService.deleteUser(id);
           return  "User deleted successfully with id : " + id;
        }


}
