package org.gaming;

import javax.swing.*;

/**
 * This class defines a minesweeper button that holds information about whether it is a mine or not.
 */
public class MineSweeperButton extends JButton {

    /**
     * Boolean flag indicating whether this button represents a mine.
     */
    private boolean isMine;

    /**
     * The number of surrounding mines adjacent to this button.
     */
    private int numberOfSurroundingMines;

    /**
     * Constructor to initialize the MineSweeperButton with mine status and surrounding mine count.
     * @param isMine Whether this button is a mine or not.
     */
    public MineSweeperButton(boolean isMine) {
        this.isMine = isMine;
        this.addActionListener(event -> {
            if (this.isMine) {
                System.out.println("Game over");
            } else {
                this.setEnabled(false);
                this.setText(String.valueOf(this.numberOfSurroundingMines));
                System.out.println("Safe button clicked");
            }
        });
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getNumberOfSurroundingMines() {
        return numberOfSurroundingMines;
    }

    public void setNumberOfSurroundingMines(int numberOfSurroundingMines) {
        this.numberOfSurroundingMines = numberOfSurroundingMines;
    }
}
