package com.pj;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.ResourceBundle;

public class BysykkelStationStatusApp {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelStationStatusApp.class);
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("application");
    private static final String API_CLIENT_IDENTIFIER = RESOURCE_BUNDLE.getString("bysykkel.api.client.identifier");
    private static final String BYSYKKEL_BASE_URL = RESOURCE_BUNDLE.getString("bysykkel.api.url.base.remote");
    private static final String MY_BASE_URL = RESOURCE_BUNDLE.getString("bysykkel.api.url.base.local");
    private static final String STATION_STATUS_ENDPOINT = RESOURCE_BUNDLE.getString("bysykkel.api.url.station.status");
    private static final String STATION_INFO_ENDPOINT = RESOURCE_BUNDLE.getString("bysykkel.api.url.station.info");

    public static void main(String[] args) throws IOException, URISyntaxException {
        LOG.info("Running BysykkelStationStatusApp");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        BysykkelRequestHandler bysykkelRequestHandler = new BysykkelRequestHandler(httpClient, API_CLIENT_IDENTIFIER);
        String stationInfoJson = bysykkelRequestHandler.sendRequestToBysykkel(BYSYKKEL_BASE_URL + STATION_INFO_ENDPOINT);
        String stationStatusJson = bysykkelRequestHandler.sendRequestToBysykkel(BYSYKKEL_BASE_URL + STATION_STATUS_ENDPOINT);
        httpClient.close();

        Map<String, Station> stationInfo = BysykkelJsonParser.parse(stationInfoJson);
        Map<String, Station> stationStatus = BysykkelJsonParser.parse(stationStatusJson);
        Map<String, Station> stations = BysykkelStationMerger.merge(stationInfo, stationStatus);
        LOG.info("Queried {}, got {} results", BYSYKKEL_BASE_URL, stations.size());

        final HttpServer server = BysykkelServer.startServer(MY_BASE_URL, stations);
        LOG.info("WADL is available at {}application.wadl\nHit enter to stop server...", MY_BASE_URL);
        System.in.read();
        server.shutdownNow();

        LOG.info("Have a nice day!");
    }
}
