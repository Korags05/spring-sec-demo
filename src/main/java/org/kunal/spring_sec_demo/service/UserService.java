package org.kunal.spring_sec_demo.service;

import org.kunal.spring_sec_demo.dao.UserRepo;
import org.kunal.spring_sec_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProvider("local"); // Set provider for regular users
        return userRepo.save(user);
    }

    public User findOrCreateOAuthUser(String email, String name, String provider, String providerId) {
        User user = userRepo.findByUsername(email);
        if (user == null) {
            user = new User();
            user.setUsername(email);
            // Generate random password and encode it
            user.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
            user.setProvider(provider);
            user.setProviderId(providerId);
            return userRepo.save(user);
        }
        return user;
    }
}
