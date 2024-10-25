package gameState;

import main.Game;

/**
 * State ==>
 * Clase padre abstracta de los estados del juego.
 */

public abstract class State implements Statemethods{

    // ====================> ATRIBUTOS <====================
    protected Game game;

    // ====================> CONSTRUCTOR <====================
    public State(Game game) {
        this.game = game;
    }

    // ====================> GETTER <====================
    public Game getGame() {
        return game;
    }
}
