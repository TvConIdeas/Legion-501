package entities;
import main.Game;

public class Bullet extends Entity {
    // ====================> ATRIBUTOS <====================
    public boolean isEnemyBullet = false;

    // ====================> CONSTRUCTOR <====================

    public Bullet(float x, float y, int width, int height) {
        super(x, y, width, height);
        active = false;
        initHitbox(x, y, Game.TILES_SIZE / 8, Game.TILES_SIZE / 8);
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================

}
