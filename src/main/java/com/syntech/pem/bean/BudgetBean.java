package com.syntech.pem.bean;

import com.syntech.pem.model.Budget;
import com.syntech.pem.model.CategoryType;
import com.syntech.pem.model.GenericLazyDataModel;
import com.syntech.pem.model.TransactionType;
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
    
    private List<CategoryType> expenseCategories;
    
    private GenericLazyDataModel<Budget> lazyBudgets;
    
    @PostConstruct
    public void init(){
        selectedBudget = new Budget();
        budgets = budgetRepository.findAll();
        
        // Filter expense categories
        expenseCategories = CategoryType.getCategoriesForType(TransactionType.Expense);
        lazyBudgets  = new GenericLazyDataModel<>(budgetRepository, Budget.class);
        
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
            if (selectedBudget.getRemainingAmount() == null) {
            selectedBudget.setRemainingAmount(BigDecimal.ZERO); // Set default value if null
            }
            
            if (selectedBudget.getId() != null) {
                budgetRepository.update(selectedBudget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget updated successfully!"));
            } else {
                budgetRepository.save(selectedBudget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget created successfully!"));                
            }
            budgets = budgetRepository.findAll(); // Refresh the list after save/update

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save/update budget: "));
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

    public List<CategoryType> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(List<CategoryType> expenseCategories) {
        this.expenseCategories = expenseCategories;
    }

    public void setLazyBudgets(GenericLazyDataModel<Budget> lazyBudgets) {
        this.lazyBudgets = lazyBudgets;
    }
    
    
    
}

