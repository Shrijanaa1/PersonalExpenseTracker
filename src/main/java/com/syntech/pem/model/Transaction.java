package com.syntech.pem.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double amount;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    //Constructors
    
    public Transaction() {}

    public Transaction(double amount, TransactionType type, Category category, String description, Date date, Account account) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = date;
        this.account = account;
    }
    
    
    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
