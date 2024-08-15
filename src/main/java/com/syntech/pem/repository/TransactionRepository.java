package com.syntech.pem.repository;

import com.syntech.pem.model.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class TransactionRepository extends GenericRepository<Transaction>{
    
    @Override
    public Transaction findById(Long id){
        return getEntityManager().find(Transaction.class, id);
    }
    
    @Override
    public List<Transaction> findAll(){
        TypedQuery<Transaction> query = getEntityManager().createQuery("SELECT  t FROM Transaction t", Transaction.class);
        return query.getResultList();
   } 
    
}
