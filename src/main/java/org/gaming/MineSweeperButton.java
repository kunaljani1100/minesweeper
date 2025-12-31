package org.gaming;

import javax.swing.*;
import java.util.List;

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
     * The list of neighboring MineSweeperButtons
     */
    private List<MineSweeperButton> neighbors;

    /**
     * Constructor to initialize the MineSweeperButton with mine status and surrounding mine count.
     * @param isMine Whether this button is a mine or not.
     */
    public MineSweeperButton(boolean isMine) {
        this.isMine = isMine;
        this.addActionListener(event -> {
            if (this.isMine) {
                this.setText("*");
            } else {
                this.setEnabled(false);
                this.setText(String.valueOf(this.numberOfSurroundingMines));
                if (this.numberOfSurroundingMines == 0) {
                    for (MineSweeperButton neighbor : neighbors) {
                        if (neighbor.isEnabled()) {
                            neighbor.doClick();
                        }
                    }
                }
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

    public List<MineSweeperButton> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<MineSweeperButton> neighbors) {
        this.neighbors = neighbors;
    }
}
