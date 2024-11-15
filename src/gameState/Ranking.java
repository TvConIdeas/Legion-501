package gameState;

import main.Game;
import ui.ClassButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Ranking extends State implements Statemethods{
    // ====================> ATRIBUTOS <====================
    private ClassButton back;

    // ====================> CONSTRUCTOR <====================
    public Ranking(Game game) {
        super(game);
        back = new ClassButton(Game.GAME_WIDTH- 80,Game.GAME_HEIGHT- 120,1);
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    @Override
    public void update() {
        back.update();
    }

    @Override
    public void draw(Graphics g) {
        // Dibuja fondo y Interfaz
        BufferedImage pincel = LoadSave.GetSpritesAtlas(LoadSave.RANKING_BACKGROUD);
        g.drawImage(pincel, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // Dibuja los nombres de los Ranking

        back.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e, back)) {
            back.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, back)) {
            if (back.isMousePressed()) {
                GameState.state = GameState.MENU;
            }
        }
        back.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        back.setMouseOver(false);
        if (isIn(e, back)) {
            back.setMouseOver(true); // Comprueba si el mouse está dentro del botón
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
