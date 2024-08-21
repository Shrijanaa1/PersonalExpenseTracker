package com.syntech.pem.converter;

import com.syntech.pem.model.Account;
import com.syntech.pem.repository.AccountRepository;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
   

@FacesConverter(value = "accountConverter", forClass = com.syntech.pem.model.Account.class)
public class AccountConverter implements Converter{
    
    @EJB
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
        return String.valueOf(((Account) o).getId());
    }
}