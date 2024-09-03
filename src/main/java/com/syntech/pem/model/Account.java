package com.syntech.pem.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account extends BaseIdEntity{
    
    @NotNull(message = "Account names should not be null")
    @Column(name = "name", nullable = false, unique = true)
    private String name; 
    
    @NotNull(message = "Balance should not be null")
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    

    public void setName(String name) {
        this.name = name;
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
        private BigDecimal balance;
        
        public AccountBuilder setName(String name){
            this.name = name;
            return this;
        }
        
        public AccountBuilder setBalance(BigDecimal balance){
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



 
