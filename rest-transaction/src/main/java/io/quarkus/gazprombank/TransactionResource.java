package io.quarkus.gazprombank;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

@Path("/api/transaction")
public class TransactionResource {

    @Inject
    MoneyTransactionService service;


    @Operation(
        summary = "Gets all transactions"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = MoneyTransaction.class, type = SchemaType.ARRAY))
    )
    @GET
    public Response getAllTransactions(){
        List<MoneyTransaction> transactions = service.findAllTransactions();
        return Response.ok(transactions).build();
    }


    @Operation(
        summary = "Gets specific transaction by id"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = MoneyTransaction.class))
    )
    @APIResponse(
        responseCode = "204",
        description = "The hero is not found for a given identifier"
    )
    @GET
    @Path("/{id}")
    public Response getTransaction(@PathParam("id") Long id){
        MoneyTransaction transaction = service.findTransactionById(id);
        if(transaction != null){
            return Response.ok(transaction).build();
        }
        else{
            return Response.noContent().build();
        }
    }


    @Operation(
        summary = "Creates a new transaction"
    )
    @APIResponse(
        responseCode = "201",
        description = "The URI of the created transaction",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    public Response createTransaction(
        @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = MoneyTransaction.class))) @Valid MoneyTransaction transaction, @Context UriInfo uriInfo){
        transaction = service.persistTransaction(transaction);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder()
            .path(Long.toString(transaction.id));
        return Response.created(builder.build()).build();
    }

    @Operation(
        summary = "Updates an existing transaction"
    )
    @APIResponse(
        responseCode = "200",
        description = "The updated transaction",
        content = @Content(mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = MoneyTransaction.class))
    )
    @PUT
    public Response updateTransaction(@RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = MoneyTransaction.class))) @Valid MoneyTransaction transaction){
        transaction = service.updateTransaction(transaction);
        return Response.ok(transaction).build();
    }

    @Operation(
        summary = "Deletes an existing transaction"
    )
    @APIResponse(
        responseCode = "204"
    )
    @DELETE
    @Path("/{id}")
    public Response deleteTransaction(@PathParam("id") Long id){
        service.deleteTransaction(id);
        return Response.noContent().build();
    }
}