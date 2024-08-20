package com.syntech.pem.repository;

import com.syntech.pem.model.BaseIdEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    
    
    //Criteria Queries
    
    protected List<T> findByCriteria(CriteriaQuery<T> criteriaQuery){
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
    //Generic method to create a criteria query by a single attribute
    protected List<T> findByAttribute(String attributeName, Object attributeValue, Class<T> entityClass){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> entityRoot = criteriaQuery.from(entityClass);
        
        Predicate predicate = criteriaBuilder.equal(entityRoot.get(attributeName), attributeValue);
        criteriaQuery.select(entityRoot).where(predicate);
        
        return findByCriteria(criteriaQuery);
    }
    
    protected List<T> findAll(Class<T> entityClass){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> entityRoot = criteriaQuery.from(entityClass);
       
        criteriaQuery.select(entityRoot);
        
        return findByCriteria(criteriaQuery);
    }
    
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
}
