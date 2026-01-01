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

        MineSweeperButton [][] buttons = new MineSweeperButton[Constants.ROWS][Constants.COLS];
        TextField mineMonitor = new TextField();
        mineMonitor.setEditable(false);
        mineMonitor.setBounds(30, 20, 100, 30);
        GameOperations.setNewGame(buttons, mineMonitor);
        GameOperations.setSurroundingMines(jFrame, buttons);

        resetButton.addActionListener(event -> {
            jFrame.dispose();
            Main.main(null);
        });
        jFrame.add(mineMonitor);
        jFrame.add(resetButton);
        jFrame.setVisible(true);
    }
}