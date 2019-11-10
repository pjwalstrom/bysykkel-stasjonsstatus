package com.pj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("station")
public class BysykkelResource {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    public String getAll() {
        StringBuilder result = new StringBuilder();
        Collection<Station> stations = BysykkelServer.STATIONS.values();
        for (Station station : stations) {
            result.append(station.toString()).append("\n");
        }
        return result.toString();
    }
}
