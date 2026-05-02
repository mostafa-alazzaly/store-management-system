package com.store.management.system.store_management.dto.role;


import com.store.management.system.store_management.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateRoleRequest {

    @NotBlank(message = "Role must not be blank")
    private String authority;

    @NotNull(message = "AccountType is required")
    private User.AccountType accountType;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(User.AccountType accountType) {
        this.accountType = accountType;
    }
}