package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class UserBean implements Serializable {
        
    @Inject
    private UserService userService;

    private Long id;
    private String username;
    private String password;
    
    private User selectedUser; //hold user being edited
    
    public UserBean(){
        selectedUser = new User(); //initialize selected user
    }
        

    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (userService.getByUsername(username) != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Please choose a different username."));
            return "signUp"; // Return to the same signup page if username is taken
        }

       User user = new User.UserBuilder()
            .setUsername(username)
            .setPassword(password)
            .build();
        
        userService.save(user);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User created successfully."));
        return "login?faces-redirect=true"; // Redirect to login page after successful signup
    }
      
    public void prepareEditUser(User user) {
        this.selectedUser = user;
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public void updateUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            selectedUser.setUsername(username);
            selectedUser.setPassword(password);
            userService.update(selectedUser);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update user"));
        }
    }
    
    public String deleteUser(User user) {
        if(user != null){
            userService.delete(user.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully."));
        }
        return "userList?faces-redirect=true"; //return to user list page
    }
        
    
    public List<User> getAllUsers() {
        return userService.getAll();
    }
    
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userService.getByUsername(username);

        if (user != null && user.getPassword().equals(password)) { 
            context.getExternalContext().getSessionMap().put("user", user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login successful"));
            return "userDashboard?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials.", "Please try again."));
            return "login";
        }
    }
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
