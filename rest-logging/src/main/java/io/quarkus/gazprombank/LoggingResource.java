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

@Path("/api/logging")
@Produces(APPLICATION_JSON)
public class LoggingResource {

    @Inject
    LoggingService service;

    @Operation(
        summary = "Returns a random log"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = Logging.class, required = true))
    )
    @GET
    @Path("/random")
    public Response getRandomLog(){
        Logging log = service.findRandom();
        return Response.ok(log).build();
    }


    @Operation(
        summary = "Gets all logs"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = Logging.class, type = SchemaType.ARRAY))
    )
    @GET
    public Response getAllLogs(){
        List<Logging> logs = service.findAllLogs();
        return Response.ok(logs).build();
    }


    @Operation(
        summary = "Gets specific log by id"
    )
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, 
            schema = @Schema(implementation = Logging.class))
    )
    @APIResponse(
        responseCode = "204",
        description = "The log is not found for a given identifier"
    )
    @GET
    @Path("/{id}")
    public Response getLog(@PathParam("id") Long id){
        Logging log = service.findLogById(id);
        if(log != null){
            return Response.ok(log).build();
        }
        else{
            return Response.noContent().build();
        }
    }


    @Operation(
        summary = "Creates a new log"
    )
    @APIResponse(
        responseCode = "201",
        description = "The URI of the created log",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    public Response createLog(
        @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Logging.class))) @Valid Logging log, @Context UriInfo uriInfo){
        log = service.persistLog(log);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder()
            .path(Long.toString(log.id));
        return Response.created(builder.build()).build();
    }

    @Operation(
        summary = "Updates an existing log"
    )
    @APIResponse(
        responseCode = "200",
        description = "The updated hero",
        content = @Content(mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = Logging.class))
    )
    @PUT
    public Response updateLog(@RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Logging.class))) @Valid Logging log){
        log = service.updateLog(log);
        return Response.ok(log).build();
    }

    @Operation(
        summary = "Deletes an existing log"
    )
    @APIResponse(
        responseCode = "204"
    )
    @DELETE
    @Path("/{id}")
    public Response deleteLog (@PathParam("id") Long id){
        service.deleteLog(id);
        return Response.noContent().build();
    }
}