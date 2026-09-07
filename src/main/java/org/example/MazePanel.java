package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {

    private final boolean[][] maze;
    private List<Point> path;

    private final int cellSize = 25;

    public MazePanel(boolean[][] maze) {
        this.maze = maze;

        int width = maze[0].length * cellSize;
        int height = maze.length * cellSize;

        setPreferredSize(new Dimension(width, height));
    }

    public void setPath(List<Point> path) {
        this.path = path;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < maze.length; row++) {

            for (int col = 0; col < maze[row].length; col++) {

                int x = col * cellSize;
                int y = row * cellSize;

                if (maze[row][col]) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }

                g.fillRect(x, y, cellSize, cellSize);
            }
        }

        if (path != null) {




            g.setColor(Color.ORANGE);

            for (Point point : path) {

                int x = point.x * cellSize;
                int y = point.y * cellSize;

                g.fillRect(x, y, cellSize, cellSize);
            }
        }
    }
}