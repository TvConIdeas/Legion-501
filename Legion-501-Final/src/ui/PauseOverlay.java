package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameState.GameState;
import gameState.Playing;
import main.Game;
import utilz.IRenderable;
import utilz.LoadSave;

public class PauseOverlay implements IRenderable {
    // ====================> ATRIBUTOS <====================
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH; // Posicion y tamaño de backroundImg
    private ClassButton[] buttons = new ClassButton[2];

    private ResumeButton resumeB;

    // ====================> CONSTRUCTOR <====================
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        loadButtons();
    }

    // ====================> METODOS <====================
    private void loadButtons(){
        int replayX = (Game.GAME_WIDTH/2)+65;
        int menuX = (Game.GAME_WIDTH/2)-70;
        int resumeX = Game.GAME_WIDTH/2;
        int bY = (int) (55 * Game.SCALE);

        buttons[0] = new ClassButton(replayX, bY + bgY, 0);
        buttons[1] = new ClassButton(menuX, bY + bgY, 1);
        resumeB = new ResumeButton(resumeX, bY + bgY + 95);
    }

    private void resetButtons() {
        for(ClassButton cb : buttons) { // El for, comprueba si el mouse está dentro del área del botón
            cb.resetBools();
        }
        resumeB.resetBools();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpritesAtlas(LoadSave.PAUSE_MENU);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (130 * Game.SCALE);
    }

    private boolean isIn(MouseEvent e, ClassButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }

    private boolean isIn(MouseEvent e, ResumeButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }

    // ====================> METODOS SOBRESCRITOS <====================
    @Override
    public void update() {
        resumeB.update();
        for(ClassButton cb : buttons) {
            cb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Fondo
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
        resumeB.draw(g);
        for(ClassButton cb : buttons) {
            cb.draw(g);
        }
    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, resumeB)) {
            resumeB.setMousePressed(true);
        }

        for(ClassButton cb : buttons) {
            if(isIn(e, cb)) {
                cb.setMousePressed(true);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, buttons[0])) {
            if (buttons[0].isMousePressed()) {
                playing.endLevel();
            }
        } else if (isIn(e, buttons[1])) {
            if (buttons[1].isMousePressed()) {
                playing.endLevel();
                GameState.state = GameState.MENU;
            }
        } else if (isIn(e, resumeB)) {
            if (resumeB.isMousePressed()) {
                playing.setPaused(false);
            }
        }
        resetButtons();
    }

    public void mouseMoved(MouseEvent e) {
        resumeB.setMouseOver(false);
        if (isIn(e, resumeB)) {
            resumeB.setMouseOver(true); // Comprueba si el mouse está dentro del botón
        }

        for (ClassButton cb : buttons)
            cb.setMouseOver(false); //Reinicia el estado `mouseOver` de todos los botones


        for (ClassButton cb : buttons)
            if (isIn(e, cb)) {
                cb.setMouseOver(true); // Comprueba si el mouse está dentro del botón
                break; // Rompe el ciclo para que solo un botón esté en estado `mouseOver`
            }
    }
}
