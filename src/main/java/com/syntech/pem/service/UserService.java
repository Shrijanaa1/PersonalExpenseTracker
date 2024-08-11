package com.syntech.pem.service;

import com.syntech.pem.model.User;
import com.syntech.pem.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;


public class UserService {

    @Inject
    private UserRepository userRepository;

    
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
    
    public User getByUsername(String username) {
    return userRepository.getByUsername(username);
    }


    @Transactional
    public void update(User user) {
        userRepository.update(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }
    
}
