package com.syntech.pem.repository;

import com.syntech.pem.model.Account;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.primefaces.model.FilterMeta;

@Stateless
public class AccountRepository extends GenericRepository<Account> {
    
    @Override
    public Account findById(Long id) {
        return getEntityManager().find(Account.class, id);
    }
    
    
    @Override
    public List<Account> findAll(){
        return findAll(Account.class);
    }
    
    
     public List<Account> findRange(int first, int pageSize) {
        String query = "SELECT a FROM Account a";
        return getEntityManager().createQuery(query, Account.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
                
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        String query = "SELECT COUNT(a) FROM Account a";
        return ((Long) getEntityManager().createQuery(query).getSingleResult()).intValue();
    }
}