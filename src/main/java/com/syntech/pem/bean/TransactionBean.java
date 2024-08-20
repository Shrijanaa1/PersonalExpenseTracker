package com.syntech.pem.bean;

import com.syntech.pem.model.Account;
import com.syntech.pem.model.CategoryType;
import com.syntech.pem.model.Transaction;
import com.syntech.pem.repository.AccountRepository;
import com.syntech.pem.repository.TransactionRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class TransactionBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private TransactionRepository transactionRepository;
    
    @Inject
    private AccountRepository accountRepository; 
    
    private Transaction selectedTransaction;
    
    private List<Transaction> transactions;
    
    private List<CategoryType> categoryOptions;
    
    private List<Account> accounts;

    @PostConstruct
    public void init() {
        accounts = accountRepository.findAll(); // Load all accounts
        transactions = transactionRepository.findAll();
        selectedTransaction = new Transaction();
        categoryOptions = new ArrayList<>();
    }
    
    public void prepareCreateTransaction(){
        this.selectedTransaction = new Transaction();
        updateCategoryOptions();
    }
    
    public void prepareEditTransaction(Transaction transaction){
        this.selectedTransaction = transaction;
        updateCategoryOptions();
    }
    
    public void onTypeChange(){
        updateCategoryOptions();
    }
    
    
     private void updateCategoryOptions() {
        if (selectedTransaction.getType() != null) {
            categoryOptions = CategoryType.getCategoriesForType(selectedTransaction.getType());
        } else {
            categoryOptions = new ArrayList<>();
        }
    }
     
    
    public void selectOrUpdateTransaction() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        if(selectedTransaction.getId() != null){
            //update existing trandaction
            
            Transaction existingTransaction = transactionRepository.findById(selectedTransaction.getId());
            if(existingTransaction == null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Transaction not found!"));
                return;
            }
            transactionRepository.update(selectedTransaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction updated successfully1")); 
        }else{
            
            //create new Transaction
            transactionRepository.save(selectedTransaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction created successfully!"));            
        }
           transactions = transactionRepository.findAll(); // Reload transactions
           selectedTransaction = new Transaction(); // Reset the selectedTransaction after save/update
           
    }
    
    public void deleteTransaction(Transaction transaction) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (transaction != null) {
            transactionRepository.delete(transaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction deleted successfully."));
        }
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    

    public List<CategoryType> getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(List<CategoryType> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }
    
    public Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
  
}



 