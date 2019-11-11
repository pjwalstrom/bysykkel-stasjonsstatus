package com.pj

import org.glassfish.grizzly.http.server.HttpServer
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

class BysykkelResourceSpec extends Specification {

    @Shared
    private HttpServer server
    @Shared
    private WebTarget target
    private static final String BASE_URL = 'http://localhost:8080/foo/'

    void setupSpec() {
        server = BysykkelServer.startServer(BASE_URL, [:])
        target = ClientBuilder.newClient().target(BASE_URL)
    }

    void cleanupSpec() {
        server.shutdownNow()
    }

    def 'calling valid resource results in a HTTP-status 200/OK'() {
        expect:
        target.path("station").request().get(Response.class).status == Response.Status.OK.statusCode
    }

    def 'calling missing resource results in a HTTP-status 404/NOT FOUND'() {
        expect:
        target.path("bar").request().get(Response.class).status == Response.Status.NOT_FOUND.statusCode
    }

    def 'calling valid resource but with missing id results in a HTTP-status 404/NOT FOUND but with a customized error message'() {
        when:
        Response response = target.path("station/none").request().get(Response.class)

        then:
        response.status == Response.Status.NOT_FOUND.statusCode
        response.readEntity(String.class).contains(BysykkelResource.ERROR_MSG_NO_STATION_FOUND)
    }
}
