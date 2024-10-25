package main;

import java.awt.Graphics;
import gameState.GameState;
import gameState.Menu;
import gameState.Playing;

/***
 * Game ==>
 *
 * Esta clase es el núcleo del programa, encargada de dirigir a las diferentes clases.
 *
 * También se encarga de crear el Game Loop, el cual actualiza la pantalla en tiempo real, tambien conocido como
 * Frames o FPS (Frames por segundo). Para crear este reloj es necesario implementar la clase Thread.
 *
 * Una vez que un hilo se inicia, no para hasta que se lo quiera detener.
 */

public class Game implements Runnable {

    // ====================> ATRIBUTOS <====================
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 60; // Frames Per Second
    private final int UPS_SET = 100; // Updates Per Second

    // GameStates
    private Playing playing;
    private Menu menu;

    // Constantes Tiles
    public final static int TILES_DEFAULT_SIZE = 32; // 32 bits
    public final static float SCALE = 1.5f; // Variable que permite "agrandar" el tamaño de los Tile
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); // 48x48
    public final static int TILES_IN_WIDTH = 12; // Columnas
    public final static int TILES_IN_HEIGHT = 15; // Filas
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // Ancho en pixeles, 576
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // Alto en pixeles, 720


    // ====================> CONSTRUCTOR <====================
    public Game(){
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    // ====================> GETTERS <====================
    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    // ====================> METODOS <====================
    /** initClasses() ==> Instancia las clases. */
    private void initClasses(){
        menu = new Menu(this);
        playing = new Playing(this);
    }

    /** startGameLoop() ==> Instancia el Thread e inicia el Game Loop. */
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /** update() ==> Actualizar la información por momento. */
    public void update(){
        switch (GameState.state){ // Llamar method update() según el gameState
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;

                default: break;
        }
    }

    /*** render() ==> Renderiza cada clase. Dibuja la ventana, con la informacion actualizada. */
    public void render(Graphics g){

        switch (GameState.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;

            default: break;
        }
    }


    /** run() ==> Se crea el GameLoop (núcleo del juego). Una vez creado un Thread (Hilo), automáticamente usa este método. */
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Mostrar por consola los Frames y Updates por segundo.
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }

    /** windowFocusLost() ==> Frena los procesos al momento de perder el foco de la ventana del juego. */
    public void windowFocusLost() {
        if(GameState.state == GameState.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }
}
