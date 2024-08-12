package com.syntech.pem.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseIdEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId(){
        return id;
    }
        
}

















    //To make id field immutable after the entity is created: Don't provide setter method.
    
//    public void setId(Long id){
//        this.id = id;
//    }