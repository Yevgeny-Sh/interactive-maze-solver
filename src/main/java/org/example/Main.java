package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        boolean[][] maze = {
                {true,  true,  false, false, false},
                {false, true,  false, true,  true },
                {false, true,  true,  true,  false},
                {false, false, false, true,  false},
                {false, false, false, true,  true }
        };

        // Temporary config for development
        RenderConfig config = new RenderConfig(
                "#BD7D39",
                "#3F1664",
                false,
                "#EB4DDB",
                258
        );

        MazeSolver solver = new MazeSolver();
        ApiService apiService = new ApiService();
        MazeDecoder decoder = new MazeDecoder();

        ConfigPanel configPanel =
                new ConfigPanel(config);

        MazePanel mazePanel =
                new MazePanel(maze, config);

        JButton checkSolutionButton =
                new JButton("Check Solution");

        JFrame window =
                new JFrame("Interactive Maze Solver");

        window.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE
        );

        window.setLayout(new BorderLayout());

        window.add(configPanel, BorderLayout.NORTH);
        window.add(mazePanel, BorderLayout.CENTER);
        window.add(checkSolutionButton, BorderLayout.SOUTH);

        // GET MAZE
        configPanel.getGetMazeButton().addActionListener(e -> {

            int width = configPanel.getMazeWidth();
            int height = configPanel.getMazeHeight();

            configPanel.getGetMazeButton().setEnabled(false);

            SwingWorker<boolean[][], Void> worker =
                    new SwingWorker<>() {

                        @Override
                        protected boolean[][] doInBackground() throws Exception {

                            BufferedImage image =
                                    apiService.getMazeImage(width, height);

                            return decoder.decode(
                                    image,
                                    width,
                                    height
                            );
                        }

                        @Override
                        protected void done() {

                            try {
                                boolean[][] newMaze = get();

                                mazePanel.setMaze(newMaze);

                                window.pack();
                                window.setLocationRelativeTo(null);

                            } catch (Exception ex) {

                                JOptionPane.showMessageDialog(
                                        window,
                                        "Failed to load maze"
                                );

                            } finally {

                                configPanel
                                        .getGetMazeButton()
                                        .setEnabled(true);
                            }
                        }
                    };

            worker.execute();
        });

        // CHECK SOLUTION
        checkSolutionButton.addActionListener(e -> {

            List<Point> path =
                    solver.findPath(mazePanel.getMaze());

            if (path.isEmpty()) {

                JOptionPane.showMessageDialog(
                        window,
                        "No solution found"
                );

                return;
            }

            checkSolutionButton.setEnabled(false);

            mazePanel.animatePath(
                    path,
                    config.getAnimationDelayMs(),
                    () -> checkSolutionButton.setEnabled(true)
            );
        });

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}