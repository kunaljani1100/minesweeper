package org.gaming;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This class defines a minesweeper button that holds information about whether it is a mine or not.
 */
@Getter
@Setter
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
                System.out.println("Safe button clicked");
            }
        });
    }
}
