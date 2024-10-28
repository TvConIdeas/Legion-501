package entities;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * Entity ==>
 * Clase padre abstracta de todas las entidades del juego.
 */

public abstract class Entity {

    // ====================> ATRIBUTOS <====================
    protected float x,y;
    protected int width, height;
    protected int aniTick, aniIndex; // Variables para animación
    protected int state;
    protected Rectangle2D.Float hitbox;
    protected float speed= 1.0f; // CAMIBAR A ENTITY
    protected boolean active = true;

    // ====================> CONSTRUCTOR <====================
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // ====================> GET/SET <====================
    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    // ====================> METODOS <====================
    //COMENTAR DESPUES!!!!!!!!!!!
    protected void drawHitbox(Graphics g) {
        // Unicamente sirve para ver la Hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /** initHitbox() ==> Instancia la hitbox. */
    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    /** updateHitbox() ==> Actualiza la posición de la hitbox. */
    protected void updateHitbox() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }
}
