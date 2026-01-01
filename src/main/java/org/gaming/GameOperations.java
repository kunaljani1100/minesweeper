package org.gaming;

import javax.swing.*;
import java.util.ArrayList;

public class GameOperations {

    /**
     * Set up a new game by initializing the buttons with mines and non-mines.
     * @param buttons The buttons that are available to the user.
     */
    public static void setNewGame(MineSweeperButton [][] buttons) {
        int totalButtons = Constants.ROWS * Constants.COLS;
        int numberOfMines = totalButtons / Constants.MINE_RATIO;
        Constants.TOTAL_MINES = numberOfMines;

        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
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
                totalButtons--;
            }
        }
    }

    /**
     * Set the number of surrounding mines for each button and add them to the JFrame.
     * @param jFrame The JFrame to which the buttons are added.
     * @param buttons The buttons that are available to the user.
     */
    public static void setSurroundingMines(JFrame jFrame, MineSweeperButton [][] buttons) {
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
    }

    /**
     * Recursively check and reveal neighboring buttons when a mine is clicked.
     * @param button The button that was clicked.
     */
    public static void checkNeighbors(MineSweeperButton button) {
        button.setEnabled(false);
        button.setRevealed(true);
        if (button.isMine()) {
            button.setText("*");
        }
        for (MineSweeperButton neighbor : button.getNeighbors()) {
            if (!neighbor.isRevealed()) {
                checkNeighbors(neighbor);
            }
        }
    }
}
