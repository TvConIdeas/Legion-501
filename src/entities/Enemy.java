package entities;

import utilz.IRenderable;

import java.awt.image.BufferedImage;

import static utilz.Constants.ANI_SPEED_ALIEN;
import static utilz.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity implements IRenderable {
    // ====================> ATRIBUTOS <====================
    private int enemyType; // Tipo de enemigo
    protected BufferedImage[][] animations; // Matriz con Animacion
    protected int lives; // Vidas del Enemigo

    protected boolean attack = false; // Capacidad de disparar

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
        if (aniTick >= ANI_SPEED_ALIEN) {
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

    @Override
    public void update(){
        updateAnimationTick();
    }
}
