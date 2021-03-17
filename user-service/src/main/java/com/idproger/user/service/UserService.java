package com.idproger.user.service;

import com.idproger.user.entity.User;
import com.idproger.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByPassport(String passport) {
        return userRepository.findByPassportNumber(passport);
    }
}
