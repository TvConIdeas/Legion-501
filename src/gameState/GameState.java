package gameState;

/**
 * GameState ==>
 * Estados del juego.
 */

public enum GameState {

    PLAYING, MENU, OPTIONS, RANKING, QUIT, LOGIN, REGISTER;

    public static GameState state = LOGIN; // Estado por default.

}
