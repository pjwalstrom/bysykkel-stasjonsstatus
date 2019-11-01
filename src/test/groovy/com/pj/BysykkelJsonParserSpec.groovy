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

    def 'parse json response from bysykkel with station status'() {
        given:
        String stationStatusJson = new File('src/test/resources/station_status.json').text

        when:
        Map<String, Station> stations = BysykkelJsonParser.parse(stationStatusJson)

        then:
        stations.size() == 246
        stations.get("1101").bikesAvailable == 2
        stations.get("1101").locksAvailable == 19
        stations.get("1101").name.isEmpty()
    }

    def 'parse json response from bysykkel with station information'() {
        given:
        String stationInfoJson = new File('src/test/resources/station_information.json').text

        when:
        Map<String, Station> stations = BysykkelJsonParser.parse(stationInfoJson)

        then:
        stations.size() == 246
        stations.get("1101").bikesAvailable == 0
        stations.get("1101").locksAvailable == 0
        stations.get("1101").name == "Stortingstunellen"
    }
}
