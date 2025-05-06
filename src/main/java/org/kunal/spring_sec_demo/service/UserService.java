package org.kunal.spring_sec_demo.service;

import org.kunal.spring_sec_demo.dao.UserRepo;
import org.kunal.spring_sec_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

}
