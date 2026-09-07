package org.example;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            BufferedImage mazeImage =
                    ImageIO.read(new File("dev-data/maze-20x20.png"));

            if (mazeImage == null) {
                System.out.println("Failed to load maze image");
                return;
            }

            boolean[][] testMaze = {
                    {true,  true,  false, false, false},
                    {false, true,  false, true,  true },
                    {false, true,  true,  true,  false},
                    {false, false, false, true,  false},
                    {false, false, false, true,  true }
            };

            MazeSolver solver = new MazeSolver();

            List<Point> path = solver.findPath(testMaze);

            if (path.isEmpty()) {
                System.out.println("No solution found");
            } else {
                System.out.println("Solution found");
                System.out.println("Path length: " + path.size());

                for (Point point : path) {
                    System.out.println(point);
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to load maze image");
            System.out.println(e.getMessage());
        }
    }
}