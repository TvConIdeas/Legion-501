package entities;

import gameState.Playing;
import main.Game;

import java.awt.*;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.HelpMethods.DetectCollision;

public class BulletManager {
    // ====================> ATRIBUTOS <====================
    private Playing playing;
    private ArrayList<Bullet> bulletArr = new ArrayList<>(); // Arraylist con las balas
    private float bulletSpeed = 5.0f; // Velocidad de la bala

    // ====================> CONSTRUCTOR <====================

    public BulletManager(Playing playing) {
        this.playing = playing;
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    public void update(){
        move();
    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        for (int i = 0; i < bulletArr.size(); i++) {
            Bullet bullet = bulletArr.get(i);
            if (!bullet.active) {
                g.fillRect((int)bullet.getHitbox().x, (int)bullet.getHitbox().y, bullet.width, bullet.height);
            }
        }
    }


    public void move() {
        for (int i = 0; i < bulletArr.size(); i++) { // Bucle para detectar todas las balas
            Bullet bullet = bulletArr.get(i);
            bullet.getHitbox().y -= bulletSpeed; // Movimiento hacia arriba

            // Detecta la Colision con Enemigos
            for (int j = 0; j < playing.enemyManager.getEnemies().size(); j++) {
                Alien1 alien = playing.enemyManager.getEnemies().get(j);

                if (!bullet.active && alien.active && DetectCollision(alien, bullet)) {
                    alien.newState(DEAD); // Metodo para hacer que empiece la animacion de DEAD y
                    bullet.active = true;
                }
            }

            // Remueve las balas que lleguen al limite
            while (bulletArr.size() > 0 && (bulletArr.get(0).active || bulletArr.get(0).y < 0)) {
                bulletArr.remove(0);
            }
        }
    }

    public void createBullet() {
        Bullet bullet = new Bullet(
                playing.getPlayer().hitbox.x,
                playing.getPlayer().hitbox.y,
                Game.TILES_SIZE / 8,
                Game.TILES_SIZE / 2);
        bulletArr.add(bullet);
    }
}
