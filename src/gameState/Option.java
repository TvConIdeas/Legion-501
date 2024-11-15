package gameState;

import exceptions.InvalidUsernameOrPasswordException;
import exceptions.SamePasswordException;
import exceptions.UsernameUnavailableException;
import main.Game;
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
        confirmUsernameButton.addActionListener(e -> changeUsername()); // Cambiar nombre de usuario
        confirmPasswordButton.addActionListener(e -> changePassword()); // Cambiar contraseña

        backButton.addActionListener(e ->{ // Register button
            game.getGamePanel().removeAll(); // Remover los componentes de la pantalla
            flagAddComponents = false; // Para que cuando vuelva a OPTIONS pueda entrar a addComponents
            clearFields(); // Limpiar los fields
            GameState.state = GameState.MENU; // Ir a menú
        });
    }

    /** changeUsername() ==> Método para cambiar nombre de usuario. Se comprueba que sea válido e inexistente
     * y se lo guarda en el archivo en caso de cumplir con las validaciones.*/
    public void changeUsername(){
        String newName = userIDField.getText(); // Leer nombre de usuario nuevo
        
        try {
            if(newName.isBlank() || newName.length() >20){ // En caso de estar vacío o ser >20
                throw new InvalidUsernameOrPasswordException("Nombre de usuario inválido.");
                
            } else if (!game.getJsonUserManager().isUsernameAvailable(newName)) { // En caso que el nombre no esté disponible
                throw new UsernameUnavailableException();
            }

            String oldName = game.getUserInGame().getName(); // Guardar nombre anterior
            game.getUserInGame().setName(newName); // Modificar nombre en UserInGame
            game.getJsonUserManager().overwriteUserName(game.getUserInGame(), oldName); // Guardar cambios en archivo
            showMessage = 3; // Indice de mensaje de confirmación

        } catch (InvalidUsernameOrPasswordException e){ // Excepción en caso de nombre invalido
            e.getMessage();
            e.printStackTrace();
            showMessage = 1;

        } catch (UsernameUnavailableException e){ // Excepcion en caso de nombre existente en archivo
            e.getMessage();
            e.printStackTrace();
            showMessage = 2;

        } finally {
            clearFields();
        }
    }

    /** changePassword() ==> Método para cambiar contraseña. Se comprueba que sea válida y diferente a la actual
     * y se la guarda en el archivo en caso de cumplir con las validaciones. */
    public void changePassword(){
        String newPassword = new String(userPasswordField.getPassword()); // Leer contraseña nueva

        try {
            if(newPassword.isBlank() || newPassword.length() >20){ // En caso de estar vacía o >20
                throw new InvalidUsernameOrPasswordException("Contraseña inválida.");

            } else if (game.getUserInGame().getPassword().equals(newPassword)) { // En caso que sea la misma que la actual
                throw new SamePasswordException();
            }

            game.getUserInGame().setPassword(newPassword); // Modificar password de user
            game.getJsonUserManager().overwriteUser(game.getUserInGame()); // Guardar cambios en archivo
            showMessage = 6; // Indice de mensaje de confirmacion

        } catch (InvalidUsernameOrPasswordException e){ // Excepcion de contraseña invalida
            e.getMessage();
            e.printStackTrace();
            showMessage = 4;

        } catch (SamePasswordException e){ // Excepcion de misma contraseña que la actual
            e.getMessage();
            e.printStackTrace();
            showMessage = 5;

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
        LoadSave.drawTitleBackgroud(g,OPTION_BACKGROUD);

        if(showMessage != 0){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(40, 550, 410, 25); // Rectangulo Negro
            g.setFont(new Font("Console", Font.BOLD, 12));
            g.setColor(Color.RED);

            // Mensajes de error o confirmación
            switch (showMessage) {
                case 1 -> g.drawString("Nombre de usuario inválido.", 120, 567);
                case 2 -> g.drawString("Nombre de usuario existente.", 120, 567);
                case 3 -> {
                    g.setColor(Color.GREEN);
                    g.drawString("Nombre de usuario modificado con éxito.", 120, 567);
                }
                case 4 -> g.drawString("Contraseña inválida.", 120, 567);
                case 5 -> g.drawString("La contraseña nueva debe ser diferente a la actual.", 120, 567);
                case 6 -> {
                    g.setColor(Color.GREEN);
                    g.drawString("Contraseña modificada con éxito.", 120, 567);
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
