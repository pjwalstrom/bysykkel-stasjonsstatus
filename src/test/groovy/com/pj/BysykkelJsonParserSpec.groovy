package com.pj

import spock.lang.Specification
import spock.lang.Unroll

class BysykkelJsonParserSpec extends Specification {
    def 'parse an aribtrary string should give no results'() {
        expect:
        BysykkelJsonParser.parse("foo bar").isEmpty()
    }

    @Unroll
    def 'parse json response from bysykkel with missing elements in file #filename'() {
        expect:
        BysykkelJsonParser.parse(new File(filename).text).isEmpty()

        where:
        filename << ['src/test/resources/empty_response.json', 'src/test/resources/empty_response_missing_stations.json', 'src/test/resources/empty_response_missing_data.json']
    }

    def 'parse simple json response from bysykkel with station status'() {
        given:
        String json = new File('src/test/resources/station_status.json').text;

        when:
        List<Map> result = BysykkelJsonParser.parse(json)

        then:
        result.size() == 246
        result.first().station_id == '1101'
    }

    def 'parse simple json response from bysykkel with station information'() {
        given:
        String json = new File('src/test/resources/station_information.json').text;

        when:
        List<Map> result = BysykkelJsonParser.parse(json)

        then:
        result.size() == 246
        result.first().station_id == '1101'
    }
}
