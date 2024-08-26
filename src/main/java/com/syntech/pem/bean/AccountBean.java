package com.syntech.pem.bean;

import com.syntech.pem.model.Account;
import com.syntech.pem.model.GenericLazyDataModel;
import com.syntech.pem.repository.AccountRepository;
import java.io.Serializable;
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
    
    private Account selectedAccount;
    
    private GenericLazyDataModel<Account> lazyAccounts;
    
    @PostConstruct
    public void init() {
        selectedAccount = new Account(); 
        lazyAccounts  = new GenericLazyDataModel<>(accountRepository, Account.class); 
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
            }
            
//            refreshAccounts(); // Refresh the lazy data model after saving or updating

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save/update account: "));
        }
    }

     
    public void deleteAccount(Account account) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (account != null) {
            try {
                accountRepository.delete(account);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account deleted successfully!"));
//                refreshAccounts(); // Refresh the lazy data model after deletion
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete account: "));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Account is null!"));
        }
    }
    
    
    //Refresh all accounts
    public void refreshAccounts(){
        lazyAccounts = new GenericLazyDataModel<>(accountRepository, Account.class); //Reinitialize lazy data model
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "All accounts refreshed successfully!"));

    }
    
//    //Row Specific Refresh 
//    public void refreshAccount(Account account) {
//     FacesContext context = FacesContext.getCurrentInstance();
//     if (account != null) {
//         try {
//             Account refreshedAccount = accountRepository.findById(account.getId());
//             int index = accounts.indexOf(account);
//             if (index >= 0) {
//                 accounts.set(index, refreshedAccount); // Update the specific account in the list
//             }
//             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account refreshed successfully!"));
//         } catch (Exception e) {
//             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to refresh account: " + e.getMessage()));
//         }
//     }
//    }

    public GenericLazyDataModel<Account> getLazyAccounts() {
        return lazyAccounts;
    }
    
    
    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
     

    
}

//lazy loading