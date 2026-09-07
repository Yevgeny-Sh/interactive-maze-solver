package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            BufferedImage mazeImage =
                    ImageIO.read(new File("dev-data/maze-20x20.png"));

            if (mazeImage == null) {
                System.out.println("Failed to load maze image");
                return;
            }

            MazeDecoder decoder = new MazeDecoder();

            boolean[][] maze = decoder.decode(
                    mazeImage,
                    20,
                    20
            );

            for (int row = 0; row < maze.length; row++) {

                for (int col = 0; col < maze[row].length; col++) {

                    if (maze[row][col]) {
                        System.out.print(".");
                    } else {
                        System.out.print("#");
                    }
                }

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Failed to load maze image");
            System.out.println(e.getMessage());
        }
    }
}