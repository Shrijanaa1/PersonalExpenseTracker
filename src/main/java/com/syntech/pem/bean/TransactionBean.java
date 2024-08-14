package com.syntech.pem.bean;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.service.TransactionService;
import java.io.IOException;
import java.io.Serializable;
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
    
    public TransactionBean(){
        selectedTransaction = new Transaction();
    }
    
    public void prepareCreateTransaction(){
        this.selectedTransaction = new Transaction();
    }
    
    public void prepareEditTransaction(Transaction transaction){
        this.selectedTransaction = transaction;
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

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }
    
    
    
}
