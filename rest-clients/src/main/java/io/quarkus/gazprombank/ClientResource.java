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

@Path("api/client")
public class ClientResource {

    @Inject
    ClientService service;


    @Operation(
        summary = "Gets all clients"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = Client.class, type = SchemaType.ARRAY))
    )
    @GET
    public Response getAllClients(){
        List<Client> clients = service.findAllClients();
        return Response.ok(clients).build();
    }


    @Operation(
        summary = "Gets specific client by id"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = Client.class))
    )
    @APIResponse(
        responseCode = "204",
        description = "The hero is not found for a given identifier"
    )
    @GET
    @Path("/{id}")
    public Response getClient(@PathParam("id") Long id){
        Client client = service.findClientById(id);
        if(client != null){
            return Response.ok(client).build();
        }
        else{
            return Response.noContent().build();
        }
    }


    @Operation(
        summary = "Creates a new client"
    )
    @APIResponse(
        responseCode = "201",
        description = "The URI of the created client",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    public Response createClient(
        @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Client.class))) @Valid Client client, @Context UriInfo uriInfo){
        client = service.persistClient(client);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder()
            .path(Long.toString(client.id));
        return Response.created(builder.build()).build();
    }

    @Operation(
        summary = "Updates an existing client"
    )
    @APIResponse(
        responseCode = "200",
        description = "The updated client",
        content = @Content(mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = Client.class))
    )
    @PUT
    public Response updateClient(@RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Client.class))) @Valid Client client){
        client = service.updateClient(client);
        return Response.ok(client).build();
    }

    @Operation(
        summary = "Deletes an existing client"
    )
    @APIResponse(
        responseCode = "204"
    )
    @DELETE
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") Long id){
        service.deleteClient(id);
        return Response.noContent().build();
    }
}