package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.PasswordMismatchException;
import exceptions.UsernameUnavailableException;
import main.Game;
import users.User;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;

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

    // ====================> CONTRUCTOR <====================
    public Register(Game game) {
        super(game);
        initUI();
        addEventListeners();
    }

    // ====================> GET | SET <====================
    public void setShowMessage(int showMessage){
        this.showMessage = showMessage;
    }

    // ====================> METODOS <====================
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

        // Botones
        quitButton.setBackground(new Color(116, 9, 56));
        quitButton.setForeground(new Color(222, 124, 125));
        registerButton.setBackground(new Color(82, 110, 72));
        registerButton.setForeground(new Color(158, 223, 156));
        quitButton.setBounds(68, Game.GAME_HEIGHT-100, 100, 25);
        registerButton.setBounds( 318, Game.GAME_HEIGHT-100, 100, 25);

        //Labels
        userIDLabel.setBounds(140, 270, 75, 25);
        userPasswordLabel.setBounds(140, 370, 75, 25);
        confirmPasswordLabel.setBounds(140, 470, 75, 25);

        //Fields
        userIDField.setBackground(Color.LIGHT_GRAY);
        userIDField.setForeground(Color.DARK_GRAY);
        userPasswordField.setBackground(Color.LIGHT_GRAY);
        userPasswordField.setForeground(Color.DARK_GRAY);
        confirmPasswordField.setBackground(Color.LIGHT_GRAY);
        confirmPasswordField.setForeground(Color.DARK_GRAY);
        userIDField.setBounds(140, 350, 200, 25);
        userPasswordField.setBounds(140, 450, 200, 25);
        confirmPasswordField.setBounds(140, 550, 200, 25);
    }

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
        quitButton.addActionListener(e -> GameState.state = GameState.QUIT); // Salir del juego
        backButton.addActionListener(e -> {
            game.getGamePanel().removeAll(); // Remover los componentes de la pantalla
            flagAddComponents = false; // Para que cuando vuelva a REGISTER pueda entrar a addComponents
            clearFields(); // Limpiar los fields
            GameState.state = GameState.LOGIN; // Volver al Login
        });
    }

    /** registerUser() ==> Registrar usuario. Se ingresa nombre de usuario y contraseña, se hace su comprobación y,
     * en caso de ser válidos, se los guarda en el archivo. */
    public void registerUser(){
        String name = userIDField.getText(); // Leer nombre
        String password = new String(userPasswordField.getPassword()); // Leer contraseña
        String confirmPassword = new String(confirmPasswordField.getPassword()); // Confirmar contraseña

        try {
            if(name.isBlank() || password.isBlank() || confirmPassword.isBlank()
                    || name.length() >20 || password.length() >20 || confirmPassword.length() >20){ // Si esta vacio o es >20
                throw new InvalidUsernameOrPasswordException();

            } else if (!game.getJsonUserManager().isUsernameAvailable(name)) { // Si el nombre de usuario ya existe
                throw new UsernameUnavailableException();

            } else if (!confirmPassword.equals(password)) { // Si las contraseñas (password y confirmPassword) no coinciden
                throw new PasswordMismatchException();
            }

            User user = new User(name, password);
            game.getJsonUserManager().userToFile(user); // Pasar user al archivo
            game.getGamePanel().removeAll(); // Eliminar componentes de la pantalla
            flagAddComponents = false; // Para que cuando vuelva a REGISTER pueda entrar a addComponents
            GameState.state = GameState.LOGIN; // Cambiar de state
            
        } catch (InvalidUsernameOrPasswordException e){ // Excepcion si name o password esta vacio o con mas de 20 caracteres
            e.getMessage();
            e.printStackTrace();
            showMessage = 1;

        } catch (UsernameUnavailableException e){ // Excepcion si el name ya existe
            e.getMessage();
            e.printStackTrace();
            showMessage = 2;

        } catch (PasswordMismatchException e){ // Excepcion si las contraseñas no coiniciden
            e.getMessage();
            e.printStackTrace();
            showMessage = 3;

        } finally {
            clearFields(); // Borrar lo escrito en los fields
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

        if(showMessage != 0){ // Si showMessage es distinto de 0, entonces se debe mostrar un mensaje
            messageCounter(this); // Llamar contador de mensaje
        }
    }

    @Override
    public void draw(Graphics g) {

        // Fondo y Titulo
        LoadSave.drawTitleBackgroud(g,REGISTER_BACKGROUD);

        // Textos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 25));
        g.drawString("Username", 180, 340);
        g.drawString("Password", 180, 440);
        g.drawString("Confirm Password", 130, 540);

        // Mensajes a mostrar
        if(showMessage != 0){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(40, 250, 410, 25); // Rectangulo Negro
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);
            switch (showMessage){
                case 1 -> g.drawString("Nombre de usuario y/o contraseña inválidos.", 120, 267);
                case 2 -> g.drawString("Nombre de usuario existente.", 150, 267);
                case 3 -> g.drawString("Las contraseñas no coinciden.", 150, 267);
            }
        }
    }
}
