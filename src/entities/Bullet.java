package entities;
import gameState.Playing;
import main.Game;

import java.util.ArrayList;

public class Bullet extends Entity {
    // ====================> ATRIBUTOS <====================

    // ====================> CONSTRUCTOR <====================

    public Bullet(float x, float y, int width, int height) {
        super(x, y, width, height);
        active = false;
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================

}
