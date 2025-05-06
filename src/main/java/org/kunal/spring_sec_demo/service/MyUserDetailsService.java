package org.kunal.spring_sec_demo.service;

import org.kunal.spring_sec_demo.dao.UserRepo;
import org.kunal.spring_sec_demo.model.User;
import org.kunal.spring_sec_demo.model.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if (user == null) {
            System.out.println("User with username " + username + " not found");
            throw new UsernameNotFoundException("User 404");
        }

        return new UserPrinciple(user);
    }
}
