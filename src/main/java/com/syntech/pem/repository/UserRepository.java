package com.syntech.pem.repository;

import com.syntech.pem.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


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
    
    
    //authenticate for login
    public User authenticate(String username, String password) {
        User user = getByUsername(username);
        if (user != null) {
            // Extract the stored hashed password and salt
            String[] parts = user.getPassword().split(":");
            
            // Ensure the password was correctly stored in the format "hashedPassword:salt"
            if (parts.length != 2) {
                throw new IllegalStateException("Stored password format is invalid for user: " + username);
            }
        
            String storedHashedPassword = parts[0];
            String salt = parts[1];
            
            // Hash the provided password with the stored salt
            String hashedPassword = hashPassword(password, salt);
            
            // Check if the hashed password matches the stored hashed password
            if (storedHashedPassword.equals(hashedPassword)) {
                return user; // Password matches
            }
        }
        return null; // Password does not match or user not found
    }
    
    public String hashPassword(String password, String salt) {
        try {
            // Initialize SHA-256 digest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Update digest with salt
            digest.update(salt.getBytes());
            
            // Hash the password
            byte[] hashedPassword = digest.digest(password.getBytes());
            
             // Return hashed password encoded in Base64
            return Base64.getEncoder().encodeToString(hashedPassword);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // RuntimeException if algorithm not found
        }
    }
    
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt); // Generate random bytes
        return Base64.getEncoder().encodeToString(salt); // Return salt encoded in Base64
    }
    
    @Transactional
    @Override
    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(user.getPassword(), salt);
            user.setPassword(hashedPassword + ":" + salt);
        }
        // Call the generic save method after modifying the password
        return super.save(user);
    }
    
    public String extractSalt(String storedPassword) {
    String[] parts = storedPassword.split(":");
    if (parts.length != 2) {
        throw new IllegalStateException("Stored password format is invalid");
    }
    return parts[1];
}

}
