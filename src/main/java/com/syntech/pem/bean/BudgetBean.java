package com.syntech.pem.bean;

import com.syntech.pem.model.Budget;
import com.syntech.pem.model.CategoryType;
import com.syntech.pem.model.GenericLazyDataModel;
import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.BudgetRepository;
import com.syntech.pem.repository.TransactionRepository;
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
    
    @Inject
    private TransactionRepository transactionRepository;
    
    @Inject
    private SessionBean sessionBean;
    
    private Budget selectedBudget;
    
    private List<CategoryType> expenseCategories;
    
    private GenericLazyDataModel<Budget> lazyBudgets;
    
    @PostConstruct
    public void init(){
        sessionBean.checkSession();
        selectedBudget = new Budget();
        
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
            
            if (selectedBudget.getId() != null) {
                
                //check if budget limit has changed
                Budget existingBudget = budgetRepository.findById(selectedBudget.getId());
                if(existingBudget != null && existingBudget.getBudgetLimit().compareTo(selectedBudget.getBudgetLimit()) != 0){
                    
                    //Recalculate remaining amount based on the new budget limit
                    BigDecimal difference = selectedBudget.getBudgetLimit().subtract(existingBudget.getBudgetLimit());
                    BigDecimal newRemainingAmount = existingBudget.getRemainingAmount().add(difference);     
                    selectedBudget.setRemainingAmount(newRemainingAmount);
                }
                
                budgetRepository.update(selectedBudget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget updated successfully!"));
            } else {
                
                //New budget: initialize remainingAmount to limitAmount
                selectedBudget.setRemainingAmount(selectedBudget.getBudgetLimit());
                budgetRepository.save(selectedBudget);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget created successfully!"));                
            }
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
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete budget: " + e.getMessage()));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Budget is null!"));
        }
    }
    
    public void recalculateRemainingAmount(Budget budget){
        if(budget != null && budget.getCategory()!= null){
            // Retrieve all expense transactions for the category associated with the budget
            List<Transaction> transactions = transactionRepository.findByCategoryAndType(budget.getCategory(), TransactionType.Expense);
            BigDecimal totalExpenses = BigDecimal.ZERO;
            
            // Sum up all expenses
            for(Transaction transaction: transactions){
                totalExpenses = totalExpenses.add(transaction.getAmount());
            }
            
            // Calculate remaining amount and update the budget
            BigDecimal remainingAmount = budget.getBudgetLimit().subtract(totalExpenses);
            budget.setRemainingAmount(remainingAmount);
            budgetRepository.update(budget);
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Budget or Category is null!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Budget Remaining balance recalculated successfully!"));
    
    }
    
    public void recalculateAllRemainingAmount(){
        List<Budget> budgets = budgetRepository.findAll();
        for(Budget budget: budgets){
            recalculateRemainingAmount(budget);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "All remaining budgets recalculated successfully!"));
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

    public GenericLazyDataModel<Budget> getLazyBudgets() {
        return lazyBudgets;
    }
    
    
    
}

