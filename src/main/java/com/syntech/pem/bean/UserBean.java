package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.repository.UserRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author shrijanakarki
 */

@Named
@ViewScoped
public class UserBean implements Serializable {
        
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private SessionBean sessionBean;
    
    private User selectedUser; //hold user being edited
    
    
    @PostConstruct
    public void init(){
        // Initialize selectedUser from the session if available, otherwise create a new instance
        selectedUser = sessionBean.getCurrentUser();
        if(selectedUser == null){
            selectedUser = new User(); // Initialize selectedUser if null
        }    
    }
        
    
    //Prepares the user object for editing
    public void prepareEditUser(User user) {
        this.selectedUser = user;
    }
    
    //Prepares a new user object for creation
    public void prepareCreateUser() {
        this.selectedUser = new User();
    }
    
    
    public String saveOrUpdateUser() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(selectedUser.getId() != null){
            
            //update existing user
            User existingUser = userRepository.findById(selectedUser.getId());
            if(existingUser == null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found"));
                return null;
            }
            
            //check if password has been modified
            User authenticatedUser = userRepository.authenticate(existingUser.getUsername(), selectedUser.getPassword());
            if(authenticatedUser == null){

                //Password has been modified; Re-hash new password before saving
                String salt = userRepository.extractSalt(existingUser.getPassword());
                selectedUser.setPassword(userRepository.hashPassword(selectedUser.getPassword(), salt) + ":" + salt);
            } else{
                //If the password hasn't changed, keep existing hashed password
                selectedUser.setPassword(existingUser.getPassword());
            }
            userRepository.update(selectedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
        
        }else{
            
            //create new user
            if(userRepository.getByUsername(selectedUser.getUsername()) != null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Please choose a different username."));
                return null;
            }
            userRepository.save(selectedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User created successfully."));
            return "login?faces-redirect=true"; // Redirect to login page
        }
        
        // Update the session with the modified user details
        sessionBean.storeUserInSession(selectedUser);
        return null;
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
