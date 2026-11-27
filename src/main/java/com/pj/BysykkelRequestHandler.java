package com.pj;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

class BysykkelRequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(BysykkelRequestHandler.class);
    private HttpClient httpClient;
    private String apiClientIdentifier;

    BysykkelRequestHandler(HttpClient httpClient, String apiClientIdentifier) {
        this.httpClient = httpClient;
        this.apiClientIdentifier = apiClientIdentifier;
    }

    String sendRequestToBysykkel(String url) throws URISyntaxException, IOException, ParseException {
        URIBuilder builder = new URIBuilder(url);
        HttpGet httpGet = new HttpGet(builder.build());
        httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpGet.setHeader("Client-Identifier", apiClientIdentifier);
        ClassicHttpResponse response = (ClassicHttpResponse) httpClient.execute(httpGet);
        String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        LOG.debug(json);
        return json;
    }
}
