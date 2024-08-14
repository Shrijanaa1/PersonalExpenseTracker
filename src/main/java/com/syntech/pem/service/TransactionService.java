package com.syntech.pem.service;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.repository.TransactionRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TransactionService {
    
    @Inject
    private TransactionRepository transactionRepository;
    
    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id);
    }
    
    public List<Transaction> getAllTransaction(){
        return transactionRepository.findAll();
    } 
    
    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    
    public Transaction updateTransaction(Transaction transaction){
        return transactionRepository.update(transaction);
    }
    
    public void deleteTransaction(Transaction transaction){
        transactionRepository.delete(transaction);
    }
}
