package com.pj;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.pj.BysykkelServer.STATIONS;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BysykkelResource {
    static final String ERROR_MSG_NO_STATION_FOUND = "No station with id ";
    private static final Gson GSON = new Gson();

    @GET
    @Path("stations")
    public Response getStations() {
        JsonArray jsonArray = new JsonArray();
        for (Station station : STATIONS.values()) {
            jsonArray.add(GSON.toJsonTree(station));
        }
        return Response.status(Response.Status.OK).entity(GSON.toJson(jsonArray)).build();
    }

    @GET
    @Path("stations/{id}")
    public Response getStation(@PathParam("id") String id) {
        if (!STATIONS.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity(ERROR_MSG_NO_STATION_FOUND + id).build();
        }
        return Response.status(Response.Status.OK).entity(GSON.toJson(STATIONS.get(id))).build();
    }
}
