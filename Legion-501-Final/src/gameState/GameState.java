package gameState;

/**
 * GameState ==>
 * Estados del juego.
 */

public enum GameState {

    LOGIN, REGISTER, MENU, PLAYING, RANKING, OPTIONS, QUIT;

    public static GameState state = LOGIN; // Estado por default.

}
