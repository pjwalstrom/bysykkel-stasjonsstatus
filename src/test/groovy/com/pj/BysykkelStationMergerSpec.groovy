package com.pj

import spock.lang.Specification

class BysykkelStationMergerSpec extends Specification {

    def 'merging two maps with Station-objects results in a list of Station-objects'() {
        given:
        Map<String, Station> stations1 = BysykkelJsonParser.parse(new File('src/test/resources/station_status.json').text)
        Map<String, Station> stations2 = BysykkelJsonParser.parse(new File('src/test/resources/station_information.json').text)
        Station myStation = new Station("1101", "Stortingstunellen", 19, 2)

        when:
        Map<String, Station> stations = BysykkelStationMerger.merge(stations1, stations2)

        then:
        !stations.isEmpty()
        stations.get('1101').equals(myStation)
    }

    def 'merging two empty maps results in an empty map'() {
        expect:
        BysykkelStationMerger.merge([:], [:]).isEmpty()
    }
}
