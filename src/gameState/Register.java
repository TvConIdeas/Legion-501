package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.PasswordMismatchException;
import exceptions.UsernameUnavailableException;
import main.Game;
import users.User;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.ANI_ERROR_MESSAGE;
import static utilz.LoadSave.*;

public class Register extends UserAccount {
    // ====================> ATRIBUTOS <====================
    // Botones
    private JButton registerButton;
    private JButton quitButton;

    // Texto
    private JLabel userIDLabel;
    private JLabel userPasswordLabel;
    private JLabel confirmPasswordLabel;

    // Contenedores
    private JTextField userIDField;
    private JPasswordField userPasswordField;
    private JPasswordField confirmPasswordField;

    // Mensajes de error
    private boolean showMessage = false;
    private boolean showMessage2 = false;
    private boolean showMessage3 = false;


    // ====================> CONTRUCTOR <====================
    public Register(Game game) {
        super(game);
        initUI();
        addEventListeners();
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================

    /** initUI ==> Instanciar los componentes. */
    @Override
    public void initUI(){
        super.initUI();
        // Instanciar
        registerButton = new JButton("Register");
        quitButton = new JButton("Quit");
        userIDLabel = new JLabel("");
        userPasswordLabel = new JLabel("");
        confirmPasswordLabel = new JLabel("");

        userIDField = new JTextField();
        userPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Limites - edita ian :p
        registerButton.setBounds(Game.GAME_WIDTH-125, Game.GAME_HEIGHT-50, 100, 25);
        quitButton.setBounds(25, Game.GAME_HEIGHT-75, 75, 20);
        userIDLabel.setBounds(Game.GAME_WIDTH/2-100, 250, 75, 25);
        userPasswordLabel.setBounds(Game.GAME_WIDTH/2-100, 325, 75, 25);
        confirmPasswordLabel.setBounds(Game.GAME_WIDTH/2-100, 400, 110, 25);
        userIDField.setBounds(Game.GAME_WIDTH/2-100, 275, 200, 25);
        userPasswordField.setBounds(Game.GAME_WIDTH/2-100, 350, 200, 25);
        confirmPasswordField.setBounds(Game.GAME_WIDTH/2-100, 425, 200, 25);
    }

    /** addComponents() ==> Agregar los componentes al GamePanel. */
    @Override
    public void addComponents(){
        super.addComponents();
        panel.setLayout(null);
        panel.add(registerButton);
        panel.add(quitButton);
        panel.add(userIDLabel);
        panel.add(userPasswordLabel);
        panel.add(confirmPasswordLabel);
        panel.add(userIDField);
        panel.add(userPasswordField);
        panel.add(confirmPasswordField);
    }

    /** addEventListeners() ==> Settear los botones para que hagan una acción al ser oprimidos. */
    @Override
    public void addEventListeners(){
        registerButton.addActionListener(e -> registerUser());
        quitButton.addActionListener(e -> GameState.state = GameState.QUIT);
        backButton.addActionListener(e -> {
            game.getGamePanel().removeAll();
            flagAddComponents = false; // Para que cuando vuelva a REGISTER entre a addComponents
            clearFields();
            GameState.state = GameState.LOGIN;
        });
    }

    /** registerUser() ==> Registrar usuario. */
    public void registerUser(){
        String name = userIDField.getText(); // Leer nombre
        String password = new String(userPasswordField.getPassword()); // Leer contraseña
        String confirmPassword = new String(confirmPasswordField.getPassword());

        try {
            if(name.isBlank() || password.isBlank() || confirmPassword.isBlank()
                    || name.length() >20 || password.length() >20 || confirmPassword.length() >20){ // Si esta vacio o es >20
                throw new InvalidUsernameOrPasswordException();

            } else if (!game.getJsonUserManager().isUsernameAvailable(name)) { // Si el nombre de usuario ya existe
                throw new UsernameUnavailableException();

            } else if (!confirmPassword.equals(password)) { // Si las contrseñas (password y confirmPassword) no coinciden
                throw new PasswordMismatchException();
            }

            User user = new User(name, password);
            game.getJsonUserManager().userToFile(user); // Pasar user al archivo
            game.getGamePanel().removeAll(); // Eliminar componentes de la pantalla
            flagAddComponents = false; // Para que cuando vuelva a REGISTER pueda entrar a addComponents
            GameState.state = GameState.LOGIN; // Cambiar de state
            
        } catch (InvalidUsernameOrPasswordException e){ // Excepcion si name o password esta vacio y mas de 20 caracteres
            e.getMessage();
            e.printStackTrace();
            showMessage = true;

        } catch (UsernameUnavailableException e){ // Excepcion si el name ya existe
            e.getMessage();
            e.printStackTrace();
            showMessage2 = true;

        } catch (PasswordMismatchException e){
            e.getMessage();
            e.printStackTrace();
            showMessage3 = true;

        } finally {
            clearFields();
        }
    }

    /** clearFields() ==> Borrar los contenidos de los fields. */
    @Override
    public void clearFields(){
        userIDField.setText("");
        userPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    // Methods interfaz IRenderable
    @Override
    public void update() {
        super.update();

        if(showMessage){
            showMessage = messageCounter(showMessage); // Contador para que desaparezca el mensaje
        }
        if(showMessage2){
            showMessage2 = messageCounter(showMessage2); // Contador para que desaparezca el mensaje
        }
        if(showMessage3){
            showMessage3 = messageCounter(showMessage3); // Contador para que desaparezca el mensaje
        }
    }

    @Override
    public void draw(Graphics g) {
  
       // Fondo y Titulo
        LoadSave.drawTitleBackgroud(g,REGISTER_BACKGROUD);
        // Titulo
        g.setFont(new Font("Console", Font.BOLD, 70));
        g.setColor(Color.WHITE);
        g.drawString("Legion 501", 50, 150);
        g.setFont(new Font("Console", Font.BOLD, 40));
        g.setColor(Color.GRAY);
        g.drawString("Register", 150, 230);

        if(showMessage || showMessage2 || showMessage3){

            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);
            if(showMessage){
                g.drawString("Nombre de usuario y/o contraseña inválidos.", Game.GAME_WIDTH/2-100, 480);
            }
            if(showMessage2){
                g.drawString("Nombre de usuario existente.", Game.GAME_WIDTH/2-100, 480);
            }
            if(showMessage3){
                g.drawString("Las contraseñas no coinciden.", Game.GAME_WIDTH/2-100, 480);
            }     

        // Textos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 25));
        g.drawString("Username", 180, 340);
        g.drawString("Password", 180, 440);
        }
    }
}
