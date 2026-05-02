package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.role.CreateRoleRequest;
import com.store.management.system.store_management.dto.role.UpdateRoleRequest;
import com.store.management.system.store_management.entity.Role;
import com.store.management.system.store_management.exception.RoleAlreadyExistsException;
import com.store.management.system.store_management.exception.RoleNotFoundException;
import com.store.management.system.store_management.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role findById(int id) {
        return roleRepo.findById(id)
                .orElseThrow (()-> new RoleNotFoundException ("Role not found: " + id));
    }

    public Role save(CreateRoleRequest  request){
        Role role = new Role();


        if (roleRepo.existsByAuthority(request.getAuthority())){
            throw new RoleAlreadyExistsException ("Authority Already Exist: " + request.getAuthority()) ;
        }
        role.setAuthority(request.getAuthority());
        role.setAccountType(request.getAccountType());
        return roleRepo.save(role);
    }

    public Role update ( int id , UpdateRoleRequest request){

        Role role = roleRepo.findById(id)
                .orElseThrow (()-> new RoleNotFoundException ("Role not found: " + id));

        if (roleRepo.existsByAuthority(request.getAuthority())
                && !role.getAuthority().equals(request.getAuthority())) {

            throw new RoleAlreadyExistsException("Authority already exists: " + request.getAuthority());
        }
        if("null".equalsIgnoreCase(request.getAuthority())){
            throw new RoleNotFoundException ("Authority cannot be empty or 'null' ");
        }
        role.setAuthority(request.getAuthority());
        return roleRepo.save(role);
    }

    public void deleteById(int id){

        if (!roleRepo.existsById(id)){
            throw new RoleNotFoundException ("Role not found: " + id);
        }
        roleRepo.deleteById(id);
    }

}
