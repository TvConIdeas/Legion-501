package entities;

import static utilz.Constants.EnemyConstants.*;

public abstract class Enemy  extends Entity{

    // ====================> ATRIBUTOS <====================
    private int enemyState, enemyType; // Estado del enemigo || Tipo de enemigo
    private int aniIndex, aniTick, aniSpeed = 25; // Posiblemente pasar a Entity


    // ====================> CONSTRUCTOR <====================
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        initHitbox(x,y,width,height);
    }

    // ====================> GETTER|SETTERS <====================
    public int getEnemyState() {
        return enemyState;
    }
    public int getAniIndex() {
        return aniIndex;
    }

    // ====================> METODOS <====================
    private void updateAnimationTick(){
        aniTick++; // Contador de tiempo para la animación
        if(aniTick >= aniSpeed){ // Cuando el contador llegue al límite de tiempo (30)
            aniTick = 0;
            aniIndex++; // Indice de qué sprite se va a mostrar
            if(aniIndex >= GetSpriteAmount(enemyType,enemyState)){ // Si se pasa de la cantidad máxima de sprites...
                aniIndex = 0; // Vuelve al primer sprite
            }
        }
    }

    public void update(){
        updateAnimationTick();
    }


}
