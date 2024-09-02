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
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class UserBean implements Serializable {
        
    @Inject
    private UserRepository userRepository;
    
    private User selectedUser; //hold user being edited
    
    
    @PostConstruct
    public void init(){
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            if (session == null || session.getAttribute("valid_user") == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "Please log in first", "You need to log in to access this page."));
                try {
                    context.getExternalContext().redirect("login.xhtml");
                }catch(IOException e){               
                }
            }else {
        selectedUser = (User) session.getAttribute("valid_user"); // Use the logged-in user's details
        }
    }
        
    public void prepareEditUser(User user) {
        this.selectedUser = user;
    }
    
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
