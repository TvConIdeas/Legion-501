package gameState;

import entities.*;
import main.Game;
import utilz.LevelConfig;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static utilz.Constants.PlayerConstants.EXPLODE;
import static utilz.LoadSave.PLAYING_BACKGROUD;

/**
 * Playing ==>
 * Clase que controla todo el funcionamiento del juego.
 */

public class Playing extends State {

    // ====================> ATRIBUTOS <====================
    public int score;
    public int alienCount;
    private Player player;
    public EnemyManager enemyManager;
    public BulletManager bulletManager;
    public HashMap<String, LevelConfig> levelManager;
    private String currentLevel = "easy"; // Nivel actual por defecto

    private boolean gameOver = false;
    public boolean hitPlayer = false;

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
    /** initClasses() ==> Inicializa todas las clases */
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
            bulletManager.bulletPlayerArr.clear();
            enemyManager.createAliens(); // Crea los aliens con la nueva configuración
        } else {
            System.out.println("Nivel no encontrado: " + dificultad);
        }
    }

    /** verifyLevel() ==> Verificacion por si no hay mas enemigos o para cambiar de dificultad */
    public void verifyLevel(){
        if (levelManager.containsKey(currentLevel)) {
            if (alienCount == 0){
                if (score >= 150 && currentLevel.equals("easy")) {
                    startLevel("medium");
                } else if (score >= 500 && currentLevel.equals("medium")) {
                    startLevel("hard");
                }
                else{
                    startLevel(currentLevel);
                }
            }
            if(hitPlayer){
                player.disableHitbox();
                player.newState(EXPLODE);
                player.lives--;
                hitPlayer = false;
            }
        }
    }

    /** windowFocusLost() ==> Cuando se pierde el foco del programa */
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    /// Interface IRenderable
    @Override
    public void draw(Graphics g) {
        // Dibujar Fondo
        BufferedImage image = LoadSave.GetSpritesAtlas(PLAYING_BACKGROUD);;
        g.drawImage(image, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // Dibujar Manager y Enemigos
        bulletManager.draw(g);
        player.draw(g);
        enemyManager.draw(g);

        // Estadisticas (Editar)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Enemies: " + alienCount, 10, 35);
        g.drawString("Lives: " + player.lives, 10, 50);

        // Cartel de Game Over
        if(gameOver){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("GAME OVER", 200, 350);
        }

    }

    @Override
    public void update() {
        if(!gameOver){
            bulletManager.update();
            player.update();
            enemyManager.update();
            verifyLevel();
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