package com.syntech.pem.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shrijanakarki
 */

public enum CategoryType {
    Salary(TransactionType.Income),
    Interest(TransactionType.Income),
    Rent(TransactionType.Income),
    Freelancing(TransactionType.Income),
    Food(TransactionType.Expense),
    Entertainment(TransactionType.Expense),
    Shopping(TransactionType.Expense),
    Travel(TransactionType.Expense),
    Education(TransactionType.Expense),
    Others(TransactionType.Expense);
    
    private final TransactionType type;
    
    CategoryType(TransactionType type){
        this.type = type;
    }

    public TransactionType getType() {
        return type;
    }
    
    public static List<CategoryType> getCategoriesForType(TransactionType type){
        List<CategoryType> categories = new ArrayList<>();
        for(CategoryType category: CategoryType.values()){
            if(category.getType().equals(type)){
                categories.add(category);
            }
        }
        return categories;
    }
    
}
