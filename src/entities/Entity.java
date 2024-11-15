package entities;

import main.Game;
import utilz.IRenderable;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * Entity ==>
 * Clase padre abstracta de todas las entidades del juego.
 */

public abstract class Entity {

    // ====================> ATRIBUTOS <====================
    protected float x,y; // Posiciones
    protected int width, height; // Ancho y Largo
    protected int aniTick, aniIndex; // Variables para animación
    protected int state; // Estado
    protected Rectangle2D.Float hitbox; // Rectangulo de Colision
    protected float speed= 1.0f; // Velocidad
    protected boolean active = true; // Activo o Inactivo

    protected float xDrawOffset = 6 * Game.SCALE; // Centraliza la hitbox en el jugador (ancho)
    protected float yDrawOffset = 4 * Game.SCALE; // Centraliza la hitbox en el jugador (largo)

    // ====================> CONSTRUCTOR <====================
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // ====================> GET | SET <====================
    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getState() {
        return state;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    // ====================> METODOS <====================
    /** drawHitbox() ==> Metodo Auxiliar para visualizar la hitbox */
    protected void drawHitbox(Graphics g) {
        // Unicamente sirve para ver la Hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /** initHitbox() ==> Instancia la hitbox. */
    public void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    /** updateHitbox() ==> Actualiza la posición de la hitbox. */
    protected void updateHitbox() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    /** newState() ==> Actualiza el estado y resetea los tick de la animacion */
    public void newState(int state){
        this.state = state;
        aniIndex = 0;
        aniTick = 0;
    }

    /** disableHitbox() ==> Actualiza la hitbox, por vacio */
    public void disableHitbox(){
        hitbox = new Rectangle2D.Float(0, 0, 0, 0);
    }

}
