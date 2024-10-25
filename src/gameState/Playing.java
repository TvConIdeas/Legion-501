package gameState;

import entities.Player;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Playing ==>
 * Clase que controla todo el funcionamiento del juego.
 */

public class Playing extends State {

    // ====================> ATRIBUTOS <====================
    private Player player;

    // ====================> CONSTRUCTOR <====================
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    // ====================> GETTER <====================
    public Player getPlayer() {
        return player;
    }

    // ====================> METODOS <====================

    private void initClasses(){
        player = new Player(game.GAME_WIDTH/2 - game.TILES_SIZE / 2,
                game.GAME_HEIGHT - game.TILES_SIZE * 2, game.TILES_SIZE, game.TILES_SIZE);
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }


    /// Interface StateMethods
    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        player.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                GameState.state = GameState.MENU;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
        }
    }
}
