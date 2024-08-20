package com.syntech.pem.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="transactions")
public class Transaction extends BaseIdEntity{
        
    @NotNull
    private double amount;
    
    @NotNull
    private String type;
    
    @NotNull
    private String category;
    
    
    @NotNull
    private String description;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {}
    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
    
//    
//    //Private Constructor to enforce usage of builder
//    
//    private Transaction(TransactionBuilder builder){
//        this.amount = builder.amount;
//        this.type = builder.type;
//        this.category = builder.category;
//        this.description = builder.description;
//        this.date = builder.date;
//        this.account = builder.account;
//    }
//    
//    public String getDescription(){
//        return description;
//    } 
//    
//    //Builder class
//    public static class TransactionBuilder{
//        private double amount;
//        private String type;
//        private String category;
//        private String description;
//        private Date date;
//        private Account account;   
//    
//        
//    public TransactionBuilder setAmount(double amount){
//        this.amount = amount;
//        return this;
//    }
//    
//    public TransactionBuilder setType(String type){
//        this.type = type;
//        return this;
//    }
//    
//    public TransactionBuilder setCategory(String category){
//        this.category = category;
//        return this;
//    }
//    
//    public TransactionBuilder setDescription(String description){
//        this.description = description;
//        return this;
//    }
//    
//    public TransactionBuilder setDate(Date date){
//        this.date = date;
//        return this;
//    }
//    
//    public TransactionBuilder setAccount(Account account){
//        this.account = account;
//        return this;
//    }
//    
//    public Transaction build(){
//        return new Transaction(this);
//    }
//    
//    }

    
}
