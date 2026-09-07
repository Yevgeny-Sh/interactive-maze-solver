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

        MazeSolver solver = new MazeSolver();

        MazePanel mazePanel = new MazePanel(maze);

        JButton checkSolutionButton =
                new JButton("Check Solution");

        checkSolutionButton.addActionListener(e -> {

            List<Point> path = solver.findPath(maze);

            if (path.isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "No solution found"
                );

            } else {

                mazePanel.setPath(path);
            }
        });

        JFrame window =
                new JFrame("Interactive Maze Solver");

        window.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE
        );

        window.setLayout(new BorderLayout());

        window.add(mazePanel, BorderLayout.CENTER);
        window.add(checkSolutionButton, BorderLayout.SOUTH);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}