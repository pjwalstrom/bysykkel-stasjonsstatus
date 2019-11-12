package com.pj

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import spock.lang.Specification

class BysykkelRequestHandlerSpec extends Specification {
    BysykkelRequestHandler toTest
    HttpClient httpClient
    HttpResponse httpResponse
    HttpEntity httpResponseEntity

    def setup() {
        httpClient = Stub(HttpClient)
        httpResponse = Stub(HttpResponse)
        httpResponseEntity = Stub(HttpEntity)
        toTest = new BysykkelRequestHandler(httpClient, "foobar")
    }

    def 'sending a request to Bysykkel (using stubs) produces the correct entities'() {
        given:
        String stationInfoResponse = new File('src/test/resources/station_information.json').text
        httpResponseEntity.getContent() >> new ByteArrayInputStream(stationInfoResponse.getBytes())
        httpResponse.getEntity() >> httpResponseEntity
        httpClient.execute(_ as HttpGet) >> httpResponse

        when:
        String json = toTest.sendRequestToBysykkel("http://foo")

        then:
        json.contains('Stortingstunellen')
    }

}
