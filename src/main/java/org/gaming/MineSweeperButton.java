package org.gaming;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
     * Check if the button has been revealed.
     */
    private boolean isRevealed;

    /**
     * Constructor to initialize the MineSweeperButton with mine status and surrounding mine count.
     * @param isMine Whether this button is a mine or not.
     */
    public MineSweeperButton(boolean isMine) {
        this.isMine = isMine;
        this.isRevealed = false;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && !isRevealed) {
                    setText(getText().equals("F") ? "" : "F");
                }
            }
        });
        this.addActionListener(event -> {
            if (this.isMine) {
                this.setText("*");
                GameOperations.checkNeighbors(this);
            } else {
                Constants.BOXES_LEFT--;
                if (Constants.TOTAL_MINES == Constants.BOXES_LEFT) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You've won the game!");
                    System.exit(0);
                }
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

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}
