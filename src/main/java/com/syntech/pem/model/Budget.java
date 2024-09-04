package com.syntech.pem.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
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
    
//    @NotNull
    @Column(name = "remainingAmount", nullable = false)
    private BigDecimal remainingAmount;
    
    @NotNull(message = "StartDate should not be null")
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;
    
    @NotNull(message = "EndDate should not be null")
    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;
    
    @Transient
    @Column(name = "remark", nullable = false)
    private String remark;
    
    public Budget() { }

    public Budget(CategoryType category, BigDecimal budgetLimit, BigDecimal remainingAmount, LocalDate startDate, LocalDate endDate, String remark) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
