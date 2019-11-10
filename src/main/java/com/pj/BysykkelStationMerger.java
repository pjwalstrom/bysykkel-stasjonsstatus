package com.pj;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BysykkelStationMerger {

    static Map<String, Station> merge(Map<String, Station> stations1, Map<String, Station> stations2) {
        Map<String, Station> result = new HashMap<>(stations1);
        stations2.forEach((key, value) -> result.merge(key, value, (station1, station2) ->
                isStationInformation(station1) ?
                        new Station(station1.getId(), station1.getName(), station2.getLocksAvailable(), station2.getBikesAvailable()) :
                        new Station(station2.getId(), station2.getName(), station1.getLocksAvailable(), station1.getBikesAvailable()))
        );
        return result;
    }

    private static boolean isStationInformation(Station station) {
        return StringUtils.isNotBlank(station.getName());
    }
}
