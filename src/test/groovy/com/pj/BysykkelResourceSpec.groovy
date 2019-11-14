package com.pj

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.glassfish.grizzly.http.server.HttpServer
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

class BysykkelResourceSpec extends Specification {

    @Shared
    HttpServer server
    @Shared
    WebTarget target
    static final Station STATION1 = new Station('1', 'Mordor', 1, 0)
    static final Station STATION2 = new Station('2', 'Isengard', 0, 1)
    static final String BASE_URL = 'http://localhost:8080/foo/'

    def setupSpec() {
        server = BysykkelServer.startServer(BASE_URL, [(STATION1.id): STATION1, (STATION2.id): STATION2])
        target = ClientBuilder.newClient().target(BASE_URL)
    }

    def cleanupSpec() {
        server.shutdownNow()
    }

    def 'calling stations resource results in a HTTP-status 200/OK with a Station'() {
        when:
        Response response = target.path("stations").request().get(Response.class)

        then:
        response.status == Response.Status.OK.statusCode
        String entity = response.readEntity(String.class)
        entity.contains(new Gson().toJson(STATION1))

        and: 'json is valid'
        new JsonSlurper().parseText(entity)
    }

    def 'calling station/id resource results in a HTTP-status 200/OK with a Station with valid json'() {
        given:
        JsonSlurper jsonSlurper = new JsonSlurper()

        when:
        Response response = target.path("stations/" + STATION1.id).request().get(Response.class)

        then:
        response.status == Response.Status.OK.statusCode
        jsonSlurper.parseText(response.readEntity(String.class)) == jsonSlurper.parseText(new Gson().toJson(STATION1))
    }

    def 'calling missing resource results in a HTTP-status 404/NOT FOUND'() {
        expect:
        target.path("bar").request().get(Response.class).status == Response.Status.NOT_FOUND.statusCode
    }

    def 'calling valid resource but with missing id results in a HTTP-status 404/NOT FOUND but with a customized error message'() {
        when:
        Response response = target.path("stations/none").request().get(Response.class)

        then:
        response.status == Response.Status.NOT_FOUND.statusCode
        response.readEntity(String.class).contains(BysykkelResource.ERROR_MSG_NO_STATION_FOUND)
    }
}
