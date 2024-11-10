package gameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Statemethods ==>
 * Interfaz encargada de proporcionar métodos necesarios para los inputs del usuario y el funcioamiento de las clases.
 * Implementada por las clases del paquete gameState.
 */

public interface Statemethods {

    public void mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);

}
