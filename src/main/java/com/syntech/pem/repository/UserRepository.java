    package com.syntech.pem.repository;

    import com.syntech.pem.model.User;
    import java.util.List;
    import javax.persistence.EntityManager;
    import javax.persistence.PersistenceContext;
    import javax.transaction.Transactional;


    public class UserRepository {

        @PersistenceContext(unitName = "PEM_DS")
        private EntityManager entityManager;

        @Transactional
        public void save(User user){    
            entityManager.persist(user);
        }

        public User getById(Long id){
            return entityManager.find(User.class, id);
        }

        @Transactional
        public void update(User user){
            entityManager.merge(user);
        }

        @Transactional
        public void delete(Long id){
            User user = getById(id);
            if(user != null){
                entityManager.remove(user);
            }
        }

        public List<User> getAll(){
            return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        }

        

        public User getByUsername(String username) {
            try {
                return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (Exception e) {
                return null;
            }
        }

    }
