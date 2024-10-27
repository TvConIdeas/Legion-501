package gameState;

import main.Game;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Menu ==>
 * Estado que funciona como parte principal del juego, uniendo a los diferentes estados.
 */

public class Menu extends State {
    // ====================> ATRIBUTOS <====================
    private MenuButton[] buttons = new MenuButton[3]; // Lo iniciamos porque ya sabemos la cantidad de botones que utilizamos

    // ====================> CONSTRUCTOR <====================
    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    // ====================> METODOS <====================
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_HEIGHT / 2, (int) (100*Game.SCALE),0,GameState.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_HEIGHT / 2, (int) (170*Game.SCALE),1,GameState.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_HEIGHT / 2, (int) (240*Game.SCALE),2,GameState.QUIT);
        // La yPos, se debe editar manualmente y le agregamos al final a que state pertene el boton
    }

    private void resetButtons() {
        for(MenuButton mb : buttons) { // El for, comprueba si el mouse está dentro del área del botón
            mb.resetBools();
        }
    }

    @Override
    public void update() {
        for(MenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for(MenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)){
                mb.setMousePressed(true); // Boton precionado
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)){
                if(mb.isMousePressed()){ // Si se soltó el botón y está en estado `mousePressed`
                    mb.applyGamestate(); // Cambia el estado del juego al estado del botón
                    break;
                }
            }
        }
        resetButtons();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false); //Reinicia el estado `mouseOver` de todos los botones

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true); // Comprueba si el mouse está dentro del botón
                break; // Rompe el ciclo para que solo un botón esté en estado `mouseOver`
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Sin uso (todavia)
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Sin uso (todavia)
    }
}
