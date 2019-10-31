package com.pj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class BysykkelStationStatusApp {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelStationStatusApp.class);
    private static final String API_CLIENT_IDENTIFIER = ResourceBundle.getBundle("application").getString("bysykkel.api.client.identifier");
    private static final String BASE_URL = ResourceBundle.getBundle("application").getString("bysykkel.api.url.base");

    public static void main(String[] args) {
        LOG.info("Running BysykkelStationStatusApp");
    }
}
