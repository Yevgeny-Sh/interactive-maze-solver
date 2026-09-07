package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {

    private final boolean[][] maze;

    private Color wallColor;
    private Color pathColor;
    private Color gridColor;
    private boolean drawGrid;

    private List<Point> path;
    private int currentPathIndex = -1;

    private final int cellSize = 25;

    public MazePanel(boolean[][] maze, RenderConfig config) {
        this.maze = maze;

        applyRenderConfig(config);

        int width = maze[0].length * cellSize;
        int height = maze.length * cellSize;

        setPreferredSize(new Dimension(width, height));
    }

    public void applyRenderConfig(RenderConfig config) {
        wallColor = Color.decode(config.getWallCellColor());
        pathColor = Color.decode(config.getPathColor());
        gridColor = Color.decode(config.getGridColor());
        drawGrid = config.isDrawGrid();

        repaint();
    }

    public void animatePath(
            List<Point> path,
            int delayMs,
            Runnable onFinished) {

        if (path == null || path.isEmpty()) {
            return;
        }

        this.path = path;
        this.currentPathIndex = -1;

        repaint();

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

        timer.setInitialDelay(0);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawMaze(g);
        drawPath(g);

        if (drawGrid) {
            drawGrid(g);
        }
    }

    private void drawMaze(Graphics g) {

        for (int row = 0; row < maze.length; row++) {

            for (int col = 0; col < maze[row].length; col++) {

                int x = col * cellSize;
                int y = row * cellSize;

                if (maze[row][col]) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(wallColor);
                }

                g.fillRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void drawPath(Graphics g) {

        if (path == null || currentPathIndex < 0) {
            return;
        }

        g.setColor(pathColor);

        for (int i = 0; i <= currentPathIndex; i++) {

            Point point = path.get(i);

            int x = point.x * cellSize;
            int y = point.y * cellSize;

            g.fillRect(x, y, cellSize, cellSize);
        }
    }

    private void drawGrid(Graphics g) {

        g.setColor(gridColor);

        int width = maze[0].length * cellSize;
        int height = maze.length * cellSize;

        for (int row = 0; row <= maze.length; row++) {

            int y = row * cellSize;

            g.drawLine(
                    0,
                    y,
                    width,
                    y
            );
        }

        for (int col = 0; col <= maze[0].length; col++) {

            int x = col * cellSize;

            g.drawLine(
                    x,
                    0,
                    x,
                    height
            );
        }
    }
}