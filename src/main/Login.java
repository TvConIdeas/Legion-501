package main;

import gameState.GameState;
import gameState.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Login extends State {
    // ====================> ATRIBUTOS <====================
    // Botones
    JButton loginButton = new JButton("Login");
    JButton quitButton = new JButton("Quit");

    // Contenedores
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    // Texto
    JLabel userIDLabel = new JLabel("Username: ");
    JLabel userPasswordLabel = new JLabel("Password: ");

    // Varibles
    HashMap<String, String> logininfo = new HashMap<String, String>();
    boolean uiInitialized = false;

    // ====================> CONSTRUCTOR <====================
    public Login(Game game) {
        super(game);
    }

    // ====================> METODOS <====================
    private void uiInit() {
        GamePanel panel = game.getGamePanel();

        if(panel != null){
            // Configura los componentes de la interfaz (etiquetas, campos, botones)
            userIDLabel.setBounds(50, 100, 75, 25);
            userPasswordLabel.setBounds(50, 150, 75, 25);
            userIDField.setBounds(125, 100, 200, 25);
            userPasswordField.setBounds(125, 150, 200, 25);
            loginButton.setBounds(125, 200, 100, 25);
            quitButton.setBounds(225, 200, 100, 25);

            // Agregar los componentes al panel
            panel.setLayout(null);
            panel.add(userIDLabel);
            panel.add(userIDField);
            panel.add(userPasswordLabel);
            panel.add(userPasswordField);
            panel.add(loginButton);
            panel.add(quitButton);

            // Cuando Toque el Boton Login
            loginButton.addActionListener(e -> {
                String userID = userIDField.getText();
                String password = new String(userPasswordField.getPassword());

                if (logininfo.containsKey(userID) && logininfo.get(userID).equals(password)) {
                    System.out.println("Login exitoso");
                    panel.removeAll();
                    GameState.state  = GameState.MENU;
                } else {
                    System.out.println("Usuario: " + userID + "\nContraseña: " + password);
                    panel.removeAll();
                    GameState.state = GameState.MENU;
                }
            });

            // Cuando Toque el Boton Reset
            quitButton.addActionListener(e -> {
                GameState.state = GameState.QUIT;
            });

            // Reanudar el Update
            uiInitialized = true;
        }
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}

