package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        ApiService apiService = new ApiService();

        try {
            BufferedImage mazeImage = apiService.getMazeImage(20, 20);

            System.out.println("Maze downloaded successfully");
            System.out.println("Width: " + mazeImage.getWidth());
            System.out.println("Height: " + mazeImage.getHeight());

            File directory = new File("dev-data");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File outputFile = new File(directory, "maze-20x20.png");

            if (outputFile.exists()) {
                outputFile.delete();
            }

            boolean saved = ImageIO.write(
                    mazeImage,
                    "png",
                    outputFile
            );

            System.out.println("Image saved: " + saved);
            System.out.println("File path: " + outputFile.getAbsolutePath());
            System.out.println("File size: " + outputFile.length() + " bytes");

            BufferedImage testImage = ImageIO.read(outputFile);

            if (testImage != null) {
                System.out.println("Saved PNG is valid");
                System.out.println(
                        "Saved image size: "
                                + testImage.getWidth()
                                + "x"
                                + testImage.getHeight()
                );
            } else {
                System.out.println("Saved file is NOT a valid image");
            }

        } catch (IOException e) {
            System.out.println("Failed");
            System.out.println(e.getMessage());
        }
    }
}