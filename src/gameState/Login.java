package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.NonexistentUserException;
import main.Game;
import main.GamePanel;
import static utilz.LoadSave.*;
import utilz.LoadSave;
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

    // ====================> CONSTRUCTOR <====================
    public Login(Game game) {
        super(game);
        initUI();
        addEventListeners();
    }

    // ====================> SET/GET <====================
    public void setShowMessage(int showMessage) {
        this.showMessage = showMessage;
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
        userIDLabel = new JLabel("");
        userPasswordLabel = new JLabel("");

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
            game.setUserInGame(new User(name, password));
            GameState.state = GameState.MENU;

        } catch (InvalidUsernameOrPasswordException e){ // Excepcion si name o password estan vacios o >20
            e.getMessage();
            e.printStackTrace();
            showMessage = 1;

        } catch (NonexistentUserException e){ // Excepcion si el nombre de usuario y/o contraseña son incorrectos
            e.getMessage();
            e.printStackTrace();
            showMessage = 2;

        } finally {
            clearFields();
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

        if(showMessage != 0){
            messageCounter(this);
        }
    }

    @Override
    public void draw(Graphics g) {
      // Fondo y Titulo
        LoadSave.drawTitleBackgroud(g,LOGIN_BACKGROUD);

        if(showMessage != 0){
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);

            switch (showMessage) {
                case 1 -> g.drawString("Nombre de usuario y/o contraseña inválidos.", Game.GAME_WIDTH/2-100, 405);
                case 2 -> g.drawString("Nombre de usuario y/o contraseña incorrectos.", Game.GAME_WIDTH/2-100, 405);
            }
        }
        
        // Textos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 25));
        g.drawString("Username", 180, 340);
        g.drawString("Password", 180, 440);
    }
}

