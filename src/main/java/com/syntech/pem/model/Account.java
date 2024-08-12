package com.syntech.pem.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends BaseIdEntity{
    
    private String name;
    
    private double balance;
    
    //Constructors
    
    public Account () {}
    
    public Account(String name, double balance){
        this.name = name;
        this.balance = balance;
    }
    
    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    
}
