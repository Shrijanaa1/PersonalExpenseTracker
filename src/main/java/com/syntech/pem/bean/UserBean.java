package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserBean implements Serializable {
    
    @Inject
    private UserService userService;
    
    private User user = new User();

    private String username;
    private String password;

    
    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (userService.getByUsername(username) != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Please choose a different username."));
            return "signUp"; // Return to the same signup page if username is taken
        }

        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        user = new User(); // Clear the form after saving
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User created successfully."));
        return "login?faces-redirect=true"; // Redirect to login page after successful signup
    }

    
    
    public void updateUser() {
        userService.update(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully."));
    }
    
    
    public void deleteUser(User user) {
        userService.delete(user.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully."));
    }

    
    public List<User> getAllUsers() {
        return userService.getAll();
    }
    
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userService.getByUsername(username);

        if (user != null && user.getPassword().equals(password)) { // Ideally,compare passwords
            context.getExternalContext().getSessionMap().put("user", user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login successful"));
            return "userDashboard?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please try again."));
            return "login";
        }
    }
    
    // Getters and Setters
    
    public User getUserInstance() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
