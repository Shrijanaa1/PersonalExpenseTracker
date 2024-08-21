package com.syntech.pem.bean;

import com.syntech.pem.model.Budget;
import com.syntech.pem.repository.BudgetRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author shrijanakarki
 */

@Named
@ViewScoped
public class BudgetBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private BudgetRepository budgetRepository;
    
    private List<Budget> budgets;
    
    private Budget selectedBudget;
    
//    @PostConstruct
//    public void init(){
//        selectedBudget = new Budget();
//        budgets = budgetRepository.findAll();
//    }
    
    @PostConstruct
    public void init() {
        if (budgetRepository == null) {
        System.out.println("BudgetRepository is null!");
    } else {
        System.out.println("BudgetRepository injected successfully.");
    }
        budgets = budgetRepository.findAll(); 
        selectedBudget = new Budget();
    }
    
    public void prepareCreateBudget() {
        this.selectedBudget = new Budget();
    }
    
     public void prepareEditBudget(Budget budget) {
        this.selectedBudget = budget;
    }
     
     public void saveOrUpdateBudget() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (selectedBudget.getId() != null) {
                budgetRepository.update(selectedBudget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget updated successfully!"));
            } else {
                budgetRepository.save(selectedBudget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget created successfully!"));                
            }
            budgets = budgetRepository.findAll(); // Refresh the list after save/update

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save/update budget: " + e.getMessage()));
        }
    }

     
    public void deleteBudget(Budget budget) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (budget != null) {
            try {
                budgetRepository.delete(budget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget deleted successfully!"));
                budgets = budgetRepository.findAll(); // Refresh the list after deletion
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete budget: " + e.getMessage()));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Budget is null!"));
        }
    }
    
    public String getRemark(Budget budget){
        BigDecimal remaining = budget.getRemainingAmount();
        if(remaining.compareTo(BigDecimal.ZERO) < 0){
            return "Overspent";
        }else if(remaining.compareTo(budget.getBudgetLimit()) < 0){
            return "Within Limit";
        }else{
            return "Budget Intact";
        }
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public Budget getSelectedBudget() {
        return selectedBudget;
    }

    public void setSelectedBudget(Budget selectedBudget) {
        this.selectedBudget = selectedBudget;
    }
    
    
    
}

