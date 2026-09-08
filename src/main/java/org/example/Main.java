package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Main {

    private static RenderConfig currentConfig;

    public static void main(String[] args) {

        MazeSolver solver = new MazeSolver();
        ApiService apiService = new ApiService();
        MazeDecoder decoder = new MazeDecoder();

        try {
            currentConfig = apiService.getRenderConfig();
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load render config"
            );

            return;
        }

        ConfigPanel configPanel =
                new ConfigPanel(currentConfig);

        MazePanel mazePanel =
                new MazePanel(currentConfig);

        JButton checkSolutionButton =
                new JButton("Check Solution");

        checkSolutionButton.setEnabled(false);

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
            checkSolutionButton.setEnabled(false);

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

                                checkSolutionButton.setEnabled(
                                        mazePanel.getMaze() != null
                                );
                            }
                        }
                    };

            worker.execute();
        });

        // REFRESH CONFIG
        configPanel.getRefreshButton().addActionListener(e -> {

            configPanel.getRefreshButton().setEnabled(false);

            SwingWorker<RenderConfig, Void> worker =
                    new SwingWorker<>() {

                        @Override
                        protected RenderConfig doInBackground() throws Exception {

                            return apiService.getRenderConfig();
                        }

                        @Override
                        protected void done() {

                            try {
                                currentConfig = get();

                                configPanel.updateConfig(currentConfig);
                                mazePanel.applyRenderConfig(currentConfig);

                            } catch (Exception ex) {

                                JOptionPane.showMessageDialog(
                                        window,
                                        "Failed to refresh config"
                                );

                            } finally {

                                configPanel
                                        .getRefreshButton()
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
            configPanel.getGetMazeButton().setEnabled(false);

            mazePanel.animatePath(
                    path,
                    currentConfig.getAnimationDelayMs(),
                    () -> {
                        checkSolutionButton.setEnabled(true);
                        configPanel.getGetMazeButton().setEnabled(true);
                    }
            );
        });

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}