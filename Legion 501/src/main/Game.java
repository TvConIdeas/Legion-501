package main;

import java.awt.Graphics;
import gameState.GameState;
import gameState.Menu;
import gameState.Playing;

public class Game implements Runnable {

    // Atributos
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 60;
    private final int UPS_SET = 100;

    private Playing playing;
    private Menu menu;

    // Constantes Tiles
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f; // 48x48
    public final static int TILES_IN_WIDTH = 12;
    public final static int TILES_IN_HEIGHT = 15;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    // Constructor
    public Game(){
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void initClasses(){
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        switch (GameState.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;

                default: break;
        }
    }

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


    // GameLoop
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

        while (true) {
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

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }

    public void windowFocusLost() {
        if(GameState.state == GameState.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }
}
