package main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow{

    // Atributos
    private JFrame jframe;

    // Constructor
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.setVisible(true); // Importante de que este al final
        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
            @Override
            public void windowGainedFocus(WindowEvent e) {}
        });
    }

}
