package com.syntech.pem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class BaseIdEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //Stores timestamp when the entity is first persisted   
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    
    //Stores timestamp when the entity is last updated 
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
       
    
    //This method called before entity is persisted setting both createdAt and updatedAt
    @PrePersist
    protected void OnCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    //This method called before entity is updated, setting it to current timestamp
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    
}
















    
