package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {

    private final boolean[][] maze;

    private List<Point> path;
    private int currentPathIndex = -1;

    private final int cellSize = 25;
    private final Color pathColor;

    public MazePanel(boolean[][] maze, Color pathColor) {
        this.maze = maze;
        this.pathColor = pathColor;

        int width = maze[0].length * cellSize;
        int height = maze.length * cellSize;

        setPreferredSize(new Dimension(width, height));
    }

    public void animatePath(
            List<Point> path,
            int delayMs,
            Runnable onFinished) {

        this.path = path;
        this.currentPathIndex = -1;

        Timer timer = new Timer(delayMs, null);

        timer.addActionListener(e -> {

            currentPathIndex++;
            repaint();

            if (currentPathIndex >= path.size() - 1) {
                timer.stop();

                if (onFinished != null) {
                    onFinished.run();
                }
            }
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ציור המבוך
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

        // ציור החלק שכבר הוצג מהפתרון
        if (path != null) {

            g.setColor(pathColor);

            for (int i = 0; i <= currentPathIndex; i++) {

                Point point = path.get(i);

                int x = point.x * cellSize;
                int y = point.y * cellSize;

                g.fillRect(x, y, cellSize, cellSize);
            }
        }
    }
}