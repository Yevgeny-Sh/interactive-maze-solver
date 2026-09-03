package org.example;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class ApiService {

    private static final String CONFIG_URL =
            "https://shaitest-production-3066.up.railway.app/fm1/get-render-config";

    private static final String MAZE_URL =
            "https://shaitest-production-3066.up.railway.app/fm1/get-maze-image";

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

    public BufferedImage getMazeImage(int width, int height) throws IOException {

        HttpResponse<byte[]> response = Unirest.get(MAZE_URL)
                .queryString("width", width)
                .queryString("height", height)
                .asBytes();

        System.out.println("HTTP status: " + response.getStatus());

        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            throw new IOException(
                    "Failed to get maze. HTTP status: " + response.getStatus()
            );
        }

        byte[] imageBytes = response.getBody();

        if (imageBytes == null || imageBytes.length == 0) {
            throw new IOException("Server returned an empty response");
        }

        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(imageBytes)
        );

        if (image == null) {
            throw new IOException("Server response is not a valid image");
        }

        return image;
    }
}