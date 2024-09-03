package com.syntech.pem.bean;

import com.syntech.pem.model.Account;
import com.syntech.pem.model.GenericLazyDataModel;
import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.AccountRepository;
import com.syntech.pem.repository.TransactionRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author shrijanakarki
 */

@Named
@ViewScoped
public class AccountBean implements Serializable{
    
    @Inject
    private AccountRepository accountRepository;
    
    @Inject 
    private TransactionRepository transactionRepository;  
    
    private Account selectedAccount;
    
    private GenericLazyDataModel<Account> lazyAccounts;
    
    @PostConstruct
    public void init() {
        selectedAccount = new Account(); 
        lazyAccounts  = new GenericLazyDataModel<>(accountRepository, Account.class); 
        recalculateAllAccountBalances(); // Initial balance calculation

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
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete account: "));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Account is null!"));
        }
    }
    
    
    public void recalculatedAccountBalance(Account account){
        if(account != null && account.getId() != null){
            List<Transaction> transactions = transactionRepository.findByAccount(account);
            BigDecimal totalBalance = BigDecimal.ZERO;
            for(Transaction transaction : transactions){
                if(transaction.getType() == TransactionType.Income){
                    totalBalance = totalBalance.add(transaction.getAmount());
                }else{
                    totalBalance = totalBalance.subtract(transaction.getAmount());
                }
            }
            account.setBalance(totalBalance);
            accountRepository.update(account); //update account with new balance
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Account balance recalculated successfully!"));
    
    }
    
    public void recalculateAllAccountBalances(){
        List<Account> accounts = accountRepository.findAll();
        for(Account account: accounts){
            recalculatedAccountBalance(account);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "All account balances recalculated successfully!"));
    
    }
    
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
