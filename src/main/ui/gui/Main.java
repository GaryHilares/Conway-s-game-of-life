package ui.gui;

// Initializes and runs a new graphic user interface of the Conway's game of life simulator.
public class Main {
    // EFFECTS: Setups and runs a new graphic user interface of Conway's game of life simulator.
    public static void main(String[] args) {
        GameOfLifeGui gui = new GameOfLifeGui();
        gui.run();
    }
}
