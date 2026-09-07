package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MazeDecoder {

    public boolean[][] decode(BufferedImage image, int width, int height) {

        boolean[][] maze = new boolean[height][width];

        int cellWidth = image.getWidth() / width;
        int cellHeight = image.getHeight() / height;

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {

                int pixelX = col * cellWidth + cellWidth / 2;
                int pixelY = row * cellHeight + cellHeight / 2;

                Color pixelColor = new Color(
                        image.getRGB(pixelX, pixelY)
                );

                boolean isWhite =
                        pixelColor.getRed() == 255 &&
                                pixelColor.getGreen() == 255 &&
                                pixelColor.getBlue() == 255;

                maze[row][col] = isWhite;
            }
        }

        return maze;
    }
}