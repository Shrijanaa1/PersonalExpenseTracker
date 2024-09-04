package com.syntech.pem.api;

import com.syntech.pem.model.Budget;
import com.syntech.pem.repository.BudgetRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author shrijanakarki
 */

@Path("/budgets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BudgetRestApi {
    
    @Inject
    private BudgetRepository budgetRepository;
    
    @GET
    public Response getAllBudgets() {
        try{
            
             
            System.out.println("Time"+ LocalDate.now().toString() +" ST "+new Date());
            List<Budget> budgets = budgetRepository.findAll();
            return RestResponse.responseBuilder("true", "200","Budgets retrieved successfully", budgets.toString());
        }catch(Exception e){
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }      
    }
    
    @GET
    @Path("/{id}")
    public Response getBudgetById(@PathParam("id") Long id) {
        try {
            Budget budget = budgetRepository.findById(id);
            if (budget == null) {
                return RestResponse.responseBuilder("true", "200", "Budget found", budget.toString());
            } else {
                return RestResponse.responseBuilder("false", "404", "Budget not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }
    
    @POST
    public Response createBudget(Budget budget) {
        try {
            budgetRepository.save(budget);
            return RestResponse.responseBuilder("true", "201", "Budget created successfully", budget.toString());
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "400", "Failed to create budget", e.getMessage());
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateBudget(@PathParam("id") Long id, Budget budget) {
        try {
            Budget existingBudget = budgetRepository.findById(id);
            if (existingBudget != null) {
                if (budget.getRemainingAmount() == null) {
                    budget.setRemainingAmount(budget.getBudgetLimit());
                }   
                
                budget.setId(id);
                budgetRepository.update(budget);
               return RestResponse.responseBuilder("true", "200", "Budget updated successfully", budget.toString());
            } else {
                return RestResponse.responseBuilder("false", "404", "Budget not found", null);
            }

        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBudget(@PathParam("id")Long id){
        try{
            Budget budget = budgetRepository.findById(id);
            if(budget != null){
                budgetRepository.delete(budget);
                return RestResponse.responseBuilder("true", "204", "Budget deleted successfully", null);          
            }else{
                return RestResponse.responseBuilder("false", "404", "Budget not found", null);  
            }               
        }catch(Exception e){
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }
 
}
