package data.mapper.resource;

import data.mapper.service.ProviderDataService;
import util.JsonMapperUtil;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataResource {

    @Inject
    ProviderDataService dataService;
    

    @POST
    public Response create(JsonObject providerData) {
        return Response.status(Response.Status.CREATED)
        .entity(dataService.save(providerData)).build();
    }

    @GET
    @Path("/filter/{providerId}")
    public Response filterData(@PathParam Integer providerId, @QueryParam("name") String name,
                               @QueryParam("age") String age, @QueryParam("timestamp") String timestamp)  {
        return Response.ok(dataService.getFilteredData(providerId, name, age, timestamp)).build();
    }

    @POST
    @Path("/provider")
    public Response saveProvider(JsonObject providerSpec) {
    return Response.status(Response.Status.CREATED)
            .entity(dataService.saveProviderSpecification(JsonMapperUtil
    .MapProvider("providerId", "fields", providerSpec))).build();
    }


}