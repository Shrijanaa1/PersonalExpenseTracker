package com.syntech.pem.bean;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.service.TransactionService;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class TransactionBean implements Serializable{
    
    @Inject
    private TransactionService transactionService;
    
    private Transaction selectedTransaction;
    
    private List<String> categoryOptions;
    
    public TransactionBean(){
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
    
    private void updateCategoryOptions(){
        if("Income".equals(selectedTransaction.getType())){
            categoryOptions = Arrays.asList("Salary", "Rent", "Interest", "Freelancing");
        }else if("Expense".equals(selectedTransaction.getType())){
            categoryOptions = Arrays.asList("Food", "Entertainment", "Shopping", "Travel", "Education" , "Others");
            
        }else{
            categoryOptions = new ArrayList<>();
        }
    }
    
    public void selectOrUpdateTransaction() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        if(selectedTransaction.getId() != null){
            //update existing trandaction
            
            Transaction existingTransaction = transactionService.getTransactionById(selectedTransaction.getId());
            if(existingTransaction == null){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Transaction not found!"));
                return;
            }
            transactionService.updateTransaction(selectedTransaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction updated successfully1")); 
        }else{
            
            //create new Transaction
            transactionService.createTransaction(selectedTransaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction created successfully!"));
            context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/transactionList.xhtml?faces-redirect=true");
            
        }
    }
    
    public void deleteTransaction(Transaction transaction) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (transaction != null) {
            transactionService.deleteTransaction(transaction);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transaction deleted successfully."));
        }
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransaction();
    }

    public Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }
    
    public List<String> getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(List<String> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }
    

    
}
