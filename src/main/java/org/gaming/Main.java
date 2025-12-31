package org.gaming;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Minesweeper");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.setSize(400, 400);

        ImageIcon icon = new ImageIcon("smiley.png");
        Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        JButton resetButton = new JButton(new ImageIcon(scaledImage));
        resetButton.setBounds(200, 20, 30, 30);
        resetButton.setBorder(BorderFactory.createCompoundBorder());
        resetButton.setContentAreaFilled(false);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                JButton button = new JButton();
                button.setBounds(20 + j * 18, 60 + i * 18, 18, 18);
                jFrame.add(button);
            }
        }

        jFrame.add(resetButton);
        jFrame.setVisible(true);
    }
}