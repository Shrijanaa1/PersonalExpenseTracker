package com.syntech.pem.api;

import com.syntech.pem.model.Budget;
import com.syntech.pem.repository.BudgetRepository;
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
        List<Budget> budgets = budgetRepository.findAll();
        return Response.ok(budgets).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getBudgetById(@PathParam("id") Long id){
        Budget budget = budgetRepository.findById(id);
        if(budget == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(budget).build();
    }
    
    @POST
    public Response createBudget(Budget budget) {
        try {
            budgetRepository.save(budget);
            return Response.status(Response.Status.CREATED).entity(budget).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error creating budget: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateBudget(@PathParam("id")Long id, Budget budget){
        Budget existingBudget = budgetRepository.findById(id);
        if(existingBudget != null){
            budget.setId(id);
            budgetRepository.update(budget);
            return Response.ok(budget).build();  
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBudget(@PathParam("id")Long id){
        Budget budget = budgetRepository.findById(id);
        if(budget != null){
            budgetRepository.delete(budget);
            return Response.noContent().build();           
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }               
    }
}
