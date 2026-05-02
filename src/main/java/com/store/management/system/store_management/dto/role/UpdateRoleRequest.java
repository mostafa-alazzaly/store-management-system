package com.store.management.system.store_management.dto.role;

import jakarta.validation.constraints.NotBlank;

public class UpdateRoleRequest {


    @NotBlank(message = "Role must not be blank")
    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
}
