package entities;

import static utilz.Constants.ANI_SPEED_PLAYER;
import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.IRenderable;
import utilz.LoadSave;

/**
 * Player ==>
 * Clase que contiene los atributos y métodos necesarios para el funcionamiento del jugador
 * (animación, movimiento, ataque, etc).
 */
public class Player extends Entity implements IRenderable {

    // ====================> ATRIBUTOS <====================
    private BufferedImage[][] animations; // Matriz con animaciones (SpriteSheat)
    private boolean moving = false; // Si el jugador se está moviendo o no
    private boolean left, right; // Direcciones del jugador

    // ====================> CONSTRUCTOR <====================
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
    }

    // ====================> GET | SET <====================
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    // ====================> METODOS <====================
    /** updateAnimationTick() ==> Genera el efecto de animación, utilizando los sprite. */
    private void updateAnimationTick(){
        aniTick++; // Contador de tiempo para la animación
        if(aniTick >= ANI_SPEED_PLAYER){ // Cuando el contador llegue al límite de tiempo (30)
            aniTick = 0;
            aniIndex++; // Indice de qué sprite se va a mostrar
            if(aniIndex >= GetSpriteAmount(state)){ // Si se pasa de la cantidad máxima de sprites...
                aniIndex = 0; // Vuelve al primer sprite
                switch (state) {
                    case EXPLODE -> active = false;
                }
            }
        }
    }

    /** move() ==> Movimiento del jugador. */
    private void move() {
        moving = false;
        if(!left && !right) // Esta línea es una optimización, para no hacer
            return;         // cálculos innecesarios si no hay input del jugador.

        float xSpeed = 0, ySpeed = 0; // Variable temporal de la velocidad del jugador

        // Movimiento
        if (left && !right)
            xSpeed = -speed*3;
        else if (right && !left)
            xSpeed = speed*3;


        // Comprobación de Colision con las Paredes
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height)) {
            x += xSpeed;
            y += ySpeed;
            updateHitbox();
            moving = true;
        }
    }

    /** loadAnimations() ==> Separa el SpriteSheat y los ubica en una matriz. */
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.Player_ATLAS); // Cargar el SpriteSheat

        animations = new BufferedImage[2][11]; // Instanciar el SpriteSheat

        // Ubicar a través de un bucle los diferentes sprites dentro de la matriz
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
    }

    /** resetDirBooleans() ==> Reset Booleanos a False. */
    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    /// Interface IRenderable
    public void update(){
        move();
        updateAnimationTick();
//        setAnimation();
    }

    public void draw(Graphics g){
//        drawHitbox(g); // COMENTAR DESPUES !!!!!!!!!!!!!!!
        if(active){
            g.drawImage(animations[state][aniIndex],
                    (int) (x - xDrawOffset),
                    (int) (y - yDrawOffset),
                    width,
                    height, null);
        }

    }


}
