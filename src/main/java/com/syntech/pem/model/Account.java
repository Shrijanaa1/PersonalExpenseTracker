package com.syntech.pem.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends BaseIdEntity{
    
    private String name;
    
    private double balance;
    
    //Private Constructor to enforce usage of the builder
    
    private Account(AccountBuilder builder){
        this.name = builder.name;
        this.balance = builder.balance;
    }
    
    //Put Getters and Setters here
    public String getName(){
        return name;
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
