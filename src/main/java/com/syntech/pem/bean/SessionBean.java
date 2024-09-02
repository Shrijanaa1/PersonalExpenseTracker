package com.syntech.pem.bean;

import com.syntech.pem.model.User;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shrijanakarki
 */

@Named
@SessionScoped
public class SessionBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    
    public void checkSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentPage = context.getViewRoot().getViewId(); //Get current page/view ID
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        
        //If the current page is signUp.xhtml, skip the session check
        if(currentPage != null && currentPage.contains("signUp.xhtml")){
            return; //Skip the session check for signUp page
        }
        
        //Perform session check for other pages
        if (session == null || session.getAttribute("valid_user") == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "Please log in first", "You need to log in to access this page."));
            try {
                context.getExternalContext().redirect("login.xhtml");
            } catch (IOException e) {
            }
        }
    }
    
    
    //Retrieves the currently logged-in user from the session
     public User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return (session != null) ? (User) session.getAttribute("valid_user") : null;
    }
     
     // Logs out the current user by invalidating the session
    public void logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();  // Invalidate the session
        }
    }
    
    //Stores specified user in session
     public void storeUserInSession(User user) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("valid_user", user);
        session.setAttribute("username", user.getUsername());
    }
}
