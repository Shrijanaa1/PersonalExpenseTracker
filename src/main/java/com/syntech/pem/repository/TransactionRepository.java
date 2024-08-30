package com.syntech.pem.repository;

import com.syntech.pem.model.Account;
import com.syntech.pem.model.CategoryType;
import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    
    public List<Transaction> findByAccount(Account account){
        TypedQuery<Transaction> query  = entityManager.createQuery(
            "SELECT t FROM Transaction t WHERE t.account = :account ", Transaction.class);
        query.setParameter("account", account);
        return query.getResultList();
    }
          
    public List<Transaction> findByCategoryAndType(CategoryType category, TransactionType type){
        TypedQuery<Transaction> query  = entityManager.createQuery(
            "SELECT t FROM Transaction t WHERE t.category = :category AND t.type = :type", Transaction.class);
            query.setParameter("category", category);
            query.setParameter("type", type);
            return query.getResultList();
    }
    
    public List<Transaction> findByMonthAndYear(int month, int year){
        return entityManager.createQuery(
                "SELECT t FROM Transaction t WHERE MONTH(t.date) = :month AND YEAR(t.date) = :year", Transaction.class)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
    }
    
    public List<Transaction> findByYear(int year){
        return entityManager.createQuery(
                "SELECT t FROM Transaction t WHERE YEAR(t.date) = :year", Transaction.class)
                .setParameter("year", year)
                .getResultList();
    }
    
    public List<Object[]> findSumByCategoryAndType(TransactionType type, int year) {
        String jpql = "SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.type = :type AND YEAR(t.date) = :year GROUP BY t.category";
        return entityManager.createQuery(jpql, Object[].class)
                            .setParameter("type", type)
                            .setParameter("year", year)
                            .getResultList();
    }
            
}
