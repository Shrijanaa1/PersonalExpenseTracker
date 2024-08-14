package com.syntech.pem.repository;

import com.syntech.pem.model.BaseIdEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public abstract class GenericRepository<T extends BaseIdEntity> {
        
    @PersistenceContext(unitName = "PEM_DS")
    private EntityManager entityManager;
    
    //Abstract methods to be implemented by subclasses
    public abstract T findById(Long id);
    public abstract List<T> findAll();
    
    
    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        if (!entityManager.contains(entity)) {
            entity = entityManager.merge(entity);
        }
        entityManager.remove(entity);
    }

    // Getter and setter for entityManager for subclasses to use
    
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
}