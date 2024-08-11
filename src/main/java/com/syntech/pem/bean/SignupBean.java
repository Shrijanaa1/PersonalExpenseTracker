package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.service.UserService;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SignupBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @Inject
    private UserService userService;

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
    
    public String signup(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (userService.getByUsername(username) != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Please choose a different username."));
            return "signup";
        }
        
        User newUser = new User(username, password);
        userService.save(newUser);

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account created successfully. Please log in."));
        return "login?faces-redirect=true"; // Redirect to login page after successful signup
    }

            
    
}
