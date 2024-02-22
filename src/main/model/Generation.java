package model;

import org.json.JSONObject;

import persistence.JsonSerializable;

// Represents a generation (i.e. a board state) within Conway's Game of Life.
// It manages the information about what cells are alive and what cells are dead.
public class Generation /*implements JsonSerializable*/ {
    private final int height;
    private final int width;
    private final boolean[][] board;

    // REQUIRES: width > 0, height > 0, board is width x height array
    // MODIFIES: board
    // EFFECTS: Builds a generation with the given board.
    private Generation(int width, int height, boolean[][] board) {
        this.width = width;
        this.height = height;
        this.board = board;
    }

    // REQUIRES: width > 0, height > 0
    // EFFECTS: Builds a generation with the given arbitrary dimensions.
    public Generation(int width, int height) {
        this.width = width;
        this.height = height;
        board = new boolean[this.width][this.height];
    }

    // REQUIRES: 0 <= x < width, 0 <= y < height
    // MODIFIES: this
    // EFFECTS: Toggles the cell at (x, y) (kills it if it is alive, revives it otherwise).
    public void toggle(int x, int y) {
        board[x][y] = !board[x][y];
    }

    // EFFECTS: Returns the width of the board.
    public int getWidth() {
        return width;
    }

    // EFFECTS: Returns the height of the board.
    public int getHeight() {
        return height;
    }

    // EFFECTS: Returns a string representation of the board.
    public String toString() {
        StringBuilder boardRep = new StringBuilder();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                boardRep.append(board[x][y] ? "O" : ".");
            }
            boardRep.append("\n");
        }
        return boardRep.toString();
    }

    // EFFECTS: Computes and returns the next generation.
    public Generation nextGeneration() {
        boolean[][] newBoard = new boolean[this.width][this.height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                int neighbors = getNeighbors(x, y);
                if (neighbors == 2) {
                    newBoard[x][y] = board[x][y];
                } else {
                    newBoard[x][y] = neighbors == 3;
                }
            }
        }
        return new Generation(this.width, this.height, newBoard);
    }

    // EFFECTS: Computes the number of alive neighbors.
    //          Assumes that cells out of board are death.
    private int getNeighbors(int x, int y) {
        int aliveNeighbors = 0;
        for (int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
            for (int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
                if (!(neighborX == x && neighborY == y)
                        && safelyGet(neighborX, neighborY)) {
                    aliveNeighbors++;
                }
            }
        }
        return aliveNeighbors;
    }

    // EFFECTS: Returns cell at (x, y), or false if (x, y) is out of bonds.
    private boolean safelyGet(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return false;
        }
        return board[x][y];
    }

    /*@Override
    public JSONObject toJson() {
        JSONObject generationJson = new JSONObject();
        generationJson.put("width", width);
        generationJson.put("height", height);
        generationJson.put("board", toString());
        return generationJson;
    }*/
}
