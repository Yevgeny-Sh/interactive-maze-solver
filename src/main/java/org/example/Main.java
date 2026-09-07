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

                printMazeWithPath(maze, path);
            }

        } catch (IOException e) {
            System.out.println("Failed to load maze image");
            System.out.println(e.getMessage());
        }
    }

    private static void printMazeWithPath(
            boolean[][] maze,
            List<Point> path) {

        boolean[][] pathCells =
                new boolean[maze.length][maze[0].length];

        for (Point point : path) {
            pathCells[point.y][point.x] = true;
        }

        for (int row = 0; row < maze.length; row++) {

            for (int col = 0; col < maze[row].length; col++) {

                if (pathCells[row][col]) {
                    System.out.print("*");
                } else if (maze[row][col]) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }

            System.out.println();
        }
    }
}