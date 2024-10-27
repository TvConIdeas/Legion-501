package gameState;

/**
 * GameState ==>
 * Estados del juego.
 */

public enum GameState {

    PLAYING, MENU, OPTIONS, QUIT;

    public static GameState state = MENU; // Estado por default.

}
