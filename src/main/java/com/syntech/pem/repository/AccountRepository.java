package com.syntech.pem.repository;

import com.syntech.pem.model.Account;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}