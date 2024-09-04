package com.syntech.pem.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author shrijanakarki
 */

@Entity
@Table(name = "budgets")
public class Budget extends BaseIdEntity{
    
    @NotNull(message = "Category should not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CategoryType category;
    
    @NotNull(message = "Budget Limit should not be null")
    @Column(name = "budgetLimit", nullable = false)
    private BigDecimal budgetLimit;
    
    @Column(name = "remainingAmount")
    private BigDecimal remainingAmount;
    
    @NotNull(message = "StartDate should not be null")
    @JsonbDateFormat("yyyy-MM-dd HH:mm:ss.S")
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    
    
    @NotNull(message = "EndDate should not be null")
    @JsonbDateFormat("yyyy-MM-dd HH:mm:ss.S")
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    
    @Transient
    @Column(name = "remark")
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
    
    @PreUpdate
    public void preUpdate(){
        if (this.remainingAmount == null) {
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

    @Override
    public String toString() {
        return "Budget{" + "category=" + category + ", budgetLimit=" + budgetLimit + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }
    
}