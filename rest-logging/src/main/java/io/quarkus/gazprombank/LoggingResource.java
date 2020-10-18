package io.quarkus.gazprombank;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

@Path("/api/logging")
@Produces(APPLICATION_JSON)
public class LoggingResource {

    @Inject
    LoggingService service;

    @GET
    @Path("/random")
    public Response getRandomLog(){
        Logging log = service.findRandom();
        return Response.ok(log).build();
    }

    @GET
    public Response getAllLogs(){
        List<Logging> logs = service.findAllLogs();
        return Response.ok(logs).build();
    }

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

    @POST
    public Response createLog(@Valid Logging log, @Context UriInfo uriInfo){
        log = service.persistLog(log);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder()
            .path(Long.toString(log.id));
        return Response.created(builder.build()).build();
    }

    @PUT
    public Response updateLog(@Valid Logging log){
        log = service.updateLog(log);
        return Response.ok(log).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLog (@PathParam("id") Long id){
        service.deleteLog(id);
        return Response.noContent().build();
    }
}