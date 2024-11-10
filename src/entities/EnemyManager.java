package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import gameState.Playing;
import main.Game;
import utilz.LoadSave;

import static main.Game.GAME_WIDTH;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.DetectCollision;

public class EnemyManager <T extends Enemy> {

    // ====================> ATRIBUTOS <====================
    private Playing playing; // Traemos el State Playing

    private ArrayList<T> enemies = new ArrayList<>(); // ArrayList con los aliens, (revisar, cambiar a Enemy)
    private int alienRows = 5; // Cantidad de Filas de aliens
    private int alienColumns = 5; // Cantidad de Columnas de aliens
    private int alienCount = 0; // Numero de Aliens a vencer
    private float alienVelocityX = 0.05f; // Velocidad de los aliens (Revisar)

    // ====================> CONSTRUCTOR <====================
    public EnemyManager(Playing playing) {
        this.playing = playing;
        createAliens();
    }

    // ====================> GET | SET <====================
    public int getAlienCount() {
        return alienCount;
    }

    public ArrayList<T> getEnemies() {
        return enemies;
    }

    // ====================> METODOS <====================
    public void update(){
        for(T alien : enemies){
            alien.update(); // Revisar
            move();

        }
    }

    public void draw(Graphics g){ // cambiar !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(T alien : enemies){
            if(alien.active){
                alien.drawHitbox(g);
                alien.draw(g);
            }
        }
    }

    /** move() ==> Se encarga de mover la ubicacion de los aliens1. */
    public void move(){
        for (int i = 0; i < enemies.size(); i++) {
            T alien = enemies.get(i);
            if (alien.active) { // Si el Alien esta vivo
                alien.hitbox.x += alienVelocityX;

                // SI el alien toca con su Hitbox.X las paredes
                if (alien.hitbox.x + alien.width >= GAME_WIDTH || alien.hitbox.x <= 0) {
                    alienVelocityX *= -1;
                    alien.hitbox.x += alienVelocityX * 2; // eeeee revisar

                    // Movemos todos los aliens una fila con su Hitbox.Y
                    for (int j = 0; j < enemies.size(); j++) {
                        enemies.get(j).hitbox.y += Alien_HEIGHT;
                    }
                }

                if (DetectCollision(alien, playing.getPlayer())) {
                    System.out.println("Colision Jugador");
                    playing.getPlayer().newState(DEAD);
                }
            }
        }
    }

    /** createAliens() ==> Ubica los aliens según las filas y columnas y los agrega al
     * ArrayList. */
    public void createAliens() {
        int cantAlien1 = 15;
        int cantAlien2 = 10;
        T alien = null;

        for (int i = 0; i < alienColumns; i++) {
            for (int j = 0; j < alienRows; j++) {
                if(cantAlien1 > 0){
                    alien = (T) new Alien1(
                            Game.TILES_SIZE + i * Alien_WIDTH,
                            Game.TILES_SIZE + j * Alien_HEIGHT);
                    cantAlien1--;
                } else if (cantAlien2 > 0) {
                    alien = (T) new Alien2(
                            Game.TILES_SIZE + i * Alien_WIDTH,
                            Game.TILES_SIZE + j * Alien_HEIGHT);
                    cantAlien2--;
                }
                enemies.add(alien); // Lo agregamos al ArrayList
            }
        }

//        enemies.add((T) new Alien1(100, 200));
//        enemies.add((T) new Alien2(150, 200));

        alienCount = enemies.size(); // Lo agregamos al contador
    }
}
