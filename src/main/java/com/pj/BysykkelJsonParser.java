package com.pj;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
}
