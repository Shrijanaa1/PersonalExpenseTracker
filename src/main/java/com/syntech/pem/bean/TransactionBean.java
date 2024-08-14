package com.syntech.pem.bean;

import com.syntech.pem.service.TransactionService;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class TransactionBean implements Serializable{
    
    @Inject
    private TransactionService transactionService;
    
    
    
}
