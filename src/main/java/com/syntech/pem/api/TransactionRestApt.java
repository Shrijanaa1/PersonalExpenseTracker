package com.syntech.pem.api;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.repository.TransactionRepository;
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

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionRestApt {
    
    @Inject
    private TransactionRepository transactionRepository;
    
    @GET
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }
    
    @GET
    @Path("/{id}")
    public Response getTransactionById(@PathParam("id") Long id){
        Transaction transaction = transactionRepository.findById(id);
        if(transaction == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(transaction).build();
    }
    
    @POST
    public Response createTransaction(Transaction transaction){
        transactionRepository.save(transaction);
        return Response.status(Response.Status.CREATED).entity(transaction).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateTransaction(@PathParam("id")Long id, Transaction transaction){
        Transaction existingTransaction = transactionRepository.findById(id);
        if(existingTransaction == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        transaction.setId(id);
        transactionRepository.update(transaction);
        return Response.ok(transaction).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteTransaction(@PathParam("id")Long id){
        Transaction transaction = transactionRepository.findById(id);
        if(transaction == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        transactionRepository.delete(transaction);
        return Response.noContent().build();
    }
}
