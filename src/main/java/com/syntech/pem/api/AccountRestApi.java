package com.syntech.pem.api;

import com.syntech.pem.model.Account;
import com.syntech.pem.repository.AccountRepository;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountRestApi {

    @Inject
    private AccountRepository accountRepository;

    @PUT
    @Path("/{id}")
    public Response updateAccount(@PathParam("id") Long id, Account account) {
        try {
            Account existingAccount = accountRepository.findById(id);
            if (existingAccount != null) {
                account.setId(id);
                accountRepository.update(account);
                 return RestResponse.responseBuilder("true", "200", "Account updated successfully", account.toString());
            } else {
                return RestResponse.responseBuilder("false", "404", "Account not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }

    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") Long id) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        accountRepository.delete(account);
        return Response.noContent().build();
    }
}
