package org.gaming;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        MineSweeperButton [][] buttons = new MineSweeperButton[10][20];

        int totalButtons = 10 * 20;
        int numberOfMines = totalButtons / 5; // 20% of the buttons are mines

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                double randomNumber = Math.random();
                MineSweeperButton button;
                if (randomNumber < ((double) numberOfMines / (double) totalButtons)) {
                    button = new MineSweeperButton(true);
                    numberOfMines--;
                } else {
                    button = new MineSweeperButton(false);
                }
                button.setBounds(20 + j * 18, 60 + i * 18, 18, 18);
                buttons[i][j] = button;
                jFrame.add(button);
                totalButtons--;
            }
        }

        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction(-1, -1));
        directions.add(new Direction(-1, 0));
        directions.add(new Direction(-1, 1));
        directions.add(new Direction(0, -1));
        directions.add(new Direction(0, 1));
        directions.add(new Direction(1, -1));
        directions.add(new Direction(1, 0));
        directions.add(new Direction(1, 1));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                for (Direction direction : directions) {
                    int newX = i + direction.getX();
                    int newY = j + direction.getY();
                    if (newX >= 0 && newX < 10 && newY >= 0 && newY < 20) {
                        if (buttons[newX][newY].isMine()) {
                            buttons[i][j].setNumberOfSurroundingMines(buttons[i][j].getNumberOfSurroundingMines() + 1);
                        }
                    }
                }
            }
        }

        jFrame.add(resetButton);
        jFrame.setVisible(true);
    }
}