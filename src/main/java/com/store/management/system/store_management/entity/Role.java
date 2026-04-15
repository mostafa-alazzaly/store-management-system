package com.store.management.system.store_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private  int id ;

    @Column(name="authority")
    private String authority;

    @Enumerated(EnumType.STRING)
    @Column(name="account_type",nullable=false)
    private User.AccountType accountType;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles",
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<User> users;


    public void add (User user){
        if(users==null){
            users=new ArrayList<User>();
        }
        users.add(user);
        user.getRoles().add(this);
        // user.add(this);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role(){}

    public Role(String authority) {
        this.authority = authority;
    }

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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", accountType=" + accountType +
                ", users=" + users +
                '}';
    }
}