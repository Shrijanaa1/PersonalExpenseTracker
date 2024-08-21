package com.syntech.pem.repository;

import com.syntech.pem.model.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TransactionRepository extends GenericRepository<Transaction>{
    
    @PersistenceContext(unitName = "PEM_DS")
    private EntityManager entityManager;
    
    public TransactionRepository() {
        super(Transaction.class);
    }
    
    @Override
    protected EntityManager entityManager() {
        return this.entityManager;
    }
    
    @Override
    public Transaction findById(Long id){
        return entityManager.find(Transaction.class, id);
    }
     
    
    @Override
    public List<Transaction> findAll(){
        return super.findAll();
    }
    
}
