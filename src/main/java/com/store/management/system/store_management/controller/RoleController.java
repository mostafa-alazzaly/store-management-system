package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.role.CreateRoleRequest;
import com.store.management.system.store_management.dto.role.UpdateRoleRequest;
import com.store.management.system.store_management.entity.Role;
import com.store.management.system.store_management.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private  final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("{id}")
    public Role findById(@PathVariable int id ){
        return roleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role save( @Valid @RequestBody CreateRoleRequest request){
        return roleService.save(request);
    }

    @PutMapping("{id}")
    public Role update(@PathVariable int id, @Valid @RequestBody UpdateRoleRequest request){
        return roleService.update(id,request);
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable int id){
        roleService.deleteById(id);
         return "Delete Role Successfully " + id ;
    }

}
