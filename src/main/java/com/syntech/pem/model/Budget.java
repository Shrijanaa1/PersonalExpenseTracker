package com.syntech.pem.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
    private BigDecimal budgetLimit;
    
    
    private BigDecimal remainingAmount;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Transient
    private String remark;
    
    public Budget() { }

    public Budget(CategoryType category, BigDecimal budgetLimit, BigDecimal remainingAmount, Date startDate, Date endDate, String remark) {
        this.category = category;
        this.budgetLimit = budgetLimit;
        this.remainingAmount = remainingAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
    }
    

    @PrePersist
    public void prePersist(){
        if(this.remainingAmount == null){
            this.remainingAmount = this.budgetLimit;
        }
    }
    
    
    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
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

    public BigDecimal getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(BigDecimal budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
}
