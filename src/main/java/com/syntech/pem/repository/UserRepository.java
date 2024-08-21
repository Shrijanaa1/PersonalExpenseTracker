package com.syntech.pem.repository;

import com.syntech.pem.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class UserRepository extends GenericRepository<User>{
    
    @PersistenceContext(unitName = "PEM_DS")
    private EntityManager entityManager;
    
    public UserRepository() {
        super(User.class);
    }

    @Override
    protected EntityManager entityManager() {
        return this.entityManager;
    }
    
    @Override
    public User findById(Long id){
        return entityManager().find(User.class, id);
    }
    
    
    @Override
    public List<User> findAll(){
        return super.findAll();
    } 
    
    public User getByUsername(String username) {
        List<User> users = findByAttribute("username", username);
        return users.isEmpty() ? null : users.get(0);
    }
    
}
