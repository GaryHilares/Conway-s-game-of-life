package ui.cli.menu;

// Represents to which menu the user should go next (e.g. a PLAY_MENU means that the user should go to the Play menu).
// Null is used to represent that the menu should not change (yet).
public enum NextMenuSignal {
    MAIN_MENU,
    NEW_GAME_MENU,
    PLAY_MENU,
    EDIT_MENU,
    QUIT
}
