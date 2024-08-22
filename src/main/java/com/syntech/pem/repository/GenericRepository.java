package com.syntech.pem.repository;

import com.syntech.pem.model.BaseIdEntity;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public abstract class GenericRepository<T extends BaseIdEntity> {
    
    protected abstract EntityManager entityManager();
        
    private Class<T> entityClass;
    protected CriteriaQuery<T> criteriaQuery;
    protected CriteriaBuilder criteriaBuilder;
    protected Root<T> root;
    
    
    public GenericRepository(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    @PostConstruct
    protected void _startQuery(){
        this.criteriaBuilder = entityManager().getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = this.criteriaQuery.from(entityClass);
    }
    
    public GenericRepository<T> startQuery(){
        this._startQuery();
        return this;
    }
    
    
    @Transactional
    public T save(T entity) {
        entityManager().persist(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        return entityManager().merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        if (!entityManager().contains(entity)) {
            entity = entityManager().merge(entity);
        }
        entityManager().remove(entity);
    }
    
    
    public T findById(Long id){
        return entityManager().find(entityClass, id);
    }
    
    public List<T> findAll(){
        return entityManager().createQuery(criteriaQuery).getResultList();
    } 
    
    
    protected List<T> findByCriteria(CriteriaQuery<T> criteriaQuery) {
        return entityManager().createQuery(criteriaQuery).getResultList();
    }

    public List<T> findByAttribute(String attributeName, Object attributeValue) {
        startQuery(); // Ensure criteria query is initialized
        Predicate predicate = criteriaBuilder.equal(root.get(attributeName), attributeValue);
        criteriaQuery.select(root).where(predicate);
        return findByCriteria(criteriaQuery);
    }
    
    public List<T> findRange(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        startQuery();
        applyFilters(filterBy);
        applySorting(sortBy);
        return entityManager().createQuery(criteriaQuery)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        startQuery();
        applyFilters(filterBy);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(entityClass)));
        return entityManager().createQuery(countQuery).getSingleResult().intValue();
    }
    
    

    private void applyFilters(Map<String, FilterMeta> filterBy) {
        if (filterBy != null && !filterBy.isEmpty()) {
            Predicate[] predicates = filterBy.values().stream()
                    .map(filterMeta -> criteriaBuilder.like(root.get(filterMeta.getField()), "%" + filterMeta.getFilterValue() + "%"))
                    .toArray(Predicate[]::new);
            criteriaQuery.where(predicates);
        }
    }
    
    private void applySorting(Map<String, SortMeta> sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            sortBy.values().forEach(sortMeta -> {
                if (sortMeta.getOrder().isAscending()) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortMeta.getField())));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortMeta.getField())));
                }
            });
        }
    }
        
    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery;
    }

    public void setCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        this.criteriaQuery = criteriaQuery;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public Root<T> getRoot() {
        return root;
    }

    public void setRoot(Root<T> root) {
        this.root = root;
    }
    
    
    
    
    
}
