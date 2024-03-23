package ui.cli;

import model.GameOfLife;
import ui.cli.menu.*;

import java.util.Scanner;

// Command line interface that allows user to interact and play with the Conway's game of life simulator.
// Displays a list of cells (i.e. allows viewing a list of items) in edit and play modes.
public class GameOfLifeCli {
    // EFFECTS: Runs the main program loop, redirecting the user to the correct menu depending on the program state.
    public void run() {
        Scanner scanner = new Scanner(System.in);
        GameOfLife game = null;
        NextMenuSignal signal = NextMenuSignal.MAIN_MENU;
        while (signal != NextMenuSignal.QUIT) {
            assert signal != null;
            if (signal == NextMenuSignal.MAIN_MENU) {
                MainMenu menu = new MainMenu(scanner);
                signal = menu.run();
                if (menu.getGame() != null) {
                    game = menu.getGame();
                }
            } else if (signal == NextMenuSignal.NEW_GAME_MENU) {
                NewGameMenu menu = new NewGameMenu(scanner);
                signal = menu.run();
                game = menu.getGame();
            } else if (signal == NextMenuSignal.EDIT_MENU) {
                assert game != null;
                signal = new EditMenu(game, scanner).run();
            } else if (signal == NextMenuSignal.PLAY_MENU) {
                assert game != null;
                signal = new PlayMenu(game, scanner).run();
            }
        }
    }
}
