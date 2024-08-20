package com.syntech.pem.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account extends BaseIdEntity{
    
    @NotNull(message = "Account name should not be null")
    private String name; 
    
    @NotNull
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
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.transactions);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (Double.doubleToLongBits(this.balance) != Double.doubleToLongBits(other.balance)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.transactions, other.transactions);
    }
    
}



 
