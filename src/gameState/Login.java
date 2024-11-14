package gameState;

import main.Game;
import main.GamePanel;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static utilz.LoadSave.*;

public class Login extends State {
    // ====================> ATRIBUTOS <====================
    // Botones
    private JButton loginButton;
    private JButton quitButton;

    // Labels
    private JLabel userIDLabel;
    private JLabel userPasswordLabel;

    // Fields
    private JTextField userIDField;
    private JPasswordField userPasswordField;

    // Atributos a ingresar
    private String name;
    private String password;

    // ====================> CONSTRUCTOR <====================
    public Login(Game game) {
        super(game);
        initUI();
        //addEventListeners();
    }

    // ====================> METODOS <====================
    public void initUI(){
        // Instanciar
        quitButton = new JButton("Quit");
        loginButton = new JButton("Login");
        userIDLabel = new JLabel("");
        userPasswordLabel = new JLabel("");
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();

        // Limites
        // Botones
        quitButton.setBackground(new Color(116, 9, 56));
        quitButton.setForeground(new Color(222, 124, 125));
        loginButton.setBackground(new Color(82, 110, 72));
        loginButton.setForeground(new Color(158, 223, 156));
        quitButton.setBounds(68, Game.GAME_HEIGHT-100, 100, 25);
        loginButton.setBounds( 318, Game.GAME_HEIGHT-100, 100, 25);

        //Labels
        userIDLabel.setBounds(140, 270, 75, 25);
        userPasswordLabel.setBounds(140, 370, 75, 25);

        //Fields
        userIDField.setBackground(Color.LIGHT_GRAY);
        userIDField.setForeground(Color.DARK_GRAY);
        userPasswordField.setBackground(Color.LIGHT_GRAY);
        userPasswordField.setForeground(Color.DARK_GRAY);
        userIDField.setBounds(140, 350, 200, 25);
        userPasswordField.setBounds(140, 450, 200, 25);
    }

    // ====================> METODOS INTERFACE <====================
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        // Fondo y Titulo
        LoadSave.drawTitleBackgroud(g,LOGIN_BACKGROUD);

        // Textos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 25));
        g.drawString("Username", 180, 340);
        g.drawString("Password", 180, 440);
    }
}

