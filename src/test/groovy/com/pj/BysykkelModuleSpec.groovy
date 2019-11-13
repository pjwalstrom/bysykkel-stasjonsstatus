package com.pj

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.glassfish.grizzly.http.server.HttpServer
import spock.lang.Specification

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

class BysykkelModuleSpec extends Specification {
    HttpServer server
    WebTarget target
    static final String LOCALHOST_URL = 'http://localhost:8080/foo/'
    static final String STATION_INFO_URL = 'http://stationinfo'
    static final String STATION_STATUS_URL = 'http://stationstatus'

    BysykkelRequestHandler requestHandler
    HttpClient httpClient
    HttpResponse stationInfoHttpResponse
    HttpResponse stationStatusHttpResponse
    HttpEntity stationInfoHttpRespEntity
    HttpEntity stationStatusHttpRespEntity

    def setup() {
        httpClient = Stub(HttpClient)
        stationInfoHttpResponse = Stub(HttpResponse)
        stationInfoHttpRespEntity = Stub(HttpEntity)
        stationStatusHttpResponse = Stub(HttpResponse)
        stationStatusHttpRespEntity = Stub(HttpEntity)
        requestHandler = new BysykkelRequestHandler(httpClient, "foobar")
        target = ClientBuilder.newClient().target(LOCALHOST_URL)
    }

    def cleanup() {
        server.shutdownNow()
    }

    def 'happy day'() {
        given:'mocking of external http resources'
        byte[] stationInfoResponse = new File('src/test/resources/station_information.json').bytes
        byte[] stationStatusResponse = new File('src/test/resources/station_status.json').bytes
        stationInfoHttpRespEntity.getContent() >> new ByteArrayInputStream(stationInfoResponse)
        stationInfoHttpResponse.getEntity() >> stationInfoHttpRespEntity
        stationStatusHttpRespEntity.getContent() >> new ByteArrayInputStream(stationStatusResponse)
        stationStatusHttpResponse.getEntity() >> stationStatusHttpRespEntity
        httpClient.execute({it.getURI().toString().contains(STATION_INFO_URL)} as HttpGet) >> stationInfoHttpResponse
        httpClient.execute({it.getURI().toString().contains(STATION_STATUS_URL)} as HttpGet) >> stationStatusHttpResponse

        when:'requests are sent to Bysykkel (using mocks) and eventually to localhost'
        String stationInfoJson = requestHandler.sendRequestToBysykkel(STATION_INFO_URL)
        String stationStatusJson = requestHandler.sendRequestToBysykkel(STATION_STATUS_URL)
        Map<String, Station> stationInfo = BysykkelJsonParser.parse(stationInfoJson)
        Map<String, Station> stationStatus = BysykkelJsonParser.parse(stationStatusJson)
        Map<String, Station> stations = BysykkelStationMerger.merge(stationInfo, stationStatus)
        server = BysykkelServer.startServer(LOCALHOST_URL, stations)
        Response response = target.path("stations").request().get(Response.class)

        then:
        response.status == Response.Status.OK.statusCode
        response.readEntity(String.class).contains('Tåsenløkka')
    }
}
