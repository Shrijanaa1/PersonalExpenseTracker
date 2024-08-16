package com.syntech.pem.bean;

import com.syntech.pem.model.Account;
import com.syntech.pem.repository.AccountRepository;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AccountBean implements Serializable{
    
    @Inject
    private AccountRepository accountRepository;
    private List<Account> accounts; // Initialize the accounts list
    
    private Account selectedAccount;
    
    @PostConstruct
    public void init() {
        selectedAccount = new Account(); // Ensure selectedAccount is initialized
        accounts = accountRepository.findAll(); // Initialize the accounts list
    }
    
    
    public void prepareCreateAccount() {
        this.selectedAccount = new Account();
    }
    
     public void prepareEditAccount(Account account) {
        this.selectedAccount = account;
    }
    
    public void saveOrUpdateAccount() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (selectedAccount.getId() != null) {
                accountRepository.update(selectedAccount);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account updated successfully!"));
            } else {
                accountRepository.save(selectedAccount);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account created successfully!"));
//                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/accountList.xhtml?faces-redirect=true");
                accounts = accountRepository.findAll();
                
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save/update account: " + e.getMessage()));
        }
    }

     
    public void deleteAccount(Account account) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (account != null) {
            try {
                accountRepository.delete(account);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account deleted successfully!"));
                accounts = accountRepository.findAll(); // Refresh the list after deletion
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete account: " + e.getMessage()));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Account is null!"));
        }
    }


    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
     
     public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    
    public List<Account> getAllTransactions() {
        return accountRepository.findAll();
    }
    
}




//    public List<Account> getAccounts(){
//        return accounts;
//    }



//    @PostConstruct
//    public void init() {
//        accounts = accountRepository.findAll();
//    }
    

//    private List<Account> accounts;