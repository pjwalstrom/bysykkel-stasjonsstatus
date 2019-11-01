package com.pj;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

public class BysykkelStationStatusApp {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelStationStatusApp.class);
    private static final String API_CLIENT_IDENTIFIER = ResourceBundle.getBundle("application").getString("bysykkel.api.client.identifier");
    private static final String BASE_URL = ResourceBundle.getBundle("application").getString("bysykkel.api.url.base");
    private static final String STATION_STATUS_ENDPOINT = ResourceBundle.getBundle("application").getString("bysykkel.api.url.station.status");
    private static final String STATION_INFO_ENDPOINT = ResourceBundle.getBundle("application").getString("bysykkel.api.url.station.info");

    public static void main(String[] args) throws IOException, URISyntaxException {
        LOG.info("Running BysykkelStationStatusApp");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        BysykkelRequestHandler bysykkelRequestHandler = new BysykkelRequestHandler(httpClient, API_CLIENT_IDENTIFIER);
        String stationInfoJson = bysykkelRequestHandler.sendRequestToBysykkel(BASE_URL + STATION_INFO_ENDPOINT);
        String stationStatusJson = bysykkelRequestHandler.sendRequestToBysykkel(BASE_URL + STATION_STATUS_ENDPOINT);
        httpClient.close();

        Map<String, Station> stationInfo = BysykkelJsonParser.parse(stationInfoJson);
        Map<String, Station> stationStatus = BysykkelJsonParser.parse(stationStatusJson);

        Collection<Station> stations = BysykkelStationMerger.merge(stationInfo, stationStatus);

        for (Station station : stations) {
            LOG.info(station.toString());
        }
        LOG.info("Have a nice day!");
    }
}
