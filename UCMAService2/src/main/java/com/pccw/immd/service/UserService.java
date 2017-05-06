package com.pccw.immd.service;

import com.pccw.immd.domain.User;
import com.pccw.immd.exception.InvalidCredentialException;
import com.pccw.immd.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by vagrant on 5/3/17.
 */
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (null == user || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialException("Invalid Credential");
        }
        return user;
    }

    public User register(User user) {
        User save = userRepository.save(securedCredential(user));
        return save;
    }

    private User securedCredential(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return user;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }



}
