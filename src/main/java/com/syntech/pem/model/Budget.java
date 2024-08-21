package com.syntech.pem.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author shrijanakarki
 */

@Entity
@Table(name = "budgets")
public class Budget extends BaseIdEntity{
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryType category;
    
    @NotNull
    private double limit;
    
    @NotNull
    private Date startDate;
    
    @NotNull
    private Date endDate;
    
    public Budget() { }

    public Budget(CategoryType category, double limit, Date startDate, Date endDate) {
        this.category = category;
        this.limit = limit;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
}
