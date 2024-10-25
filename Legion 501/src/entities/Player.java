package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

    // Atributos
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    private boolean left, up, right, down, fire;
    private float playerSpeed = 4.0f;
    private float xDrawOffset = 6 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // Constructor
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
    }

    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width,height,null);
        drawHitbox(g);
    }

    // Moving
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePos() {
        moving = false;
        if(!left && !up && !right && !down) // Esta línea es una optimización, para no hacer
            return;                         // cálculos innecesarios si no hay input del jugador.

        float xSpeed = 0, ySpeed = 0; // Variable temporal de la velocidad

        // Movimiento X
        if (left && !right)
            xSpeed = -playerSpeed;
        else if (right && !left)
            xSpeed = playerSpeed;

        // Disparo (Test)
        if(fire)
            System.out.println("Disparo");

        // Comprobación de Colision
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }
    }

    // Animation
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.Player_ATLAS);

        animations = new BufferedImage[2][2];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
    }

    // Reset Booleanos a False
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    // Getters Setters
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }
}
