package com.syntech.pem.model;

public class Main {
    public static void main(String[] args){
        Transaction transaction = new Transaction.TransactionBuilder()
                .setAmount(100.0)
                .setDescription("Groceries")
                .build();
        
        System.out.println("Transaction Created:" + transaction.getDescription());
    
    Account account = new Account.AccountBuilder()
            .setName("Cash")
            .setBalance(10000)
            .build();
    
        System.out.println("Account created: " + account.getName());
    }
}
