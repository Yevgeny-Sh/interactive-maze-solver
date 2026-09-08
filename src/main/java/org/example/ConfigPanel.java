package org.example;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {

    private final JLabel wallColorLabel;
    private final JLabel pathColorLabel;
    private final JLabel drawGridLabel;
    private final JLabel gridColorLabel;
    private final JLabel animationDelayLabel;

    private final JTextField widthField;
    private final JTextField heightField;

    private final JButton refreshButton;
    private final JButton getMazeButton;

    public ConfigPanel(RenderConfig config) {

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        wallColorLabel = new JLabel();
        pathColorLabel = new JLabel();
        drawGridLabel = new JLabel();
        gridColorLabel = new JLabel();
        animationDelayLabel = new JLabel();

        widthField = new JTextField("30", 10);
        heightField = new JTextField("30", 10);

        refreshButton = new JButton("Refresh Config");
        getMazeButton = new JButton("GET MAZE");

        updateConfig(config);

        int row = 0;

        addRow("Wall color:", wallColorLabel, row++, gbc);
        addRow("Path color:", pathColorLabel, row++, gbc);
        addRow("Draw grid:", drawGridLabel, row++, gbc);
        addRow("Grid color:", gridColorLabel, row++, gbc);
        addRow("Animation delay:", animationDelayLabel, row++, gbc);

        addRow("Width:", widthField, row++, gbc);
        addRow("Height:", heightField, row++, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        add(refreshButton, gbc);

        gbc.gridx = 1;
        add(getMazeButton, gbc);
    }

    private void addRow(
            String text,
            Component component,
            int row,
            GridBagConstraints gbc) {

        gbc.gridx = 0;
        gbc.gridy = row;

        add(new JLabel(text), gbc);

        gbc.gridx = 1;

        add(component, gbc);
    }

    public void updateConfig(RenderConfig config) {

        wallColorLabel.setText(config.getWallCellColor());
        pathColorLabel.setText(config.getPathColor());
        drawGridLabel.setText(String.valueOf(config.isDrawGrid()));
        gridColorLabel.setText(config.getGridColor());
        animationDelayLabel.setText(
                config.getAnimationDelayMs() + " ms"
        );
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getGetMazeButton() {
        return getMazeButton;
    }

    public int getMazeWidth() {
        int width = getValidSize(widthField.getText());
        widthField.setText(String.valueOf(width));
        return width;
    }

    public int getMazeHeight() {
        int height = getValidSize(heightField.getText());
        heightField.setText(String.valueOf(height));
        return height;
    }

    private int getValidSize(String text) {

        try {
            int value = Integer.parseInt(text.trim());
            if (value >= 5 && value <= 100) {
                return value;
            }

        } catch (NumberFormatException e) {
            // Invalid input will use the default value
        }

        return 30;
    }
}