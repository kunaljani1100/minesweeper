package org.gaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameOperationsTest {

    private MineSweeperButton[][] buttons;
    private TextField mineMonitor;
    private JFrame jFrame;

    @BeforeEach
    void setUp() {
        buttons = new MineSweeperButton[Constants.ROWS][Constants.COLS];
        mineMonitor = new TextField();
        jFrame = new JFrame();
        Constants.TOTAL_MINES = 0;
        Constants.BOXES_LEFT = Constants.ROWS * Constants.COLS;
    }

    @Test
    @DisplayName("setNewGame should initialize all buttons in the grid")
    void testSetNewGame_InitializesAllButtons() {
        GameOperations.setNewGame(buttons, mineMonitor);

        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                assertNotNull(buttons[i][j], "Button at [" + i + "][" + j + "] should not be null");
            }
        }
    }

    @Test
    @DisplayName("setNewGame should create approximately correct number of mines")
    void testSetNewGame_CreatesCorrectNumberOfMines() {
        GameOperations.setNewGame(buttons, mineMonitor);

        int expectedMines = (Constants.ROWS * Constants.COLS) / Constants.MINE_RATIO;
        // Allow some variance due to random placement, but should be close
        assertTrue(Constants.TOTAL_MINES >= expectedMines - 5 && 
                   Constants.TOTAL_MINES <= expectedMines + 5,
                   "Total mines should be approximately " + expectedMines + 
                   " but was " + Constants.TOTAL_MINES);
    }

    @Test
    @DisplayName("setNewGame should update mineMonitor text field")
    void testSetNewGame_UpdatesMineMonitor() {
        GameOperations.setNewGame(buttons, mineMonitor);

        assertNotNull(mineMonitor.getText(), "Mine monitor text should not be null");
        assertFalse(mineMonitor.getText().isEmpty(), "Mine monitor text should not be empty");
        assertEquals(Integer.toString(Constants.TOTAL_MINES), mineMonitor.getText(),
                     "Mine monitor should display total mines count");
    }

    @Test
    @DisplayName("setNewGame should set button bounds correctly")
    void testSetNewGame_SetsButtonBounds() {
        GameOperations.setNewGame(buttons, mineMonitor);

        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                Rectangle bounds = buttons[i][j].getBounds();
                int expectedX = 20 + j * 18;
                int expectedY = 60 + i * 18;
                assertEquals(expectedX, bounds.x, "Button X position should be " + expectedX);
                assertEquals(expectedY, bounds.y, "Button Y position should be " + expectedY);
                assertEquals(18, bounds.width, "Button width should be 18");
                assertEquals(18, bounds.height, "Button height should be 18");
            }
        }
    }

    @Test
    @DisplayName("setNewGame should reset TOTAL_MINES before counting")
    void testSetNewGame_ResetsTotalMines() {
        Constants.TOTAL_MINES = 100; // Set to a wrong value
        GameOperations.setNewGame(buttons, mineMonitor);

        int expectedMines = (Constants.ROWS * Constants.COLS) / Constants.MINE_RATIO;
        assertTrue(Constants.TOTAL_MINES >= expectedMines - 5 && 
                   Constants.TOTAL_MINES <= expectedMines + 5,
                   "TOTAL_MINES should be reset and recalculated");
    }

    @Test
    @DisplayName("setSurroundingMines should add all buttons to JFrame")
    void testSetSurroundingMines_AddsButtonsToJFrame() {
        // Initialize buttons first
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j] = new MineSweeperButton(false);
            }
        }

        GameOperations.setSurroundingMines(jFrame, buttons);

        // Check that all buttons are added to the frame
        Component[] components = jFrame.getContentPane().getComponents();
        assertEquals(Constants.ROWS * Constants.COLS, components.length,
                     "All buttons should be added to JFrame");
    }

    @Test
    @DisplayName("setSurroundingMines should initialize neighbors list for each button")
    void testSetSurroundingMines_InitializesNeighbors() {
        // Initialize buttons first
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j] = new MineSweeperButton(false);
            }
        }

        GameOperations.setSurroundingMines(jFrame, buttons);

        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                assertNotNull(buttons[i][j].getNeighbors(), 
                             "Neighbors list should be initialized");
            }
        }
    }

    @Test
    @DisplayName("setSurroundingMines should correctly count surrounding mines")
    void testSetSurroundingMines_CountsSurroundingMines() {
        // Initialize all buttons as non-mines first
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j] = new MineSweeperButton(false);
            }
        }
        
        // Place a mine at (1,1)
        buttons[1][1] = new MineSweeperButton(true);

        GameOperations.setSurroundingMines(jFrame, buttons);

        // Button at (0,0) should have 1 neighbor mine (at 1,1)
        assertEquals(1, buttons[0][0].getNumberOfSurroundingMines(),
                     "Button at (0,0) should have 1 surrounding mine");
        
        // Button at (1,0) should have 1 neighbor mine
        assertEquals(1, buttons[1][0].getNumberOfSurroundingMines(),
                     "Button at (1,0) should have 1 surrounding mine");
        
        // Button at (2,2) should have 1 neighbor mine
        assertEquals(1, buttons[2][2].getNumberOfSurroundingMines(),
                     "Button at (2,2) should have 1 surrounding mine");
    }

    @Test
    @DisplayName("setSurroundingMines should handle corner buttons correctly")
    void testSetSurroundingMines_HandlesCornerButtons() {
        // Initialize all buttons
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j] = new MineSweeperButton(false);
            }
        }

        GameOperations.setSurroundingMines(jFrame, buttons);

        // Corner buttons should have fewer neighbors (3 neighbors)
        // Top-left corner
        assertTrue(buttons[0][0].getNeighbors().size() == 3,
                   "Top-left corner should have 3 neighbors");
        
        // Top-right corner
        assertTrue(buttons[0][Constants.COLS - 1].getNeighbors().size() == 3,
                   "Top-right corner should have 3 neighbors");
        
        // Bottom-left corner
        assertTrue(buttons[Constants.ROWS - 1][0].getNeighbors().size() == 3,
                   "Bottom-left corner should have 3 neighbors");
        
        // Bottom-right corner
        assertTrue(buttons[Constants.ROWS - 1][Constants.COLS - 1].getNeighbors().size() == 3,
                   "Bottom-right corner should have 3 neighbors");
    }

    @Test
    @DisplayName("setSurroundingMines should handle edge buttons correctly")
    void testSetSurroundingMines_HandlesEdgeButtons() {
        // Initialize all buttons
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j] = new MineSweeperButton(false);
            }
        }

        GameOperations.setSurroundingMines(jFrame, buttons);

        // Edge buttons (not corners) should have 5 neighbors
        // Top edge (not corner)
        if (Constants.COLS > 2) {
            assertTrue(buttons[0][1].getNeighbors().size() == 5,
                       "Top edge button should have 5 neighbors");
        }
        
        // Left edge (not corner)
        if (Constants.ROWS > 2) {
            assertTrue(buttons[1][0].getNeighbors().size() == 5,
                       "Left edge button should have 5 neighbors");
        }
    }

    @Test
    @DisplayName("setSurroundingMines should handle center buttons correctly")
    void testSetSurroundingMines_HandlesCenterButtons() {
        // Initialize all buttons
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                buttons[i][j] = new MineSweeperButton(false);
            }
        }

        GameOperations.setSurroundingMines(jFrame, buttons);

        // Center buttons should have 8 neighbors
        int centerRow = Constants.ROWS / 2;
        int centerCol = Constants.COLS / 2;
        assertTrue(buttons[centerRow][centerCol].getNeighbors().size() == 8,
                   "Center button should have 8 neighbors");
    }

    @Test
    @DisplayName("checkNeighbors should disable and reveal the clicked button")
    void testCheckNeighbors_DisablesAndRevealsButton() {
        MineSweeperButton button = new MineSweeperButton(false);
        button.setNeighbors(new java.util.ArrayList<>());
        button.setEnabled(true);
        button.setRevealed(false);

        GameOperations.checkNeighbors(button);

        assertFalse(button.isEnabled(), "Button should be disabled");
        assertTrue(button.isRevealed(), "Button should be revealed");
    }

    @Test
    @DisplayName("checkNeighbors should set text to '*' for mine buttons")
    void testCheckNeighbors_SetsMineText() {
        MineSweeperButton mineButton = new MineSweeperButton(true);
        mineButton.setNeighbors(new java.util.ArrayList<>());
        mineButton.setEnabled(true);
        mineButton.setRevealed(false);

        GameOperations.checkNeighbors(mineButton);

        assertEquals("*", mineButton.getText(), "Mine button should display '*'");
    }

    @Test
    @DisplayName("checkNeighbors should recursively reveal all neighbors")
    void testCheckNeighbors_RecursivelyRevealsNeighbors() {
        // Create a chain of buttons
        MineSweeperButton button1 = new MineSweeperButton(false);
        MineSweeperButton button2 = new MineSweeperButton(false);
        MineSweeperButton button3 = new MineSweeperButton(false);

        java.util.ArrayList<MineSweeperButton> neighbors1 = new java.util.ArrayList<>();
        neighbors1.add(button2);
        button1.setNeighbors(neighbors1);

        java.util.ArrayList<MineSweeperButton> neighbors2 = new java.util.ArrayList<>();
        neighbors2.add(button3);
        button2.setNeighbors(neighbors2);

        button3.setNeighbors(new java.util.ArrayList<>());

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button1.setRevealed(false);
        button2.setRevealed(false);
        button3.setRevealed(false);

        GameOperations.checkNeighbors(button1);

        assertTrue(button1.isRevealed(), "Button1 should be revealed");
        assertTrue(button2.isRevealed(), "Button2 should be revealed");
        assertTrue(button3.isRevealed(), "Button3 should be revealed");
        assertFalse(button1.isEnabled(), "Button1 should be disabled");
        assertFalse(button2.isEnabled(), "Button2 should be disabled");
        assertFalse(button3.isEnabled(), "Button3 should be disabled");
    }

    @Test
    @DisplayName("checkNeighbors should not reveal already revealed neighbors")
    void testCheckNeighbors_DoesNotRevealAlreadyRevealedNeighbors() {
        MineSweeperButton button1 = new MineSweeperButton(false);
        MineSweeperButton button2 = new MineSweeperButton(false);

        java.util.ArrayList<MineSweeperButton> neighbors1 = new java.util.ArrayList<>();
        neighbors1.add(button2);
        button1.setNeighbors(neighbors1);
        button2.setNeighbors(new java.util.ArrayList<>());

        button1.setEnabled(true);
        button2.setEnabled(true);
        button1.setRevealed(false);
        button2.setRevealed(true); // Already revealed

        GameOperations.checkNeighbors(button1);

        assertTrue(button1.isRevealed(), "Button1 should be revealed");
        assertTrue(button2.isRevealed(), "Button2 should remain revealed");
        // Button2 should not be disabled again (it was already revealed)
    }

    @Test
    @DisplayName("checkNeighbors should handle buttons with no neighbors")
    void testCheckNeighbors_HandlesNoNeighbors() {
        MineSweeperButton button = new MineSweeperButton(false);
        button.setNeighbors(new java.util.ArrayList<>());
        button.setEnabled(true);
        button.setRevealed(false);

        GameOperations.checkNeighbors(button);

        assertFalse(button.isEnabled(), "Button should be disabled");
        assertTrue(button.isRevealed(), "Button should be revealed");
    }

    @Test
    @DisplayName("checkNeighbors should handle circular neighbor references")
    void testCheckNeighbors_HandlesCircularReferences() {
        MineSweeperButton button1 = new MineSweeperButton(false);
        MineSweeperButton button2 = new MineSweeperButton(false);

        java.util.ArrayList<MineSweeperButton> neighbors1 = new java.util.ArrayList<>();
        neighbors1.add(button2);
        button1.setNeighbors(neighbors1);

        java.util.ArrayList<MineSweeperButton> neighbors2 = new java.util.ArrayList<>();
        neighbors2.add(button1);
        button2.setNeighbors(neighbors2);

        button1.setEnabled(true);
        button2.setEnabled(true);
        button1.setRevealed(false);
        button2.setRevealed(false);

        GameOperations.checkNeighbors(button1);

        assertTrue(button1.isRevealed(), "Button1 should be revealed");
        assertTrue(button2.isRevealed(), "Button2 should be revealed");
        // Should not cause infinite recursion due to isRevealed check
    }
}
