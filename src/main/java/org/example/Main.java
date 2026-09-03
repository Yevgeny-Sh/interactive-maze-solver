package org.example;

public class Main {

    public static void main(String[] args) {

        // Temporary config for development to avoid unnecessary API requests
        RenderConfig config = new RenderConfig(
                "#BD7D39",
                "#3F1664",
                false,
                "#EB4DDB",
                258
        );

        System.out.println("Wall color: " + config.getWallCellColor());
        System.out.println("Path color: " + config.getPathColor());
        System.out.println("Draw grid: " + config.isDrawGrid());
        System.out.println("Grid color: " + config.getGridColor());
        System.out.println("Animation delay: " + config.getAnimationDelayMs());
    }
}