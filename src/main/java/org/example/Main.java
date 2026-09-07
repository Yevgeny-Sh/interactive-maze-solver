package org.example;

import javax.swing.*;
import java.awt.*;
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

        ConfigPanel configPanel =
                new ConfigPanel(config);

        MazePanel mazePanel =
                new MazePanel(maze, config);

        JButton checkSolutionButton =
                new JButton("Check Solution");

        checkSolutionButton.addActionListener(e -> {

            List<Point> path = solver.findPath(maze);

            if (path.isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
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

        JFrame window =
                new JFrame("Interactive Maze Solver");

        window.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE
        );

        window.setLayout(new BorderLayout());

        window.add(configPanel, BorderLayout.NORTH);
        window.add(mazePanel, BorderLayout.CENTER);
        window.add(checkSolutionButton, BorderLayout.SOUTH);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}