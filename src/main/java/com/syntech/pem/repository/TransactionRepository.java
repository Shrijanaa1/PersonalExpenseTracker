package com.syntech.pem.repository;

import com.syntech.pem.model.Transaction;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class TransactionRepository extends GenericRepository<Transaction>{
    
    @Override
    public Transaction findById(Long id){
        return getEntityManager().find(Transaction.class, id);
    }
     
    
    @Override
    public List<Transaction> findAll(){
        return findAll(Transaction.class);
    }
    
}
