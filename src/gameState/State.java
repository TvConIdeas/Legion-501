package gameState;

import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

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

    // ====================> GET | SET <====================
    public Game getGame() {
        return game;
    }

    // ====================> METODOS <====================
    public boolean isIn(MouseEvent e, MenuButton mb){ // Method auxiliar que verifica si el mouse está dentro de la hitbox
        return mb.getHitbox().contains(e.getX(), e.getY());
    }
}
