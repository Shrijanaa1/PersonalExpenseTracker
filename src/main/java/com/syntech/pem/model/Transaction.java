package com.syntech.pem.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryType category;
    
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
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
