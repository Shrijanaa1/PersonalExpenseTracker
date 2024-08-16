package com.syntech.pem.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends BaseIdEntity{
    
    private String name; 
    
    private double balance;

    @OneToMany(mappedBy="account")
    private List<Transaction> transactions;
    
    public Account() {}
    
    
    //Private Constructor to enforce usage of the builder
    
    private Account(AccountBuilder builder){
        this.name = builder.name;
        this.balance = builder.balance;
    }
    
    //Put Getters and Setters here
    public String getName(){
        return name;
    } 
    
    public double getBalance(){
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    

    //Builder class
    public static class AccountBuilder{
        private String name;
        private double balance;
        
        public AccountBuilder setName(String name){
            this.name = name;
            return this;
        }
        
        public AccountBuilder setBalance(double balance){
            this.balance = balance;
            return this;
        }
        
        public Account build(){
            return new Account(this);
        }
    }
    
    
}
