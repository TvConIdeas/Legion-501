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
    JTextField adminCode = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    // CheckBox
    JCheckBox adminCheckBox = new JCheckBox("Admin");

    // Texto
    JLabel userIDLabel = new JLabel("Username: ");
    JLabel userPasswordLabel = new JLabel("Password: ");
    JLabel adminCodeLabel = new JLabel("Admin Code: ");

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

            //Configurar Ubicaciones

            // Usuario
            userIDLabel.setBounds(50, 100, 75, 25);
            userIDField.setBounds(125, 100, 200, 25);

            // Contraseña
            userPasswordLabel.setBounds(50, 150, 75, 25);
            userPasswordField.setBounds(125, 150, 200, 25);

            // Botones
            loginButton.setBounds(125, 200, 100, 25);
            quitButton.setBounds(225, 200, 100, 25);
            registerButton.setBounds(Game.GAME_WIDTH-150, Game.GAME_HEIGHT-50, 100, 25);

            // CheckBox
            adminCheckBox.setBounds(225, 250, 100, 25);
            adminCode.setBounds(225, 250, 100, 25);
            adminCodeLabel.setBounds(225, 250, 100, 25);

            // Agregar los componentes al panel
            panel.setLayout(null);
            panel.add(userIDLabel);
            panel.add(userIDField);
            panel.add(userPasswordLabel);
            panel.add(userPasswordField);
            panel.add(loginButton);
            panel.add(quitButton);
            panel.add(adminCheckBox);
            panel.add(registerButton);

            // Cuando Toque el Boton Login (Crear metodo Comprobar Usuario)
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

            // Cuando Toque el Boton Quit
            quitButton.addActionListener(e -> {
                GameState.state = GameState.QUIT;
            });

            registerButton.addActionListener(e -> {
                panel.removeAll();
                GameState.state = GameState.REGISTER;
            });

            // Cuando se toque el CheckBox
            adminCheckBox.addActionListener(e -> {
                panel.add(adminCodeLabel);
                panel.add(adminCode);
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
}

