package com.pj;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
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

    String sendRequestToBysykkel(String url) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(url);
        HttpGet httpGet = new HttpGet(builder.build());
        httpGet.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        httpGet.setHeader("Client-Identifier", apiClientIdentifier);
        HttpResponse response = httpClient.execute(httpGet);
        String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        LOG.debug(json);
        return json;
    }
}
