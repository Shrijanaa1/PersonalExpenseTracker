package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class UserBean implements Serializable {
    
    @Inject
    private UserService userService;
    
    private User user = new User();
    private Long userId;
    
    public void createUser(){
        userService.save(user);
    }
    
    public User getUser() {
        if (userId != null) {
            user = userService.getById(userId);
        }
        return user;
    }

    public void updateUser() {
        userService.update(user);
    }
    
    public void deleteUser() {
        userService.delete(userId);
    }

    public List<User> getAllUsers() {
        return userService.getAll();
    }
    
    
    // Getters and Setters
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
