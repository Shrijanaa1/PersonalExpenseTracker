package com.syntech.pem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseIdEntity{
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;

    public User() {}
    
    //private constructor to enforce usage of the builder  
    private User(UserBuilder builder){
        this.username = builder.username;
        this.password = builder.password;
    }

    //Put Getters and Setters here
    public String getUsername(){
        return username;
    } 
    
    public String getPassword(){
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public static class UserBuilder{
        private String username;
        private String password;
        
        public UserBuilder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public UserBuilder setPassword(String password){
            this.password = password;
            return this;
        }
        
        public User build(){
            return new User(this);
        }
    }
    
   
}
