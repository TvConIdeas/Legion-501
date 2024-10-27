package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

/**
 * Player ==>
 * Clase que contiene los atributos y métodos necesarios para el funcionamiento del jugador
 * (animación, movimiento, ataque, etc).
 */

public class Player extends Entity {

    // ====================> ATRIBUTOS <====================
    private BufferedImage[][] animations; // Matriz con animaciones (SpriteSheat)
    private int aniTick, aniIndex, aniSpeed = 10; // Variables para animación
    private int playerAction = IDLE; // Estado del jugador, por defecto IDLE (inactivo)
    private boolean moving = false; // Si el jugador se está moviendo o no
    private boolean left, right; // Direcciones del jugador
    private float  playerSpeed= 4.0f; // CAMIBAR A ENTITY
    private float xDrawOffset = 6 * Game.SCALE; // Centraliza la hitbox en el jugador (ancho)
    private float yDrawOffset = 4 * Game.SCALE; // Centraliza la hitbox en el jugador (largo)

    // ====================> CONSTRUCTOR <====================
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
    }

    // ====================> SET/GET <====================
    // Moving, VER
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
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void draw(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset),
                (int)(hitbox.y - yDrawOffset), width,height,null);
//        drawHitbox(g); // COMENTAR DESPUES !!!!!!!!!!!!!!!
    }

    /** updateAnimationTick() ==> Genera el efecto de animación, utilizando los sprite. */
    private void updateAnimationTick(){
        aniTick++; // Contador de tiempo para la animación
        if(aniTick >= aniSpeed){ // Cuando el contador llegue al límite de tiempo (30)
            aniTick = 0;
            aniIndex++; // Indice de qué sprite se va a mostrar
            if(aniIndex >= GetSpriteAmount(playerAction)){ // Si se pasa de la cantidad máxima de sprites...
                aniIndex = 0; // Vuelve al primer sprite
            }
        }
    }

    /** setAnimation() ==> Settea el estado del jugador. */
    private void setAnimation() {
        if (moving) // En caso de estar en movimiento
            playerAction = RUNNING;
        else // En caso de estar inactivo
            playerAction = IDLE;
    }

    private void updatePos() {
        moving = false;

        if(!left && !right) // Esta línea es una optimización, para no hacer
            return;         // cálculos innecesarios si no hay input del jugador.

        float xSpeed = 0, ySpeed = 0; // Variable temporal de la velocidad del jugador

        // Movimiento
        if (left && !right)
            xSpeed = -playerSpeed;
        else if (right && !left)
            xSpeed = playerSpeed;


        // Comprobación de Colision
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }
    }

    /** loadAnimations() ==> Separa el SpriteSheat y los ubica en una matriz. */
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.Player_ATLAS); // Cargar el SpriteSheat

        animations = new BufferedImage[3][7]; // Instanciar el SpriteSheat

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
}
