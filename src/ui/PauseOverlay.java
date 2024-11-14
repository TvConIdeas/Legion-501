package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameState.GameState;
import gameState.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.Buttons.*;

public class PauseOverlay {
    // ====================> ATRIBUTOS <====================
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private PauseButton menuB, replayB;
    private MenuButton resumeB;

    // ====================> CONSTRUCTOR <====================
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createUrmButtons();
    }

    // ====================> METODOS <====================
    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new PauseButton(menuX, bY, PB_SIZE, PB_SIZE, 0);
        replayB = new PauseButton(replayX, bY, PB_SIZE, PB_SIZE, 1);
        //resumeB = new MenuButton(unpauseX,bY, 0, GameState.MENU);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpritesAtlas(LoadSave.PAUSE_MENU);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (70 * Game.SCALE);
    }

    public void update() {
        menuB.update();
        replayB.update();
        //resumeB.update();
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        // UrmButtons
        menuB.draw(g);
        replayB.draw(g);
        //resumeB.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB))
            menuB.setMousePressed(true);
        else if (isIn(e, replayB))
            replayB.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
//                playing.resetAll();
//                playing.setGamestate(Gamestate.MENU);
//                playing.unpauseGame();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
//                playing.resetAll();
//                playing.unpauseGame();
            }

            menuB.resetBools();
            replayB.resetBools();
            //resumeB.resetBools();

        }
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        //resumeB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else if (isIn(e, replayB))
            replayB.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
