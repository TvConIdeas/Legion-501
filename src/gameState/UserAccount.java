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

    protected int showMessage; // Index para mensajes de error/modificacion
    private int aniTick = 0; // Contador para mensajes de error

    protected GamePanel panel;

    // ====================> CONSTRUCTOR <====================
    public UserAccount(Game game) {
        super(game);
        showMessage = 0;
    }

    // ====================> SET/GET <====================

    // ====================> METODOS <====================
    /** initUI() ==> Instancia todos los componentes. */
    public void initUI(){
        // Instanciar
        backButton = new JButton("Back");

        // Limites
        backButton.setBackground(new Color(0, 11, 88));
        backButton.setForeground(new Color(106, 154, 176));
        backButton.setBounds(193, Game.GAME_HEIGHT-100, 100, 25);
    }

    /** addComponents() ==> Agregar */
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

    public <T extends UserAccount> void messageCounter(T state){ // Cualquier clase que extienda de UserAccount
        aniTick++;
        if(aniTick >= ANI_ERROR_MESSAGE){ // Mostrar hasta que se cumpla un tiempo determinado
            aniTick = 0; // Fin Contador
            state.showMessage = 0;
        }
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
