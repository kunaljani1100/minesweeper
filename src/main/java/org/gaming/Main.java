package org.gaming;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        GameOperations.setNewGame(buttons);

        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j].setNeighbors(new ArrayList<>());
                for (Direction direction : Constants.ADJACENT_OFFSETS) {
                    int newX = i + direction.getX();
                    int newY = j + direction.getY();
                    if (newX >= 0 && newX < Constants.ROWS && newY >= 0 && newY < Constants.COLS) {
                        buttons[i][j].getNeighbors().add(buttons[newX][newY]);
                        if (buttons[newX][newY].isMine()) {
                            buttons[i][j].setNumberOfSurroundingMines(buttons[i][j].getNumberOfSurroundingMines() + 1);
                        }
                    }
                }
                jFrame.add(buttons[i][j]);
            }
        }

        jFrame.add(resetButton);
        jFrame.setVisible(true);
    }
}