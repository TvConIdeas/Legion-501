package gameState;

import main.Game;

public class State {

    // Atributos
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
