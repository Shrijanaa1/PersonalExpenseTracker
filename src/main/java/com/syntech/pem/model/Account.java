package com.syntech.pem.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account extends BaseIdEntity{
    
    @NotNull(message = "Account name should not be null")
    @Column(name = "name", nullable = false)
    private String name; 
    
    @NotNull
    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
    
    public Account() {}
    
    
    //Private Constructor to enforce usage of the builder
    
    private Account(AccountBuilder builder){
        this.name = builder.name;
        this.balance = builder.balance;
    }
    
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
   

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Account account = (Account) obj;
        return getId() != null && getId().equals(account.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
    
}



 
