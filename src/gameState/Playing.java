package gameState;

import entities.*;
import main.Game;
import ui.PauseOverlay;
import utilz.LevelConfig;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static utilz.Constants.ANI_RESTART_LEVEL;
import static utilz.Constants.PlayerConstants.EXPLODE;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.LoadSave.PLAYING_BACKGROUD;

/**
 * Playing ==>
 * Clase que controla todo el funcionamiento del juego.
 */

public class Playing extends State implements Statemethods {

    // ====================> ATRIBUTOS <====================
    public int score; // Puntaje
    public int alienCount; // Contador de Aliento
    private int aniTick; // Contador para reiniciar el nivel
    private String currentLevel = "easy"; // Nivel actual por defecto
    private Player player; // Jugador

    // Booleanos
    public boolean hitPlayer = false; // Booleano para cuando se golpea al Jugador
    private boolean gameOver = false; // Booleano para Game Over
    private boolean paused = false; // Booleano para Pausa
    private boolean backMenu = false; // Booleano para regresar al menu

    // Managers
    public EnemyManager enemyManager; // Enemy Manager, controla los enemigos
    public BulletManager bulletManager; // Bullet Manager, controla las balas
    public HashMap<String, LevelConfig> levelManager; // Level Manager, controla el nivel
    public PauseOverlay pauseOverlay;

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

    public void setPaused(boolean paused) {
        this.paused = paused;
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
        pauseOverlay = new PauseOverlay(this);
        levelManager = new HashMap<>();
        enemyManager.loadConfigLevel(levelManager);
        score = 0;
        startLevel(currentLevel); // Iniciar el primer nivel con dificultad "Easy"
    }

    /** startLevel() ==> Configurar nivel con la dificultad actual. */
    public void startLevel(String dificultad) {
        if (levelManager.containsKey(dificultad)) { // Si existe la dificultad
            currentLevel = dificultad; // Actualiza el nivel actual
            bulletManager.clearBullets(); // Limpieza de balas en pantalla
            enemyManager.getEnemies().clear(); // Limpia los enemigos anteriores
            enemyManager.createAliens(); // Crea los aliens con la nueva configuración
        } else {
            System.out.println("Nivel no encontrado: " + dificultad);
        }
    }

    /** verifyLevel() ==> Metodo encargado de verificar que accion hacer */
    public void verifyLevel(){
        alienCount = enemyManager.getRemainingAliens(); // Actualizamos alienCount
        if (levelManager.containsKey(currentLevel)) { // Verifica si existe el nivel
            if (alienCount == 0){ // Si se mataron todos los enemigos, pasa de nivel
                nextLevel();
            }
            if(hitPlayer){ // Si el jugador es golpeado
                playerHit();
            }
            if(backMenu){
                endLevel();
            }
            if(player.lives == 0){ // Si no hay mas vidas
                gameOver = true;
            } else if(player.getState() == EXPLODE){ // Si el jugador Explota
                aniTick++;
                if(aniTick >= ANI_RESTART_LEVEL) { // Si el contador llega al limite
                    restartLevel();
                }
            }
        }
    }

    /** nextLevel() ==> Dependiendo el puntaje, cambia de dificultad*/
    public void nextLevel(){
        if (score >= 150 && currentLevel.equals("easy")) { // Cambiar de nivel en caso de pasar puntos necesarios
            startLevel("medium");
        } else if (score >= 500 && currentLevel.equals("medium")) {
            startLevel("hard");
        }
        else{
            startLevel(currentLevel); // Comenzar mismo nivel en caso de no superar puntos
        }
    }

    /** endLevel() ==> Termina la partida y regresa todo a 0.*/
    public void endLevel(){
        if(gameOver) { // Cuando el usuario pierda la partida
            if (game.getUserInGame().getBestScore() < score) { // Si el puntaje actual supera su puntaje mas alto
                game.getUserInGame().setBestScore(score); // Settea su nuevo bestScore
                game.getJsonUserManager().overwriteUser(game.getUserInGame()); // Guarda los cambios en el archivo
            }
        }
        // Reseta valores del juego
        score = 0;
        player.lives = 3;
        currentLevel = "easy";
        resetBooleans();
        restartLevel();
    }

    /** playerHit() ==> Si el jugador es golpeado por una bala. */
    public void playerHit(){
        enemyManager.stopEnemys = true; // Detenmos el disparo y movimiento de los enemigos
        bulletManager.bulletPlayerArr.clear();  // Limpiamos las
        bulletManager.bulletAlienArr.clear();   // balas en pantalla
        player.disableHitbox(); // Desactivamos la Hitbox
        player.resetDirBooleans();
        player.newState(EXPLODE); // Explota el jugador
        player.lives--; // Sacamos una vida
        hitPlayer = false;
    }

    /** restartLevel() ==> Cuando el jugador explota, el nivel se reinicia. */
    public void restartLevel(){
        aniTick = 0; // Fin Contador
        player.newState(IDLE); // Jugador a State IDLE
        player.setX((float) Game.GAME_WIDTH / 2 - (float) Game.TILES_SIZE / 2); // Ubicando el jugador
        player.setY(Game.GAME_HEIGHT - Game.TILES_SIZE * 2);                 // en el centro de vuelta
        player.initHitbox(player.getX(), player.getY(), (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
        enemyManager.stopEnemys = false; // Reactivamos a los enemigos
        startLevel(currentLevel); // Comenzar nivel
    }

    public void resetBooleans(){
        hitPlayer = false;
        gameOver = false;
        paused = false;
        backMenu = false;
    }

    /** windowFocusLost() ==> Cuando se pierde el foco del programa */
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    /** <====== Interface IRenderable ======> */
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
        g.setFont(new Font("Console", Font.BOLD, 15));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Enemies: " + alienCount, 10, 35);
        g.drawString("Lives: " + player.lives, 10, 50);

        // Pausa
        if(paused && !gameOver){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }

        // Pantalla de Game Over
        if(gameOver){
            g.setColor(new Color(47, 70, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 90, 350);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press BackSpace for Back To Menu", 70, 400);
            g.drawString("Score: " + score, Game.GAME_WIDTH/2 - 50, 430);
        }
    }

    @Override
    public void update() {
        verifyLevel();
        if(paused){
            pauseOverlay.update();
        }
        if (!paused && !gameOver) { // Solo actualiza cuando no está pausado ni en game over
            bulletManager.update();
            player.update();
            enemyManager.update();
        }
    }

    /** <====== Interface StateMethods ======> */
    @Override
    public void keyPressed(KeyEvent e){ // Solo cuando el jugador este IDLE
        if(player.getState() == IDLE){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    if(paused){
                        paused = false;
                    }else{
                        paused = true;
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(player.getState() == IDLE) { // Solo cuando el jugador este IDLE
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
        if(gameOver){
            if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                endLevel();
                GameState.state = GameState.MENU;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
         if (paused)
            pauseOverlay.mouseMoved(e);
    }
}