package com.pj;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.Map;

class BysykkelServer {
    //TODO (PJW, 2019-11-10) Move this to a cache
    static Map<String, Station> STATIONS;

    static HttpServer startServer(String baseUrl, Map<String, Station> stations) {
        STATIONS = stations;
        final ResourceConfig rc = new ResourceConfig().packages("com.pj");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUrl), rc);
    }
}
