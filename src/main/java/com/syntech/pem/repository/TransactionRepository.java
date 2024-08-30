package com.syntech.pem.repository;

import com.syntech.pem.model.Account;
import com.syntech.pem.model.CategoryType;
import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TransactionRepository extends GenericRepository<Transaction> {

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
    public Transaction findById(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> findAll() {
        return super.findAll();
    }

    
    public List<Transaction> findByAccount(Account account) {
        startQuery(); // Initialize Criteria API components
        addPredicates(criteriaBuilder.equal(root.get("account"), account));
        return getResultList();
    }

    
    public List<Transaction> findByCategoryAndType(CategoryType category, TransactionType type) {
        startQuery();
        addPredicates(criteriaBuilder.equal(root.get("category"), category));
        addPredicates(criteriaBuilder.equal(root.get("type"), type));
        return getResultList();
    }

    
    public List<Transaction> findByMonthAndYear(int month, int year) {
        startQuery();
        addPredicates(criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("date")), month));
        addPredicates(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("date")), year));
        return getResultList();
    }

    
    public List<Transaction> findByYear(int year) {
        startQuery();
        addPredicates(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("date")), year));
        return getResultList();
    }

    public List<Object[]> findSumByCategoryAndType(TransactionType type, int year) {
        String jpql = "SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.type = :type AND YEAR(t.date) = :year GROUP BY t.category";
        return entityManager.createQuery(jpql, Object[].class)
                .setParameter("type", type)
                .setParameter("year", year)
                .getResultList();
    }

}
