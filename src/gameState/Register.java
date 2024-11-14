package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.UsernameUnavailableException;
import main.Game;
import main.GamePanel;
import users.User;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.ANI_ERROR_MESSAGE;
import static utilz.LoadSave.*;

public class Register extends State {

    // ====================> ATRIBUTOS <====================
    // Botones
    private JButton registerButton;
    private JButton quitButton;
    private JButton backButton;

    // Labels
    private JLabel userIDLabel;
    private JLabel userPasswordLabel;

    // Fields
    private JTextField userIDField;
    private JPasswordField userPasswordField;

    // Atributos a ingresar
    private String name;
    private String password;

    // Flags
    boolean flagAddComponents = false; // Flag para agregar los componentes una única vez
    boolean showMessage = false; // Mostrar mensaje en pantalla al lanzar excepcion
    private int aniTick = 0; // Contador para mostrar mensaje al lanzar excepcion


    // ====================> CONTRUCTOR <====================
    public Register(Game game) {
        super(game);
        initUI();
        addEventListeners();
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================

    /** initUI ==> Instanciar los componentes. */
    public void initUI(){
        // Instanciar
        registerButton = new JButton("Register");
        quitButton = new JButton("Quit");
        backButton = new JButton("Back");
        userIDLabel = new JLabel("");
        userPasswordLabel = new JLabel("");
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();

        // Limites
        // Botones
        quitButton.setBackground(new Color(116, 9, 56));
        quitButton.setForeground(new Color(222, 124, 125));
        backButton.setBackground(new Color(0, 11, 88));
        backButton.setForeground(new Color(106, 154, 176));
        registerButton.setBackground(new Color(82, 110, 72));
        registerButton.setForeground(new Color(158, 223, 156));
        quitButton.setBounds(68, Game.GAME_HEIGHT-100, 100, 25);
        backButton.setBounds(193, Game.GAME_HEIGHT-100, 100, 25);
        registerButton.setBounds( 318, Game.GAME_HEIGHT-100, 100, 25);

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

    /** addComponents() ==> Agregar los componentes al GamePanel. */
    public void addComponents(GamePanel panel){
        panel.setLayout(null);
        panel.add(registerButton);
        panel.add(quitButton);
        panel.add(backButton);
        panel.add(userIDLabel);
        panel.add(userPasswordLabel);
        panel.add(userIDField);
        panel.add(userPasswordField);
    }

    /** addEventListeners() ==> Settear los botones para que hagan una acción al ser oprimidos. */
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
        name = userIDField.getText(); // Leer nombre
        password = new String(userPasswordField.getPassword()); // Leer contraseña

        try {
            if(name.isBlank() || password.isBlank() || name.length() >20 || password.length() >20){ // Si esta vacio o es >20
                throw new InvalidUsernameOrPasswordException();

            } else if (!game.getJsonUserManager().isUsernameAvailable(name)) { // Si el nombre de usuario ya existe
                throw new UsernameUnavailableException();
            }

            User user = new User(name, password);
            game.getJsonUserManager().userToFile(user); // Pasar user al archivo
            game.getGamePanel().removeAll(); // Eliminar componentes de la pantalla
            flagAddComponents = false; // Para que cuando vuelva a REGISTER pueda entrar a addComponents
            GameState.state = GameState.LOGIN; // Cambiar de state
            
        } catch (InvalidUsernameOrPasswordException e){ // Excepcion si name o password esta vacio y mas de 20 caracteres
            System.out.println(game.getJsonUserManager().fileToUsers());
            e.getMessage();
            e.printStackTrace();
            showMessage = true;

        } catch (UsernameUnavailableException e){ // Excepcion si el name ya existe
            e.getMessage();
            e.printStackTrace();
            showMessage = true;

        } finally {
            clearFields();
        }
    }

    /** clearFields() ==> Borrar los contenidos de los fields. */
    public void clearFields(){
        userIDField.setText("");
        userPasswordField.setText("");
    }

    // Methods interfaz IRenderable
    @Override
    public void update() {
        if(!flagAddComponents) { // Entrar una unica vez
            GamePanel panel = game.getGamePanel();

            if(panel != null){
                addComponents(panel);
                flagAddComponents = true; // Para que no se agreguen de nuevo
            }
        }

        if(showMessage){ // Si se mostró el mensaje de error
            aniTick++;
            if(aniTick >= ANI_ERROR_MESSAGE){ // Mostrar hasta que se cumpla un tiempo determinado
                aniTick = 0; // Fin Contador
                showMessage = false;
            }
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

        // Encaso de Fallar
        if(showMessage){
            g.setColor(Color.BLACK);
            g.fillRect(40, 250, 410, 25); // Rectangulo Negro
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);
            g.drawString("El nombre de usuario y/o contraseña no cumplen con las condiciones.", 48, 267);
        }
    }
}
