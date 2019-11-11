package com.pj;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
public class BysykkelResource {
    static final String ERROR_MSG_NO_STATION_FOUND = "No station with id ";

    @GET
    @Path("stations")
    public Response getStations() {
        String result = BysykkelServer.STATIONS.values().stream().map(Station::toString).collect(Collectors.joining("\n"));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("stations/{id}")
    public Response getStation(@PathParam("id") String id) {
        if (!BysykkelServer.STATIONS.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity(ERROR_MSG_NO_STATION_FOUND + id).build();
        }
        return Response.status(Response.Status.OK).entity(BysykkelServer.STATIONS.get(id).toString()).build();
    }
}
