package com.syntech.pem.service;

import com.syntech.pem.model.User;
import com.syntech.pem.repository.UserRepository;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;


public class UserService implements Serializable{

    @Inject
    private UserRepository userRepository;

    public User getUserById(Long id){
        return userRepository.findById(id);
    }
    
    public List<User> getAllUser(){
        return userRepository.findAll();
    } 
    
    public User createUser(User user){
        return userRepository.save(user);
    }
    
    public User updateUser(User user){
        return userRepository.update(user);
    }
    
    public void deleteUser(User user){
        userRepository.delete(user);
    }
    
    public User getByUsername(String username) {
    return userRepository.getByUsername(username);
    }
    
}
