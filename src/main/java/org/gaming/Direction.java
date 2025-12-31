package org.gaming;

/**
 * This class represents a direction in a 2D space with x and y coordinates.
 */
public class Direction {
    private int x;
    private int y;

    /**
     * Constructor to initialize the Direction with x and y coordinates.
     * @param x The distance in the horizontal direction.
     * @param y The distance in the vertical direction.
     */
    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
