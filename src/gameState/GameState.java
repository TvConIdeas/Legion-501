package gameState;

/**
 * GameState ==>
 * Estados del juego.
 */

public enum GameState {

    PLAYING, MENU, OPTIONS, RANKING, QUIT, REGISTER, LOGIN;

    public static GameState state = LOGIN; // Estado por default.

}
