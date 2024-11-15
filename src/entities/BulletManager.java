package entities;

import gameState.Playing;
import main.Game;
import utilz.IRenderable;

import java.awt.*;
import java.util.ArrayList;

import static utilz.HelpMethods.DetectCollision;

public class BulletManager implements IRenderable {
    // ====================> ATRIBUTOS <====================
    private Playing playing;
    public ArrayList<Bullet> bulletPlayerArr; // Arraylist con las balas
    public ArrayList<Bullet> bulletAlienArr; // Arraylist con las balas
    private float bulletSpeed = 5.0f; // Velocidad de la bala
    private boolean updatingBullets = false;

    // ====================> CONSTRUCTOR <====================
    public BulletManager(Playing playing) {
        this.playing = playing;
        bulletPlayerArr = new ArrayList<>();
        bulletAlienArr = new ArrayList<>();
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    /** move() ==> Movimiento de la bala y verificacion de colisiones */
    public void move() {

        /// Balas Jugador ====================================>
        for (int i = 0; i < bulletPlayerArr.size(); i++) { // Bucle para detectar todas las balas
            Bullet bullet = bulletPlayerArr.get(i);
            bullet.getHitbox().y -= bulletSpeed; // Movimiento hacia arriba

            // Detecta colision Bala con Enemigos (Balas Jugador)
            for (int j = 0; j < playing.enemyManager.getEnemies().size(); j++) {
                Enemy alien = (Enemy) playing.enemyManager.getEnemies().get(j);

                if (!bullet.active && alien.active && DetectCollision(alien, bullet)) {
                    playing.enemyManager.hitEnemy(alien);
                    bullet.active = true; // Desvanece la bala
                }
            }

            // Remueve las balas que lleguen al limite
            while (!bulletPlayerArr.isEmpty() && (bulletPlayerArr.getFirst().active || bulletPlayerArr.getFirst().y < 0)) {
                bulletPlayerArr.removeFirst();
            }
        }

        /// Balas Enemigos ====================================>
        for (int i = 0; i < bulletAlienArr.size(); i++) { // Bucle para detectar todas las balas
            Bullet bullet = bulletAlienArr.get(i);
            if(bullet.isEnemyBullet){
                bullet.getHitbox().y += bulletSpeed; // Movimiento hacia arriba
            }

            if(!bullet.active && DetectCollision(playing.getPlayer(), bullet)){
                playing.hitPlayer = true;
            }

            // Remueve las balas que lleguen al limite
            while (!bulletAlienArr.isEmpty() && (bulletAlienArr.getFirst().active || bulletAlienArr.getFirst().y < 0)) {
                bulletAlienArr.removeFirst();
            }
        }
    }

    /** createBullet() ==> Crea una bala en el jugador */
    public void createBullet() {
        Bullet bullet = new Bullet(
                playing.getPlayer().x + (float) Game.TILES_SIZE / 2 - (float) (Game.TILES_SIZE / 8)*2,
                playing.getPlayer().y,
                Game.TILES_SIZE / 8,
                Game.TILES_SIZE / 2);
        bulletPlayerArr.add(bullet);
    }

    /** createBulletAlien() ==> Crea una bala en el Enemigo */
    public <T extends Enemy> void createBulletAlien(T alien) {
        Bullet bullet = new Bullet(
                alien.x + (float) Game.TILES_SIZE / 2 - (float) (Game.TILES_SIZE / 8)*2,
                alien.y + (float) Game.TILES_SIZE,
                Game.TILES_SIZE / 8,
                Game.TILES_SIZE / 2);
        bullet.isEnemyBullet = true;
        bulletAlienArr.add(bullet);
    }

    /** clearBullets() ==> Limpia las balas en pantalla */
    public void clearBullets() {
        updatingBullets = true; // Activar flag de pausa
        bulletPlayerArr.clear();
        bulletAlienArr.clear();
        updatingBullets = false; // Desactivar flag al terminar
    }

    /** <====== Interface IRenderable ======> */
    @Override
    public void update(){
        if (!updatingBullets) {
            move();
        }
    }

    @Override
    public void draw(Graphics g){
        // Balas Jugadador
        g.setColor(Color.yellow);
        for (int i = 0; i < bulletPlayerArr.size(); i++) {
            Bullet bullet = bulletPlayerArr.get(i);
            if (!bullet.active) {
                g.fillRect((int)bullet.getHitbox().x, (int)bullet.getHitbox().y, bullet.width, bullet.height);
            }
        }

        // Balas Aliens
        g.setColor(Color.red);
        for (int i = 0; i < bulletAlienArr.size(); i++) {
            Bullet bullet = bulletAlienArr.get(i);
            if (!bullet.active) {
                g.fillRect((int)bullet.getHitbox().x, (int)bullet.getHitbox().y, bullet.width, bullet.height);
            }
        }
    }
}
