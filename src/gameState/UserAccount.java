package gameState;

import main.Game;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;

import static utilz.Constants.ANI_ERROR_MESSAGE;

public abstract class UserAccount extends State {

    // ====================> ATRIBUTOS <====================
    // Botones
    protected JButton backButton;

    // Flags
    protected boolean flagAddComponents = false;
//    protected boolean showMessage = false;

    protected int aniTick = 0;

    protected GamePanel panel;

    // ====================> CONSTRUCTOR <====================
    public UserAccount(Game game) {
        super(game);
    }

    // ====================> METODOS <====================
    public void initUI(){
        // Instanciar
        backButton = new JButton("Back");

        // Limites
        backButton.setBounds(25, Game.GAME_HEIGHT-50, 100, 25);
    }

    public void addComponents(){
        this.panel = game.getGamePanel();
        if(panel != null){
            panel.setLayout(null);
            panel.add(backButton);
            flagAddComponents = true;
        }
    }

    public abstract void addEventListeners();

    public abstract void clearFields();

    public boolean messageCounter(boolean message){
        aniTick++;
        if(aniTick >= ANI_ERROR_MESSAGE){ // Mostrar hasta que se cumpla un tiempo determinado
            aniTick = 0; // Fin Contador
            message = false;
        }
        return message;
    }

    @Override
    public void update() {
        if (!flagAddComponents){
            addComponents();
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}
