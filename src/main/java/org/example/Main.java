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

            MazeDecoder decoder = new MazeDecoder();

            boolean[][] maze = decoder.decode(
                    mazeImage,
                    20,
                    20
            );

            MazeSolver solver = new MazeSolver();

            List<Point> path = solver.findPath(maze);

            if (path.isEmpty()) {
                System.out.println("No solution found");
            } else {
                System.out.println("Solution found");
                System.out.println("Path length: " + path.size());
                System.out.println("Start: " + path.get(0));
                System.out.println("End: " + path.get(path.size() - 1));
            }

        } catch (IOException e) {
            System.out.println("Failed to load maze image");
            System.out.println(e.getMessage());
        }
    }
}