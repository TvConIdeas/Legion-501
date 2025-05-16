package gameState;

import json.JSONUserManager;
import main.Game;
import ui.ClassButton;
import users.User;
import utilz.LoadSave;

import java.awt.*;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class Ranking extends State implements Statemethods{
    // ====================> ATRIBUTOS <====================
    private ClassButton back;
    private ArrayList<User> usuarios;

    // ====================> CONSTRUCTOR <====================
    public Ranking(Game game) {
        super(game);
        back = new ClassButton(Game.GAME_WIDTH- 80,Game.GAME_HEIGHT- 120,1);
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    private void verifyRanking() {
        boolean flag = false;
        if(!flag){
            JSONUserManager userManager = new JSONUserManager();
            usuarios = new ArrayList<>(userManager.fileToUsers()); // Pasa el archivo a una lista
            usuarios.sort((u1, u2) -> Integer.compare(u2.getBestScore(), u1.getBestScore()));
            flag = true;
        }
    }


    @Override
    public void update() {
        verifyRanking();
        back.update();
    }

    @Override
    public void draw(Graphics g) {
        // Dibuja fondo y Interfaz
        BufferedImage pincel = LoadSave.GetSpritesAtlas(LoadSave.RANKING_BACKGROUD);
        g.drawImage(pincel, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // Dibuja los nombres de los Ranking
        g.setFont(new Font("Console", Font.BOLD, 20));
        int y = 180;
        for (int i = 0; i < usuarios.size() && i < 5; i++) {
            User u = usuarios.get(i);
            g.setColor(Color.WHITE);
            g.drawString(u.getName(), 230, y);
            g.setColor(Color.YELLOW);
            g.drawString(String.valueOf(u.getBestScore()), 380, y);
            y += 88;
        }

        // Dibuja la informacion del jugador
        g.setColor(Color.WHITE);
        g.drawString(game.getUserInGame().getName(), 200, 630);
        g.setColor(Color.YELLOW);
        g.drawString(String.valueOf(game.getUserInGame().getBestScore()), 200, 660);

        back.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e, back)) {
            back.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, back)) {
            if (back.isMousePressed()) {
                GameState.state = GameState.MENU;
            }
        }
        back.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        back.setMouseOver(false);
        if (isIn(e, back)) {
            back.setMouseOver(true); // Comprueba si el mouse está dentro del botón
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
