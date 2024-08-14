package com.syntech.pem.repository;

import com.syntech.pem.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Stateless
public class UserRepository extends GenericRepository<User>{

    @Override
    public User findById(Long id){
        return getEntityManager().find(User.class, id);
    }
    
    @Override
    public List<User> findAll(){
        TypedQuery<User> query = getEntityManager().createQuery("SELECT  u FROM User u", User.class);
        return query.getResultList();
   } 
    

    public User getByUsername(String username) {
            try {
                return getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }

}
