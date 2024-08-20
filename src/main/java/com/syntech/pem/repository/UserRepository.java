package com.syntech.pem.repository;

import com.syntech.pem.model.User;
import java.util.List;
import javax.ejb.Stateless;


@Stateless
public class UserRepository extends GenericRepository<User>{

    @Override
    public User findById(Long id){
        return getEntityManager().find(User.class, id);
    }
    
    
    @Override
    public List<User> findAll(){
        return findAll(User.class);
    } 
    
    public User getByUsername(String username) {
        List<User> users = findByAttribute("username", username, User.class);
        return users.isEmpty() ? null : users.get(0);
    }

//    public User getByUsername(String username) {
//            try {
//                return getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
//                        .setParameter("username", username)
//                        .getSingleResult();
//            } catch (NoResultException e) {
//                return null;
//            }
//        }

}
