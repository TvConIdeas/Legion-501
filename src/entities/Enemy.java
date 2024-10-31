package entities;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity{

    // ====================> ATRIBUTOS <====================
    private int enemyType; // Estado del enemigo || Tipo de enemigo

    // ====================> CONSTRUCTOR <====================
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        initHitbox(x,y,width,height);
    }

    // ====================> GET | SET <====================
    public int getAniIndex() {
        return aniIndex;
    }

    // ====================> METODOS <====================
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;
                switch (state) {
                    case DEAD -> active = false;
                }
            }
        }
    }

    public void update(){
        updateAnimationTick();
    }


}
