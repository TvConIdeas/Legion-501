package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.EnemyConstants.*;

public class Alien4 extends Enemy {

    // ====================> ATRIBUTOS <====================

    // ====================> CONTRUCTOR <====================
    public Alien4(float x, float y) {
        super(x, y, Alien_WIDTH, Alien_HEIGHT, Alien4);
        lives = 2;
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
        loadImgs();
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    public void draw(Graphics g){
        g.drawImage(
                animations[state][getAniIndex()],
                (int)(x - xDrawOffset),
                (int)(y - yDrawOffset),
                Alien_WIDTH,
                Alien_HEIGHT,
                null);
    }

    /** loadImgs() ==> Separa el SpriteSheat y los ubica en una matriz. */
    private void loadImgs() {
        animations = new BufferedImage[2][5];
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.Alien4_ATLAS);
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = temp.getSubimage(i * Alien_WIDHT_DEFAULT, j * Alien_HEIGHT_DEFAULT, Alien_WIDHT_DEFAULT, Alien_HEIGHT_DEFAULT);
    }
}
