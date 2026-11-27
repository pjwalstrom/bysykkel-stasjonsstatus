package com.pj

import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.core5.http.ClassicHttpResponse
import org.apache.hc.core5.http.HttpEntity
import spock.lang.Specification

class BysykkelRequestHandlerSpec extends Specification {
    BysykkelRequestHandler toTest
    HttpClient httpClient
    ClassicHttpResponse httpResponse
    HttpEntity httpResponseEntity

    def setup() {
        httpClient = Stub(HttpClient)
        httpResponse = Stub(ClassicHttpResponse)
        httpResponseEntity = Stub(HttpEntity)
        toTest = new BysykkelRequestHandler(httpClient, "foobar")
    }

    def 'sending a request to Bysykkel (using stubs) produces the correct entities'() {
        given:
        byte[] stationInfoResponse = new File('src/test/resources/station_information.json').bytes
        httpResponseEntity.getContent() >> new ByteArrayInputStream(stationInfoResponse)
        httpResponse.getEntity() >> httpResponseEntity
        httpClient.execute(_ as HttpGet) >> httpResponse

        when:
        String json = toTest.sendRequestToBysykkel("http://foo")

        then:
        json.contains('Stortingstunellen')
    }

}
