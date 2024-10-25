package gameState;

import entities.Player;
import main.Game;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods {

    // Atributos
    private Player player;

    // Constructor
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses(){
        player = new Player(200, 200, (int) (game.TILES_DEFAULT_SIZE * game.SCALE), (int) (game.TILES_DEFAULT_SIZE * game.SCALE));
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    // Gets
    public Player getPlayer() {
        return player;
    }

    /// Interface StateMethods
    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        player.render(g);
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
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setFire(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                GameState.state = GameState.MENU;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_S:
                player.setDown(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setFire(false);
                break;
        }
    }
}
