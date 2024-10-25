package gameState;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Menu ==>
 * Estado que funciona como parte principal del juego, uniendo a los diferentes estados.
 */

public class Menu extends State {

    // ====================> CONSTRUCTOR <====================
    public Menu(Game game) {
        super(game);
    }

    // ====================> METODOS <====================
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman",Font.PLAIN,50));
        g.drawString("Menu", Game.GAME_WIDTH / 2 - Game.TILES_SIZE, 200);
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
