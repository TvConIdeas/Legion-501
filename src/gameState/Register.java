package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.UsernameUnavailableException;
import main.Game;
import main.GamePanel;
import users.User;

import javax.swing.*;
import java.awt.*;

import static utilz.Constants.ANI_ERROR_MESSAGE;

public class Register extends State {

    // ====================> ATRIBUTOS <====================
    // Botones
    private JButton registerButton;
    private JButton quitButton;
    private JButton backButton;

    private JLabel userIDLabel;
    private JLabel userPasswordLabel;
    private JTextField userIDField;
    private JPasswordField userPasswordField;

    private String name;
    private String password;

    boolean flagAddComponents = false; // Flag para agregar los componentes una única vez
    boolean showMessage = false;

    private int aniTick = 0;


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
        userIDLabel = new JLabel("User name:");
        userPasswordLabel = new JLabel("Password:");
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();

        // Limites
        registerButton.setBounds(Game.GAME_WIDTH-150, Game.GAME_HEIGHT-100, 100, 25);
        quitButton.setBounds(20, Game.GAME_HEIGHT-100, 100, 25);
        backButton.setBounds(20, Game.GAME_HEIGHT-150, 100, 25);
        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);
        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);
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
            game.getJsonUserManager().userToFile(user);
            game.getGamePanel().removeAll();
            flagAddComponents = false;
            GameState.state = GameState.LOGIN;
            
        } catch (InvalidUsernameOrPasswordException e){
            System.out.println(game.getJsonUserManager().fileToUsers());
            e.getMessage();
            e.printStackTrace();
            showMessage = true;

        } catch (UsernameUnavailableException e){
            e.getMessage();
            e.printStackTrace();
            showMessage = true;

        } finally {
            clearFields();
        }
    }

    public void clearFields(){
        userIDField.setText("");
        userPasswordField.setText("");
    }

    // Methods interfaz IRenderable
    @Override
    public void update() {

        if(!flagAddComponents) {
            GamePanel panel = game.getGamePanel();

            if(panel != null){
                addComponents(panel);
                flagAddComponents = true;

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
        if(showMessage){
            g.fillRect(125, 200, 200, 50);
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);
            g.drawString("El nombre de usuario y/o contraseña no cumplen con las condiciones.", 150, 225);
        }
    }
}
