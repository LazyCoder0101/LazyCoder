package com.lazycoder.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighlightedPanel extends JFrame {
    private JPanel activePanel;
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};
    private int colorIndex = 0;

    public HighlightedPanel() {
        setTitle("Dynamic Highlighted Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3));

        JPanel panel1 = createPanel(Color.LIGHT_GRAY);
        JPanel panel2 = createPanel(Color.LIGHT_GRAY);
        JPanel panel3 = createPanel(Color.LIGHT_GRAY);

        // Initially, set the first panel as active
        activePanel = panel1;
        updateActivePanelBorder();

        add(panel1);
        add(panel2);
        add(panel3);

        // Timer to change the border color of the active panel
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorIndex = (colorIndex + 1) % colors.length;
                updateActivePanelBorder();
            }
        });
        timer.start();
    }

    private JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }

    private void updateActivePanelBorder() {
        if (activePanel != null) {
            activePanel.setBorder(BorderFactory.createLineBorder(colors[colorIndex], 3));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HighlightedPanel().setVisible(true);
            }
        });
    }
}