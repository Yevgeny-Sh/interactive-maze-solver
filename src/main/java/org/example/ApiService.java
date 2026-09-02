package org.example;


import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;


public class ApiService {

    private static final String CONFIG_URL =
            "https://shaitest-production-3066.up.railway.app/fm1/get-render-config";

    public String getRenderConfig() {

        HttpResponse<String> response = Unirest.get(CONFIG_URL)
                .asString();

        return response.getBody();
    }
}