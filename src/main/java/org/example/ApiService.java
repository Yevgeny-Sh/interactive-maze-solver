package org.example;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.json.JSONObject;

public class ApiService {

    private static final String CONFIG_URL =
            "https://shaitest-production-3066.up.railway.app/fm1/get-render-config";

    public RenderConfig getRenderConfig() {

        HttpResponse<String> response = Unirest.get(CONFIG_URL)
                .asString();

        JSONObject json = new JSONObject(response.getBody());

        String wallCellColor = json.getString("wallCellColor");
        String pathColor = json.getString("pathColor");
        boolean drawGrid = json.getBoolean("drawGrid");
        String gridColor = json.getString("gridColor");
        int animationDelayMs = json.getInt("animationDelayMs");

        return new RenderConfig(
                wallCellColor,
                pathColor,
                drawGrid,
                gridColor,
                animationDelayMs
        );
    }
}