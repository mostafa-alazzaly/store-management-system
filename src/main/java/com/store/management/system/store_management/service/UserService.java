package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.CreateUserRequest;
import com.store.management.system.store_management.dto.UpdateUserRequest;
import com.store.management.system.store_management.entity.Role;
import com.store.management.system.store_management.entity.User;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.RoleRepo;
import com.store.management.system.store_management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User getUserById(int id){
        return userRepo.findById(id)
                .orElseThrow (() -> new UserNotFoundException("User not found"));
    }

    public void validateRolesForAccountType (User.AccountType accountType, List<String> roles ) {
        if (accountType == null || roles == null || roles.isEmpty()) {
            return;
        }

        switch (accountType) {
            case CUSTOMER-> {
                for (String role : roles) {
                    if (!role.equals("ROLE_CUSTOMER")) {
                        throw new InvalidRoleForAccountTypeException("CUSTOMER account can only have ROLE_CUSTOMER");
                    }
                }
            }
            case EMPLOYEE-> {
                for (String role : roles) {
                    if (!role.equals("ROLE_ADMIN")
                            && !role.equals("ROLE_MANAGER")
                            && !role.equals("ROLE_EMPLOYEE")) {
                        throw new InvalidRoleForAccountTypeException("EMPLOYEE account can only have ROLE_EMPLOYEE, ROLE_MANAGER, or ROLE_ADMIN");
                    }
                }
            }
        }
    }
    public User createUser(CreateUserRequest request) {

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username Already Exist: " + request.getUsername()) ;
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email Already Exist: " + request.getEmail()) ;
        }

        User user = new User();

        // Set DTO to Entity
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setEnabled(
                request.getEnabled() !=null ? request.getEnabled() : true );
        user.setAccountNonLocked(
                request.getAccountNonLocked() !=null ? request.getAccountNonLocked() : true );
        user.setAccountType(request.getAccountType());

        List<Role> roleList = new ArrayList<>();


        for (String roleName : request.getRoles()) {
            Role role = roleRepo.findByAuthority(roleName);

            if (role == null) {
                throw new RoleNotFoundException("Role not found: " + roleName);
            }
            roleList.add(role);
        }
        validateRolesForAccountType(request.getAccountType(), request.getRoles());

        user.setRoles(roleList);
        return userRepo.save(user);
    }

    public User updateUser (int id , UpdateUserRequest request){

         User user = userRepo.findById(id)
                    .orElseThrow (() -> new UserNotFoundException("User not found with id: " + id));

         // For Username
         if (request.getUsername() != null) {
             if(request.getUsername().isBlank()|| request.getUsername() == null) {
                    throw new InvalidRequestException ("Username cannot be empty");
             }

             if(!request.getUsername().equals(user.getUsername()) &&
                     userRepo.existsByUsername(request.getUsername())) {
                 throw new UsernameAlreadyExistsException("Username Already Exist: " + request.getUsername()) ;
             }

             user.setUsername(request.getUsername());
         }


        // For Email
        if (request.getEmail() != null ){
            if(request.getEmail().isBlank()){
                throw new InvalidRequestException ("Email cannot be empty");
            }
            if( !request.getEmail().equals(user.getEmail()) &&
                    userRepo.existsByEmail(request.getEmail())){
                throw new EmailAlreadyExistsException("Email Already Exist: " + request.getEmail()) ;
            }
                user.setEmail(request.getEmail());
        }



        // For password
        if(request.getPassword() != null){
            if (request.getPassword().isBlank()) {
               throw new InvalidPasswordException("Password cannot be empty");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // For Phone
        if (request.getPhone()!= null){
            user.setPhone(request.getPhone());
        }

        // For Enable
        if (request.getEnabled() != null){
            user.setEnabled(request.getEnabled());
        }

        // For Account NonLocked
        if (request.getAccountNonLocked() != null){
            user.setAccountNonLocked(request.getAccountNonLocked());
        }

        // For Role
        if (request.getRoles() != null && !request.getRoles().isEmpty()){

            List <Role> roles = new ArrayList<> ();

            for (String roleName : request.getRoles()) {
                Role role = roleRepo.findByAuthority(roleName);
                if(role == null){
                    throw new RoleNotFoundException("Role not found: " + roleName);
                }
                roles.add(role);
            }
            validateRolesForAccountType(user.getAccountType(),request.getRoles());
            user.setRoles(roles);

        }
        return userRepo.save(user);
    }

    public void deleteUser (int id){
        User user = userRepo.findById(id)
                .orElseThrow (() -> new UserNotFoundException("User not found with id: " + id));

        userRepo.delete(user);
    }

}
