package com.syntech.pem.model;

import com.syntech.pem.repository.GenericRepository;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author shrijanakarki
 */

public class GenericLazyDataModel<T extends BaseIdEntity> extends LazyDataModel<T> {
    
    private final GenericRepository<T> repository;
    
    public GenericLazyDataModel(GenericRepository<T> repository, Class<T> entityClass){
        this.repository = repository;
    }
    

    @Override
    public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy){
        List<T> data = repository.findRange(first, pageSize, sortBy, filterBy);
        this.setRowCount(repository.count(filterBy));
        return data;
    }
               
    @Override
    public int count(Map<String, FilterMeta> filterBy){
        return repository.count(filterBy);
    }
   
    
}

    
    
    
