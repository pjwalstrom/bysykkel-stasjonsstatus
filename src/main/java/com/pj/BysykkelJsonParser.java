package com.pj;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BysykkelJsonParser {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelJsonParser.class);

    static List<Map> parse(String json) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyList();
        }
        try {
            return (List<Map>) ((Map) new Gson().fromJson(json, Map.class).getOrDefault("data", Collections.emptyMap())).getOrDefault("stations", Collections.emptyList());
        } catch (JsonSyntaxException e) {
            LOG.warn("Could not parse this string as json: " + json);
            return Collections.emptyList();
        }
    }

    static Collection<Station> mergeJsons(List<Map<String, Object>> stationInformations, List<Map<String, Object>> stationStatuses) {
        Map<String, Station> stationsMap = new HashMap<>();
        for (Map<String, Object> stationInformation : stationInformations) {
            String stationId = (String)stationInformation.getOrDefault("station_id","-1");
            Station station = new Station(stationId, (String)stationInformation.getOrDefault("name","Mordor"));
            stationsMap.put(stationId, station);
        }
        for (Map<String, Object> stationStatus : stationStatuses) {
            String stationId = (String)stationStatus.getOrDefault("station_id","-1");
            Station station = stationsMap.getOrDefault(stationId, new Station(stationId, "Mordor"));
            station.setBikesAvailable(((Double)stationStatus.getOrDefault("num_bikes_available", -1)).intValue());
            station.setLocksAvailable(((Double)stationStatus.getOrDefault("num_docks_available", -1)).intValue());
        }
        return stationsMap.values();
    }
}
