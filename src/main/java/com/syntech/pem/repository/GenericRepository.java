package com.syntech.pem.repository;

import com.syntech.pem.model.BaseIdEntity;
import java.util.ArrayList;
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
    protected List<Predicate> predicateList;
    
    public GenericRepository(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    @PostConstruct
    protected void _startQuery(){
        this.criteriaBuilder = entityManager().getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = this.criteriaQuery.from(entityClass);
        this.predicateList = new ArrayList<>();
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
    
    
    public GenericRepository<T> addPredicates(Predicate p) {
        this.predicateList.add(p);
        return this;
    }

    protected void applyPredicates() {
        if (!predicateList.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[0])));
        }
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
    
    
    public T getSingleResult() {
        applyPredicates();
        return entityManager().createQuery(criteriaQuery).getSingleResult();
    }

    public List<T> getResultList() {
        applyPredicates();
        return entityManager().createQuery(criteriaQuery).getResultList();
    }
        
    public int countEntity(Map<String, FilterMeta> filters) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(entityClass)));
        return entityManager().createQuery(countQuery).getSingleResult().intValue();
    }

        
    public List<T> getEntity(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
            applyFilters(filters, root, criteriaQuery);

            return entityManager().createQuery(criteriaQuery)
                    .setFirstResult(first)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
        
    private void applyFilters(Map<String, FilterMeta> filters, Root<T> root, CriteriaQuery<?> query) {
            Predicate[] predicates = filters.values().stream()
                    .map(filter -> criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getFilterValue() + "%"))
                    //similar to sql, where username like '%john%' ==> filter.getField=username , filter.getFilterValue=john
                    .toArray(Predicate[]::new);
            query.where(predicates);
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

    public List<Predicate> getPredicateList() {
        return predicateList;
    }

    public void setPredicateList(List<Predicate> predicateList) {
        this.predicateList = predicateList;
    }
    
    
    
}
