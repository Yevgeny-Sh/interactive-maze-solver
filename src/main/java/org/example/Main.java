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

            System.out.println("Maze loaded from local file");
            System.out.println("Width: " + mazeImage.getWidth());
            System.out.println("Height: " + mazeImage.getHeight());

        } catch (IOException e) {
            System.out.println("Failed to load maze image");
            System.out.println(e.getMessage());
        }
    }
}