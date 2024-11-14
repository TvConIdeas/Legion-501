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

public class Menu extends State implements Statemethods {
    // ====================> ATRIBUTOS <====================
    private MenuButton[] buttons = new MenuButton[4]; // Lo iniciamos porque ya sabemos la cantidad de botones que utilizamos

    // ====================> CONSTRUCTOR <====================
    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150*Game.SCALE),0,GameState.PLAYING); // Primero (y = 150)
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (310*Game.SCALE),1,GameState.OPTIONS); // Tercero (y = 310)
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (390*Game.SCALE),2,GameState.LOGIN); // Cuarto (y = 390)
        buttons[3] = new MenuButton(Game.GAME_WIDTH / 2, (int) (230*Game.SCALE),3,GameState.RANKING); // Segundo (y = 230)
        // La yPos, se debe editar manualmente y le agregamos al final a que state pertene el boton
    }

    private void resetButtons() {
        for(MenuButton mb : buttons) { // El for, comprueba si el mouse está dentro del área del botón
            mb.resetBools();
        }
    }

    /// Interface StateMethods
    @Override
    public void update() {
        for(MenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        //"Titulo" medio feo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 60));
        g.drawString("Legion 501", 70, 100);

        for(MenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)){
                mb.setMousePressed(true); // Boton presionado
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
    public void keyPressed(KeyEvent e) { // BORRAR LUEGO
        // Sin uso
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Sin uso (todavia)
    }
}
