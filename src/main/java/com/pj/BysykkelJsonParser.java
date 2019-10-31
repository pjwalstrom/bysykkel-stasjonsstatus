package com.pj;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BysykkelJsonParser {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelJsonParser.class);

    static List<Map> parse(String json) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            Map map = new Gson().fromJson(json, Map.class);
            Map data = (Map) map.get("data");
            return (List)data.get("stations");
        } catch (JsonSyntaxException e) {
            LOG.warn("Could not parse this string as json: " + json);
            return Collections.emptyList();
        }
    }
}
