package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.service.UserService;
import java.io.IOException;
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
    
    private User selectedUser; //hold user being edited
    
    public UserBean(){
        selectedUser = new User(); //initialize selected user
    }
        
    public void prepareEditUser(User user) {
        this.selectedUser = user;
    }
    
    public void prepareCreateUser() {
        this.selectedUser = new User();
    }
    
    
    public void saveOrUpdateUser() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(selectedUser.getId() != null){
            
            //update existing user
            User existingUser = userService.getUserById(selectedUser.getId());
            if(existingUser == null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found"));
                return;
            }
            userService.updateUser(selectedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
        
        }else{
            
            //create new user
            if(userService.getByUsername(selectedUser.getUsername()) != null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Please choose a different username."));
                return;
            }
            userService.createUser(selectedUser);
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User created successfully."));
            context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/login.xhtml?faces-redirect=true");
        }
    }
    
    public String deleteUser(User user) {
        if(user != null){
            userService.deleteUser(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully."));
        }
        return "userList?faces-redirect=true"; //return to user list page
    }
        
    
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }
    
    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
}  
