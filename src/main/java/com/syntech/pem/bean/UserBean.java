package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.repository.UserRepository;
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
    private UserRepository userRepository;
    
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
            User existingUser = userRepository.findById(selectedUser.getId());
            if(existingUser == null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found"));
                return;
            }
            userRepository.update(selectedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
        
        }else{
            
            //create new user
            if(userRepository.getByUsername(selectedUser.getUsername()) != null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Please choose a different username."));
                return;
            }
            userRepository.save(selectedUser);
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User created successfully."));
            context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/login.xhtml?faces-redirect=true");
        }
    }
    
    public String deleteUser(User user) {
        if(user != null){
            userRepository.delete(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully."));
        }
        return "userList?faces-redirect=true"; //return to user list page
    }
        
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
}  
