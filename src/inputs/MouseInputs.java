package inputs;

import gameState.GameState;
import main.GamePanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * MouseInputs ==>
 * Esta clase nos permitará detectar el movimiento y las acciones del mouse.
 * Para lograr esto, implementamos las interfaces MouseListener y MouseMotionListener.
 */
public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state){
            case MENU -> gamePanel.getGame().getMenu().mouseClicked(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
