package com.syntech.pem.bean;

import com.syntech.pem.model.Account;
import com.syntech.pem.model.Budget;
import com.syntech.pem.model.CategoryType;
import com.syntech.pem.model.GenericLazyDataModel;
import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.AccountRepository;
import com.syntech.pem.repository.BudgetRepository;
import com.syntech.pem.repository.TransactionRepository;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
    
    @Inject 
    private BudgetRepository budgetRepository;
    
    private Transaction selectedTransaction;
    
    private List<Transaction> transactions;
    
    private List<CategoryType> categoryOptions;
    
    private List<Account> accounts;
    
    private GenericLazyDataModel<Transaction> lazyTransactions;

    @PostConstruct
    public void init() {
        accounts = accountRepository.findAll(); 
        transactions = transactionRepository.findAll();
        selectedTransaction = new Transaction();
        categoryOptions = new ArrayList<>();
        lazyTransactions  = new GenericLazyDataModel<>(transactionRepository, Transaction.class);
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

        Account account = selectedTransaction.getAccount();
        if(selectedTransaction.getId() != null){
            //update existing transaction
            
            Transaction existingTransaction = transactionRepository.findById(selectedTransaction.getId());
            if(existingTransaction == null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Transaction not found!"));
                return;
            }
            
            //Revert old transaction's impact on the account balance
            updateAccountBalance(existingTransaction, true);
            updateBudgetBalance(existingTransaction, true);
            transactionRepository.update(selectedTransaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction updated successfully1")); 
        }else{
            
            //create new Transaction
            transactionRepository.save(selectedTransaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction created successfully!"));            
        }
        
           //Apply new transaction's impact on the account balance
           updateAccountBalance(selectedTransaction, false);
           updateBudgetBalance(selectedTransaction, false);
           transactions = transactionRepository.findAll(); // Reload transactions
           selectedTransaction = new Transaction(); // Reset the selectedTransaction after save/update
           
    }
    
    
    public void deleteTransaction(Transaction transaction) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (transaction != null) {
            
            //Rever transaction's impact on the account balance before deleting
            updateAccountBalance(transaction, true);
            transactionRepository.delete(transaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction deleted successfully."));
        }
    }
    
    
    private void updateAccountBalance(Transaction transaction, boolean isReverting){
        Account account = transaction.getAccount();
        double amount = transaction.getAmount();
        
        if(transaction.getType() == TransactionType.Expense){
            account.setBalance(account.getBalance() + (isReverting ? amount : -amount));
        }else if(transaction.getType() == TransactionType.Income){
            account.setBalance(account.getBalance() + (isReverting ? -amount : amount));
        }
        accountRepository.update(account); //Persist the account updated balance
    }
    
    
    private void updateBudgetBalance (Transaction transaction, boolean isReverting){
        if(transaction.getType() == TransactionType.Expense){
            Budget budget = budgetRepository.findByCategory(transaction.getCategory());
            if(budget != null){
                BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
                BigDecimal updatedAmount;
                
                if(isReverting){
                    updatedAmount = budget.getRemainingAmount().add(amount);
                }else{
                    updatedAmount = budget.getRemainingAmount().subtract(amount);
                }
                budget.setRemainingAmount(updatedAmount);
                budgetRepository.update(budget);
            }
        }
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

public GenericLazyDataModel<Transaction> getLazyTransactions() {
        return lazyTransactions;
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



 