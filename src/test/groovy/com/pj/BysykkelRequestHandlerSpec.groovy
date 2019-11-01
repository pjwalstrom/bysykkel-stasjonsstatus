package com.pj

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import spock.lang.Specification

class BysykkelRequestHandlerSpec extends Specification {
    BysykkelRequestHandler toTest
    HttpClient httpClient
    HttpResponse response
    HttpEntity entity

    def setup() {
        httpClient = Stub(HttpClient)
        response = Stub(HttpResponse)
        entity = Stub(HttpEntity)
        toTest = new BysykkelRequestHandler(httpClient, "foobar")
    }

    def 'sending a request to Bysykkel (using stubs) produces the correct entities'() {
        given:
        String response = new File('src/test/resources/station_information.json').text;
        entity.getContent() >> new ByteArrayInputStream(response.getBytes())
        this.response.getEntity() >> entity
        httpClient.execute(_) >> this.response

        when:
        String json = toTest.sendRequestToBysykkel("http://foo")

        then:
        json.contains('Stortingstunellen')
    }

}
