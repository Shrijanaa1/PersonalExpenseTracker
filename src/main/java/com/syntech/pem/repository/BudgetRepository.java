package com.syntech.pem.repository;

import com.syntech.pem.model.Budget;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author shrijanakarki
 */

@Stateless
public class BudgetRepository extends GenericRepository<Budget>{
    
    @PersistenceContext(unitName = "PEM_DS")
    private EntityManager entityManager;
    
    public BudgetRepository() {
        super(Budget.class);
    }

    @Override
    protected EntityManager entityManager() {
        return this.entityManager;
    }
    
    @Override
    public Budget findById(Long id){
        return entityManager.find(Budget.class, id);
    }
    
    @Override
    public List<Budget> findAll(){
        return super.findAll();
    }
}
