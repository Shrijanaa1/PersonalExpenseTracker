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

    public GenericLazyDataModel(GenericRepository<T> repository, Class<T> entityClass) {
        this.repository = repository;
    }

    @Override
    public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

        //For debugger purpose
        
//        List<T> loadedUsers = repository.getEntity(first, pageSize, sortBy, filterBy);
//        int numberOfUser = 0;
//        for (T user : loadedUsers) {
//            System.out.println(user.getId() + " added");
//            numberOfUser++;
//        }
//        System.out.println("total loaded users = " + numberOfUser);
//
//        return loadedUsers;

         return repository.getEntity(first, pageSize, sortBy, filterBy);
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return repository.countEntity(filterBy);
    }

}
