package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Alien1 extends Enemy{

    // ====================> ATRIBUTOS <====================


    // ====================> CONTRUCTOR <====================
    public Alien1(float x, float y) {
        super(x, y, Alien_WIDTH, Alien_HEIGHT, Alien1);
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    // ====================> GET | SET <====================


    // ====================> METODOS <====================
}
