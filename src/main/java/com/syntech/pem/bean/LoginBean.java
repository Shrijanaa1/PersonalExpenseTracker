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
public class LoginBean implements Serializable {
    
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

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userService.getByUsername(username);

        if (user != null && user.getPassword().equals(password)) { 
            context.getExternalContext().getSessionMap().put("user", user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login successful"));
            return "userDashboard?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please try again."));
            return "login";
        }
    }
    

     
}
