package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.NonexistentUserException;
import exceptions.UsernameUnavailableException;
import main.Game;
import users.User;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;

import static utilz.LoadSave.OPTION_BACKGROUD;

public class Option extends UserAccount {
    // ====================> ATRIBUTOS <====================
    // Botones
    JButton confirmUsernameButton;
    JButton confirmPasswordButton;

    // Contenedores
    JTextField userIDField;
    JPasswordField userPasswordField;

    // Texto
    JLabel userIDLabel;
    JLabel userPasswordLabel;

    // ====================> CONSTRUCTOR <====================
    public Option(Game game) {
        super(game);
        initUI();
        addEventListeners();
    }

    // ====================> SET/GET <====================

    // ====================> METODOS <====================
    @Override
    public void initUI(){
        super.initUI();
        // Instanciar
        confirmUsernameButton = new JButton("Confirm");
        confirmPasswordButton = new JButton("Confirm");
        userIDField = new JTextField("");
        userPasswordField = new JPasswordField();
        userIDLabel = new JLabel("");
        userPasswordLabel = new JLabel("");

        backButton.setText("To Menu");

        // Botones
        confirmUsernameButton.setBackground(new Color(82, 110, 72));
        confirmUsernameButton.setForeground(new Color(158, 223, 156));
        confirmUsernameButton.setBounds(193, 330, 100, 25);

        confirmPasswordButton.setBackground(new Color(82, 110, 72));
        confirmPasswordButton.setForeground(new Color(158, 223, 156));
        confirmPasswordButton.setBounds(193, 450 , 100, 25);

        //Labels
        userIDLabel.setBounds(140, 270, 75, 25);
        userPasswordLabel.setBounds(140, 370, 75, 25);

        //Fields
        userIDField.setBackground(Color.LIGHT_GRAY);
        userIDField.setForeground(Color.DARK_GRAY);
        userPasswordField.setBackground(Color.LIGHT_GRAY);
        userPasswordField.setForeground(Color.DARK_GRAY);
        userIDField.setBounds(140, 300, 200, 25);
        userPasswordField.setBounds(140, 420, 200, 25);
    }

    @Override
    public void addComponents(){
        super.addComponents();
        panel.setLayout(null);
        panel.add(confirmUsernameButton);
        panel.add(confirmPasswordButton);
        panel.add(userIDField);
        panel.add(userPasswordField);
        panel.add(userIDLabel);
        panel.add(userPasswordLabel);
    }

    @Override
    public void addEventListeners(){
        confirmUsernameButton.addActionListener(e -> changeUsername());
//        confirmPasswordButton.addActionListener(e -> changePassword());

        backButton.addActionListener(e ->{ // Register button
            game.getGamePanel().removeAll();
            flagAddComponents = false;
            clearFields();
            GameState.state = GameState.MENU;
        });
    }

    public void changeUsername(){
        String newName = userIDField.getText();
        
        try {
            if(newName.isBlank() || newName.length() >20){
                throw new InvalidUsernameOrPasswordException("Nombre de usuario inválido.");
                
            } else if (!game.getJsonUserManager().isUsernameAvailable(newName)) {
                throw new UsernameUnavailableException();
            }

            String oldName = game.getUserInGame().getName();
            System.out.println(game.getUserInGame());
            game.getUserInGame().setName(newName);
            System.out.println(game.getUserInGame());
            game.getJsonUserManager().overwriteUserName(game.getUserInGame(), oldName);
            showMessage = 3;

        } catch (InvalidUsernameOrPasswordException e){
            e.getMessage();
            e.printStackTrace();
            showMessage = 1;

        } catch (UsernameUnavailableException e){
            e.getMessage();
            e.printStackTrace();
            showMessage = 2;
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
        LoadSave.drawTitleBackgroud(g,OPTION_BACKGROUD);

        if(showMessage != 0){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(40, 550, 410, 25); // Rectangulo Negro
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);

            switch (showMessage) {
                case 1 -> g.drawString("Nombre de usuario inválido.", 120, 567);
                case 2 -> g.drawString("Nombre de usuario existente.", 120, 567);
                case 3 -> {
                    g.setColor(Color.GREEN);
                    g.drawString("Nombre de usuario modificado con éxito.", 120, 567);
                }
            }
        }

        // Textos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Console", Font.BOLD, 25));
        g.drawString("New Username", 150, 290);
        g.drawString("New Password", 150, 410);
    }
}
