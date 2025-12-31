package org.gaming;

import java.util.List;

public class Constants {

    public static final int ROWS = 10;
    public static final int COLS = 20;
    public static final int MINE_RATIO = 10;
    public static final List<Direction> ADJACENT_OFFSETS = List.of(
            new Direction(-1, -1), new Direction(-1, 0), new Direction(-1, 1),
            new Direction( 0, -1),                       new Direction( 0, 1),
            new Direction( 1, -1), new Direction( 1, 0), new Direction( 1, 1)
    );
}
