package gameState;

import main.Game;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends State {

    // ====================> ATRIBUTOS <====================
    JButton registerButton = new JButton("Register");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("userID:");
    JLabel userPasswordLabel = new JLabel("password:");

    String name = "";
    String password = "";

    boolean uiInitialized = false;

    // ====================> CONTRUCTOR <====================
    public Register(Game game) {
        super(game);
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================

    public void uiInit(){
        GamePanel panel = game.getGamePanel();
        if(panel != null){
            panel.setLayout(null);

            // Configura los componentes de la interfaz (etiquetas, campos, botones)
            registerButton.setBounds(125, 200, 100, 25);
            userIDField.setBounds(125, 100, 200, 25);
            userPasswordField.setBounds(125, 150, 200, 25);
            userIDLabel.setBounds(50, 100, 75, 25);
            userPasswordLabel.setBounds(50, 150, 75, 25);

            // Agregar los componentes al panel
            panel.add(registerButton);
            panel.add(userIDField);
            panel.add(userPasswordField);
            panel.add(userIDLabel);
            panel.add(userPasswordLabel);

            // Agregar ActionListener al botón de login
            registerButton.addActionListener(e -> {

                createAccount();
            });

            uiInitialized = true;
        }
    }

    public void createAccount(){

        name = userIDField.getText();
        password = userPasswordField.getText();

        try {
            if(name.length() >= 5 || password.length() == 0){
                throw new IllegalArgumentException();
            }
            System.out.println("Registrado.");
            game.getGamePanel().removeAll(); // Limpiar pantalla
            GameState.state = GameState.MENU;

        } catch (IllegalArgumentException exception){
            System.out.println("Error. Ingrese nuevamente.");
        }
    }

    // Methods interfaz IRenderable
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
