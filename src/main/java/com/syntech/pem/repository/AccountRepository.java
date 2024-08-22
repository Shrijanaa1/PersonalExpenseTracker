package com.syntech.pem.repository;

import com.syntech.pem.model.Account;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.FilterMeta;

@Stateless
public class AccountRepository extends GenericRepository<Account> {
    
    @PersistenceContext(unitName = "PEM_DS")
    private EntityManager entityManager;
    
    public AccountRepository() {
        super(Account.class);
    }
    
    @Override
    protected EntityManager entityManager() {
        return this.entityManager;
    }
    
    @Override
    public Account findById(Long id) {
        return super.findById(id);
    }
    
    
    @Override
    public List<Account> findAll(){
        return super.findAll();
    }
    
    
    public Account findAccountByName(String name){
        return findByAttribute("name", name).stream().findFirst().orElse(null);
    }
    
    
    public List<Account> findRange(int first, int pageSize) {
        String query = "SELECT a FROM Account a";
        return entityManager.createQuery(query, Account.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
                
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        String query = "SELECT COUNT(a) FROM Account a";
        return ((Long) entityManager.createQuery(query).getSingleResult()).intValue();
    }
}