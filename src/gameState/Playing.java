package gameState;

import entities.*;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Playing ==>
 * Clase que controla todo el funcionamiento del juego.
 */

public class Playing extends State {

    // ====================> ATRIBUTOS <====================
    private Player player;
    public EnemyManager enemyManager;
    private BulletManager bulletManager;
    private int score;

    // ====================> CONSTRUCTOR <====================
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    // ====================> GET | SET <====================
    public Player getPlayer() {
        return player;
    }
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    public BulletManager getBulletManager() {
        return bulletManager;
    }
    
    // ====================> METODOS <====================

    private void initClasses(){
        player = new Player(
                game.GAME_WIDTH/2 - game.TILES_SIZE / 2,
                game.GAME_HEIGHT - game.TILES_SIZE * 2,
                game.TILES_SIZE,
                game.TILES_SIZE);

        enemyManager = new EnemyManager(this);
        bulletManager = new BulletManager(this);
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    /// Interface IRenderable
    @Override
    public void draw(Graphics g) {
        bulletManager.draw(g);
        player.draw(g);
        enemyManager.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /// Interface StateMethods
    @Override
    public void update() {
        bulletManager.update();
        player.update();
        enemyManager.update();
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
            case KeyEvent.VK_E:
                bulletManager.createBullet();
                break;
        }
    }
}
