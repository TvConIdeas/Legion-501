package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.NonexistentUserException;
import main.Game;
import static utilz.LoadSave.*;
import utilz.LoadSave;
import users.User;

import javax.swing.*;
import java.awt.*;

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

        backButton.setText("Register"); // Cambiar etiqueta de boton de "Back" a "Register"

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
        quitButton.addActionListener(e -> GameState.state = GameState.QUIT); // Salir del juego
        backButton.addActionListener(e ->{ // Register button
            game.getGamePanel().removeAll(); // Remover los componentes de la pantalla
            flagAddComponents = false; // Para que cuando vuelva a LOGIN pueda entrar a addComponents
            clearFields(); // Limpiar los fields
            GameState.state = GameState.REGISTER; // Ir a Register
        });
    }

    /** login() ==> Loguear usuario. Se realiza la comprobación del nombre de usuario y su contraseña.
     * Si el nombre de usuario y la contraseña coinciden con los datos almacenados,
     * el usuario se autentica correctamente y se le da acceso al menú del juego. */
    public void login(){
        String name = userIDField.getText(); // Leer nombre de usuario
        String password = new String(userPasswordField.getPassword()); // Leer contraseña

        try {
            if(name.isBlank() || password.isBlank() || name.length() >20 || password.length() >20) { // Si esta vacio o es >20
                throw new InvalidUsernameOrPasswordException();

            } else if(!game.getJsonUserManager().verifyUserInfo(new User(name, password))){ // Si el nombre y/o contraseña no son correctos
                throw new NonexistentUserException();
            }

            game.getGamePanel().removeAll(); // Borrar componentes de pantalla
            flagAddComponents = false; // Para que cuando vuelva a REGISTER pueda entrar a addComponents
            game.setUserInGame(game.getJsonUserManager().getUser(name)); // Asigna al jugador activo en el juego según su nombre
            GameState.state = GameState.MENU; // Ir a menú

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

        if(showMessage != 0){ // Si showMessage es distinto de 0, entonces se debe mostrar un mensaje
            messageCounter(this); // Llamar contador de mensaje
        }
    }

    @Override
    public void draw(Graphics g) {
        // Fondo y Titulo
        LoadSave.drawTitleBackgroud(g,LOGIN_BACKGROUD);

        // Mensajes de error
        if(showMessage != 0){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(40, 250, 410, 25); // Rectangulo Negro
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);
            
            switch (showMessage) {
                case 1 -> g.drawString("Nombre de usuario y/o contraseña inválidos.", 120, 267);
                case 2 -> g.drawString("Nombre de usuario y/o contraseña incorrectos.", 120, 267);
            }
        }
        
        // Textos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 25));
        g.drawString("Username", 180, 340);
        g.drawString("Password", 180, 440);
    }
}

