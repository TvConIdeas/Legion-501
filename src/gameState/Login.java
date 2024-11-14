package gameState;

import main.Game;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Login extends State {
    // ====================> ATRIBUTOS <====================
    // Botones
    JButton loginButton = new JButton("Login");
    JButton quitButton = new JButton("Quit");
    JButton registerButton = new JButton("Register");

    // Contenedores
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    // Texto
    JLabel userIDLabel = new JLabel("Username: ");
    JLabel userPasswordLabel = new JLabel("Password: ");

    // Variables
    boolean uiInitialized = false;

    // ====================> CONSTRUCTOR <====================
    public Login(Game game) {
        super(game);

    }

    // ====================> METODOS <====================
    public void uiInit(){

    }

    // ====================> METODOS INTERFACE <====================
    @Override
    public void update() {
        if(!uiInitialized){
            uiInit();
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}

