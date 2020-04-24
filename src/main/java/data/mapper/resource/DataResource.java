package data.mapper.resource;

import data.mapper.dto.DataMapper;
import data.mapper.service.DataService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataResource {

    @Inject
    DataService dataService;

    @POST
    public Response create(@Valid DataMapper data) {
        DataMapper dataMapper = dataService.save(data);
        return Response.status(Response.Status.CREATED).entity(dataMapper).build();
    }

    @GET
    @Path("/filter/{providerId}")
    public Response filterData(@PathParam Long providerId, @QueryParam("name") String name,
                               @QueryParam("age") String age, @QueryParam("timestamp") String timestamp) {
        return Response.ok(dataService.getFilteredData(providerId, name, age, timestamp)).build();
    }



}