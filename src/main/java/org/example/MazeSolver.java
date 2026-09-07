package org.example;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class MazeSolver {

    public List<Point> findPath(boolean[][] maze) {

        int height = maze.length;
        int width = maze[0].length;

        if (!maze[0][0] || !maze[height - 1][width - 1]) {
            return Collections.emptyList();
        }

        boolean[][] visited = new boolean[height][width];
        Point[][] previous = new Point[height][width];

        Queue<Point> queue = new ArrayDeque<>();

        Point start = new Point(0, 0);
        Point end = new Point(width - 1, height - 1);

        queue.add(start);
        visited[0][0] = true;

        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        while (!queue.isEmpty()) {

            Point current = queue.remove();

            if (current.equals(end)) {
                return buildPath(previous, end);
            }

            for (int[] direction : directions) {

                int nextX = current.x + direction[0];
                int nextY = current.y + direction[1];

                if (nextX >= 0 && nextX < width &&
                        nextY >= 0 && nextY < height &&
                        maze[nextY][nextX] &&
                        !visited[nextY][nextX]) {

                    visited[nextY][nextX] = true;

                    previous[nextY][nextX] = current;

                    queue.add(new Point(nextX, nextY));
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Point> buildPath(Point[][] previous, Point end) {

        List<Point> path = new ArrayList<>();

        Point current = end;

        while (current != null) {
            path.add(current);
            current = previous[current.y][current.x];
        }

        Collections.reverse(path);

        return path;
    }
}