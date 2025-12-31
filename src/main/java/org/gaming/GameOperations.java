package org.gaming;

public class GameOperations {

    /**
     * Set up a new game by initializing the buttons with mines and non-mines.
     * @param buttons The buttons that are available to the user.
     */
    public static void setNewGame(MineSweeperButton [][] buttons) {
        int totalButtons = Constants.ROWS * Constants.COLS;
        int numberOfMines = totalButtons / Constants.MINE_RATIO;

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
}
