package ui;

// Initializes and runs a new command-line interface of the Conway's game of life simulator.
public class Main {
    // EFFECTS: Setups and runs a new command-line interface of Conway's game of life simulator.
    public static void main(String[] args) {
        GameOfLifeCli cli = new GameOfLifeCli();
        cli.setup();
        cli.run();
    }
}
