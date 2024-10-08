package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import com.syntech.pem.repository.UserRepository;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author shrijanakarki
 */

@Named
@SessionScoped
public class LoginBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @Inject
    private UserRepository userRepository;
    
    @Inject
    private SessionBean sessionBean;

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

        // Authenticate the user using the UserRepository's authenticate method
        User user = userRepository.authenticate(username, password);

        if (user != null) { 
            //Successful login, store user details in the session
            sessionBean.storeUserInSession(user);           
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login successful"));
            return "userDashboard?faces-redirect=true";
        } else {
            // Authentication failed, show an error message
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please try again."));
            return null; //stay on login page
        }
    }
    

    public String logout() {
        sessionBean.logout(); // Invalidate the session
        return "login?faces-redirect=true";  // Redirect to login page
    }
     
}
