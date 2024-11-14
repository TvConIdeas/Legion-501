package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.NonexistentUserException;
import main.Game;
import main.GamePanel;
import users.User;

import javax.swing.*;
import java.awt.*;

import static utilz.Constants.ANI_ERROR_MESSAGE;

public class Login extends UserAccount {
    // ====================> ATRIBUTOS <====================
    // Botones
    JButton loginButton;
    JButton quitButton;

    // Contenedores
    JTextField userIDField;
    JPasswordField userPasswordField;

    // Texto
    JLabel userIDLabel;
    JLabel userPasswordLabel;

    // Flags

    // ====================> CONSTRUCTOR <====================
    public Login(Game game) {
        super(game);
        initUI();
        addEventListeners();
    }

    // ====================> METODOS <====================
    @Override
    public void initUI(){
        super.initUI();
        // Instanciar
        loginButton = new JButton("Login");
        quitButton = new JButton("Quit");
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();
        userIDLabel = new JLabel("User name:");
        userPasswordLabel = new JLabel("Password:");

        backButton.setText("Register");

        // Limites
        loginButton.setBounds(Game.GAME_WIDTH-125, Game.GAME_HEIGHT-50, 100, 25);
        quitButton.setBounds(25, Game.GAME_HEIGHT-75, 75, 20);
        userIDField.setBounds(Game.GAME_WIDTH/2-100, 275, 200, 25);
        userPasswordField.setBounds(Game.GAME_WIDTH/2-100, 350, 200, 25);
        userIDLabel.setBounds(Game.GAME_WIDTH/2-100, 250, 75, 25);
        userPasswordLabel.setBounds(Game.GAME_WIDTH/2-100, 325, 75, 25);
    }

    @Override
    public void addComponents(){
        super.addComponents();
        panel.setLayout(null);
        panel.add(loginButton);
        panel.add(quitButton);
        panel.add(userIDField);
        panel.add(userPasswordField);
        panel.add(userIDLabel);
        panel.add(userPasswordLabel);
    }

    @Override
    public void addEventListeners(){
        loginButton.addActionListener(e -> login());
        quitButton.addActionListener(e -> GameState.state = GameState.QUIT);
        backButton.addActionListener(e ->{ // Register button
            game.getGamePanel().removeAll();
            flagAddComponents = false;
            clearFields();
            GameState.state = GameState.REGISTER;
        });
    }

    public void login(){
        String name = userIDField.getText();
        String password = new String(userPasswordField.getPassword());

        try {
            if(name.isBlank() || password.isBlank() || name.length() >20 || password.length() >20) { // Si esta vacio o es >20
                throw new InvalidUsernameOrPasswordException();
            } else if(!game.getJsonUserManager().verifyUserInfo(new User(name, password))){
                throw new NonexistentUserException();
            }

            game.getGamePanel().removeAll();
            flagAddComponents = false;
            GameState.state = GameState.MENU;

        } catch (InvalidUsernameOrPasswordException e){
            e.getMessage();
            e.printStackTrace();
//            showMessage = true;

        } catch (NonexistentUserException e){
            e.getMessage();
            e.printStackTrace();
//            showMessage = true;
        }
    }

    @Override
    public void clearFields(){
        userIDField.setText("");
        userPasswordField.setText("");
    }

    // ====================> METODOS INTERFACE <====================
    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics g) {
        // Titulo
        g.setFont(new Font("Console", Font.BOLD, 70));
        g.setColor(Color.WHITE);
        g.drawString("Legion 501", 50, 150);
        g.setFont(new Font("Console", Font.BOLD, 40));
        g.setColor(Color.GRAY);
        g.drawString("Login", 170, 230);

        /*if(showMessage){
            g.fillRect(125, 200, 200, 50);
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);
            g.drawString("Nombre de usuario y/o contraseña incorrectos.", 150, 225);
        }*/
    }
}

