package com.store.management.system.store_management.service;

import com.store.management.system.store_management.entity.User;
import com.store.management.system.store_management.model.UserPrincipal;
import com.store.management.system.store_management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

    @Service
    public class userDetailsService implements UserDetailsService {


        private  UserRepo userRepo;

        @Autowired
        public userDetailsService(UserRepo userRepo) {
            this.userRepo = userRepo;
        }


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            User user = userRepo.findByUsername(username);

                if (user == null) {
                    System.out.println("user not found");
                    throw new UsernameNotFoundException("Username not found");
                }
                
            return new UserPrincipal(user);
        }
    }
