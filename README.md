# Minesweeper (Swing)

Simple Minesweeper clone written in Java using Swing and Gradle.

## Summary
A small desktop Minesweeper game. Key features:
- Grid of clickable `MineSweeperButton` cells
- Random mine placement
- Smiley reset button (`smiley.png`) that rebuilds the board
- Uses Swing on the EDT

## Requirements
- Java 11+ (JDK)
- Gradle (wrapper included)

## Build & run
Run with the Gradle wrapper:

```bash
./gradlew run
```

Or build a jar and run:

```bash
./gradlew build
java -jar build/libs/<project-name>-all.jar
```

Replace `<project-name>` with the produced artifact name.

## Important files
- `src/main/java/org/gaming/Main.java` — application entry, UI setup and board population
- `src/main/java/org/gaming/Direction.java` — small value class for neighbor offsets (ensure it exposes `getX()` and `getY()`)
- `src/main/java/org/gaming/MineSweeperButton.java` — custom button representing a cell
- `src/main/java/org/gaming/GameOperations.java` — helper to initialize/reset the board (`setNewGame(...)`)
- `src/main/java/org/gaming/Constants.java` — grid size, mine ratio, and `ADJACENT_OFFSETS`
- `src/main/resources/smiley.png` — reset button icon

## Usage / Controls
- Left-click a cell to reveal it.
- Use the smiley reset button to start a new randomly generated board (the reset action calls `GameOperations.setNewGame(buttons)` and rebuilds neighbors on the EDT).

## Notes & troubleshooting
- If you get a compilation error at code that calls `direction.getX()` / `direction.getY()`, add those getters to `Direction`.
- If neighbor counts are always `1`, ensure you initialize a cell's neighbors once per cell before iterating `Constants.ADJACENT_OFFSETS` (don’t reset the neighbors list inside the offsets loop).
- The UI currently uses absolute positioning (`null` layout and `setBounds`). Consider switching to a layout manager (or scale icons) so `pack()` can size the frame correctly.

## License
No license specified (add a `LICENSE` file if needed).
