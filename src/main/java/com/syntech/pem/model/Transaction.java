package com.syntech.pem.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="transactions")
public class Transaction extends BaseIdEntity{
        
    private double amount;
    
    private String type;
    
    private String category;

    private String description;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    //Constructors
    
    public Transaction() {}

    public Transaction(double amount, String type, String category, String description, Date date, Account account) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = date;
        this.account = account;
    }
    
    
    //Getters and Setters

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
   
}
