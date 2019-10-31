package com.pj

import spock.lang.Specification

class BysykkelJsonParserSpec extends Specification {
    def 'parse an aribtrary string should give no results'() {
        expect:
        BysykkelJsonParser.parse("foo bar").isEmpty()
    }

    def 'parse json response from bysykkel with no results'() {
        given:
        String json = new File('src/test/resources/empty_response.json').text;

        when:
        List result = BysykkelJsonParser.parse(json)

        then:
        result.isEmpty()
    }

    def 'parse simple json response from bysykkel'() {
        given:
        String json = new File('src/test/resources/station_information.json').text;

        when:
        List<Map> result = BysykkelJsonParser.parse(json)

        then:
        !result.isEmpty()
    }
}
