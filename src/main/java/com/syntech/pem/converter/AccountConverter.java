package com.syntech.pem.converter;

import com.syntech.pem.model.Account;
import com.syntech.pem.repository.AccountRepository;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
   
@RequestScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@FacesConverter(value = "accountConverter", forClass = Account.class, managed = true)
public class AccountConverter implements Converter{
    
    @Inject
    private AccountRepository accountRepository;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty() || value.length() == 0 || value.equals("")) {
            return null;
        }        
        return accountRepository.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null || o.equals("")) {
            return "";
        }
        return ((Account) o).getId().toString();
    }
}



//omni 
