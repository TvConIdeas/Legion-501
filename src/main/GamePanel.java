package main;

import java.awt.*;
import javax.swing.JPanel;

import gameState.GameState;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

/**
 * GamePanel ==>
 * Clase encargada de la interfaz gráfica.
 *
 * También, settea el tamaño de la pantalla.
 */
public class GamePanel extends JPanel {

    // ====================> ATRIBUTOS <====================
    private MouseInputs mouseInputs;
    private Game game;

    // ====================> CONSTRUCTOR <====================
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        setBackground(new Color(11, 25, 44));
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    // ====================> GETTERS <====================
    public Game getGame() {
        return game;
    }

    // ====================> METODOS <====================
    /** setPanelSize() ==> Settea el tamaño de la pantalla. */
    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Size Window :" + GAME_WIDTH + "x" + GAME_HEIGHT ); // Mostrar en consola el tamaño
    }

    /** paintComponent() ==> Method encargado de "dibujar" en el Jpanel.
     * La clase Graphics nos proporciona muchas funciones para dibujar en la pantalla. */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); /* Obligatorio siempre en esta función. Llama al method de la superclase
                                 para que el Panel funcione correctamente. */

        game.render(g);
    }
}
