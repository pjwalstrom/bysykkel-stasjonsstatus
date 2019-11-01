package com.pj;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BysykkelJsonParser {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelJsonParser.class);

    static Map<String, Station> parse(String json) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }
        try {
            List<Map> fromJson = (List<Map>) ((Map) new Gson().fromJson(json, Map.class).getOrDefault("data", Collections.emptyMap())).getOrDefault("stations", Collections.emptyList());
            return createStationsMap(fromJson);
        } catch (JsonSyntaxException e) {
            LOG.warn("Could not parse this string as json: " + json);
            return Collections.emptyMap();
        }
    }

    private static Map<String, Station> createStationsMap(List<Map> stationsList) {
        Map<String, Station> stationsMap = new HashMap<>();
        for (Map map : stationsList) {
            String stationId = (String) map.getOrDefault("station_id", "-1");
            Station station = createStation(map, stationId);
            stationsMap.put(stationId, station);
        }
        return stationsMap;
    }

    private static Station createStation(Map map, String stationId) {
        return isStationInformation(map) ?
                new Station(stationId, (String) map.getOrDefault("name", "Mordor")) :
                new Station(stationId, "", ((Double) map.getOrDefault("num_docks_available", -1)).intValue(), ((Double) map.getOrDefault("num_bikes_available", -1)).intValue());
    }

    private static boolean isStationInformation(Map map) {
        return map.containsKey("name");
    }
}
