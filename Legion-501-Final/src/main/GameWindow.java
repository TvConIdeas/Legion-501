package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 *  GameWindow ==>
 *  Clase encargada de crear/settear la ventana.
 *
 *  JFrame es una clase utilizada en Swing para generar ventanas sobre las cuales añadir distintos objetos con los
 *  que podrá interactuar o no el usuario.
 *
 *  A diferencia de JPanel, JFrame posee algunas nociones típicas de una ventana
 *  tales como minimizar, cerrar, maximizar y poder moverla.
 */

public class GameWindow extends JFrame{

    // ====================> CONSTRUCTOR <====================
    public GameWindow(GamePanel gamePanel) {

        setTitle("Legion 501"); // Settea nombre de ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cerrar ventana al oprimir cruz
        add(gamePanel);
        setResizable(false); // Impide modificacion del tamaño de ventana
        pack(); // Le da a la ventana el tamaño dado en GamePanel
        setLocationRelativeTo(null); // Abre la ventana en el centro de la pantalla
        setVisible(true); // Para que sea visible la ventana. Importante de que este vaya al final

        addWindowFocusListener(new WindowFocusListener() { // Detecta cuándo la ventana pierde o gana el foco
                                                           // (es decir, cuándo el usuario interactúa con ella).

            @Override
            public void windowLostFocus(WindowEvent e) { // Cuando la ventana pierde el foco
                gamePanel.getGame().windowFocusLost();
            }
            @Override
            public void windowGainedFocus(WindowEvent e) {} // Method innecesario para el programa
        });
    }
}
