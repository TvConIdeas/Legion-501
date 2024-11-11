package gameState;

import entities.*;
import main.Game;
import utilz.LevelConfig;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Playing ==>
 * Clase que controla todo el funcionamiento del juego.
 */

public class Playing extends State {

    // ====================> ATRIBUTOS <====================
    public int score;
    private Player player;
    public EnemyManager enemyManager;
    private BulletManager bulletManager;

    public HashMap<String, LevelConfig> levelManager;
    private String currentLevel = "easy"; // Nivel actual por defecto

    // ====================> CONSTRUCTOR <====================
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    // ====================> GET | SET <====================
    public Player getPlayer() {
        return player;
    }

    public String getCurrentLevel(){
        return currentLevel;
    }

    // ====================> METODOS <====================
    private void initClasses(){
        player = new Player(
                (float) Game.GAME_WIDTH/2 - (float) Game.TILES_SIZE /2,
                Game.GAME_HEIGHT - Game.TILES_SIZE * 2,
                Game.TILES_SIZE,
                Game.TILES_SIZE);

        enemyManager = new EnemyManager(this);
        bulletManager = new BulletManager(this);
        score = 0;

        levelManager = new HashMap<>();
        enemyManager.loadConfigLevel(levelManager);
        startLevel(currentLevel); // Iniciar el primer nivel con dificultad "Easy"
    }

    /** startLevel() ==> Configurar nivel con la dificultad actual. */
    public void startLevel(String dificultad) {
        if (levelManager.containsKey(dificultad)) { // Si existe la dificultad
            currentLevel = dificultad; // Actualiza el nivel actual
            enemyManager.createAliens(); // Crea los aliens con la nueva configuración
            score = 0; // Reinicia el puntaje si es necesario
        } else {
            System.out.println("Nivel no encontrado: " + dificultad);
        }
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
        score = 0;
    }

    /// Interface IRenderable
    @Override
    public void draw(Graphics g) {
        bulletManager.draw(g);
        player.draw(g);
        enemyManager.draw(g);
    }

    @Override
    public void update() {
        bulletManager.update();
        player.update();
        enemyManager.update();

        // Ejemplo de lógica para cambiar de nivel basado en el puntaje
        if (score >= 100 && currentLevel.equals("easy")) {
            startLevel("medium");
        } else if (score >= 200 && currentLevel.equals("medium")) {
            startLevel("hard");
        }
    }

    /// Interface StateMethods
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
}