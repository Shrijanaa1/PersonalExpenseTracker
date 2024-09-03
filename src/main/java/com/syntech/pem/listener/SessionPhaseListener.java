package com.syntech.pem.listener;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shrijanakarki
 */

public class SessionPhaseListener implements PhaseListener{

    @Override
    public void beforePhase(PhaseEvent pe) {
        FacesContext context = pe.getFacesContext();
        String currentPage = context.getViewRoot().getViewId(); //Get current page/view ID
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        
        
        //If the current page is login.xhtml or signUp.xhtml, skip the session check
        if(currentPage != null && (currentPage.contains("login.xhtml") || currentPage.contains("signUp.xhtml"))){
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

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
    
    @Override
    public void afterPhase(PhaseEvent pe) {
        //No action required after phase
    }
    
}
