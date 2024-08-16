package com.syntech.pem.repository;

import com.syntech.pem.model.Account;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class AccountRepository extends GenericRepository<Account>{
    @Override
    public Account findById(Long id){
        return getEntityManager().find(Account.class, id);
    }
    
    @Override
    public List<Account> findAll(){
        TypedQuery<Account> query = getEntityManager().createQuery("SELECT  a FROM Account a", Account.class);
        return query.getResultList();
   } 

}
