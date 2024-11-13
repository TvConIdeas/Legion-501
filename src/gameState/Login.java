package gameState;

import inputs.KeyboardInputs;
import main.Game;
import main.GamePanel;
import utilz.IRenderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Login extends State {

    // ====================> ATRIBUTOS <====================
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("userID:");
    JLabel userPasswordLabel = new JLabel("password:");
    JLabel messageLabel = new JLabel();
    HashMap<String, String> logininfo = new HashMap<String, String>();

    boolean uiInitialized = false;

    // ====================> CONSTRUCTOR <====================
    public Login(Game game) {
        super(game);
        // Llamamos al método para inicializar la UI
    }

    // ====================> MÉTODO PARA INICIALIZAR LA UI <====================
    private void uiInit() {

        GamePanel panel = game.getGamePanel();

        if(panel != null){

            // Configura los componentes de la interfaz (etiquetas, campos, botones)
            userIDLabel.setBounds(50, 100, 75, 25);
            userPasswordLabel.setBounds(50, 150, 75, 25);
            userIDField.setBounds(125, 100, 200, 25);
            userPasswordField.setBounds(125, 150, 200, 25);
            loginButton.setBounds(125, 200, 100, 25);
            resetButton.setBounds(225, 200, 100, 25);

            // Agregar los componentes al panel
            panel.setLayout(null);
            panel.add(userIDLabel);
            panel.add(userIDField);
            panel.add(userPasswordLabel);
            panel.add(userPasswordField);
            panel.add(loginButton);
            panel.add(resetButton);

            // Agregar ActionListener al botón de login
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener el texto ingresado por el usuario en los campos de texto
                    String userID = userIDField.getText();
                    String password = new String(userPasswordField.getPassword());

                    // Lógica para verificar si el usuario y la contraseña son correctos
                    /*if (logininfo.containsKey(userID) && logininfo.get(userID).equals(password)) {
                        System.out.println("Login exitoso");
                        // Aquí puedes agregar lo que sucede cuando el login es exitoso
                    } else {
                        System.out.println("Usuario o contraseña incorrectos");
                        // Aquí puedes mostrar un mensaje de error al usuario
                    }*/
                }
            });

            // ActionListener para el botón de reset
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Limpiar los campos de texto
                    userIDField.setText("");
                    userPasswordField.setText("");
                }
            });

            uiInitialized = true;
        }
    }

    // ====================> Métodos IRenderable <====================
    @Override
    public void update() {
        // Actualizar la interfaz si es necesario

        if(!uiInitialized){
            uiInit();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Dibujar los elementos gráficos si es necesario
    }

}
