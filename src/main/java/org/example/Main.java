package org.example;

public class Main {

    public static void main(String[] args) {

        ApiService apiService = new ApiService();

        RenderConfig config = apiService.getRenderConfig();

        System.out.println("Wall color: " + config.getWallCellColor());
        System.out.println("Path color: " + config.getPathColor());
        System.out.println("Draw grid: " + config.isDrawGrid());
        System.out.println("Grid color: " + config.getGridColor());
        System.out.println("Animation delay: " + config.getAnimationDelayMs());
    }
}