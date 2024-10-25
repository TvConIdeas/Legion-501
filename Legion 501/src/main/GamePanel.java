package main;

import java.awt.*;
import javax.swing.JPanel;

import gameState.GameState;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    // Atributos
    private MouseInputs mouseInputs;
    private Game game;

    // Constructor
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    // Panel Size
    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Size Window :" + GAME_WIDTH + "x" + GAME_HEIGHT );
    }

    // Update
    public void updateGame() {

    }

    // PaintComponent - Metodo que se encarga de "dibujar" con la clase Graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    // Get
    public Game getGame() {
        return game;
    }
}
