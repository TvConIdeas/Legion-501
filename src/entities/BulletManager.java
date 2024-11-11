package entities;

import gameState.Playing;
import main.Game;
import utilz.IRenderable;

import java.awt.*;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.HelpMethods.DetectCollision;

public class BulletManager implements IRenderable {
    // ====================> ATRIBUTOS <====================
    private Playing playing;
    public ArrayList<Bullet> bulletArr = new ArrayList<>(); // Arraylist con las balas
    private float bulletSpeed = 5.0f; // Velocidad de la bala

    // ====================> CONSTRUCTOR <====================
    public BulletManager(Playing playing) {
        this.playing = playing;
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
    /** move() ==> Movimiento de la bala y verificacion de colisiones */
    public void move() {
        for (int i = 0; i < bulletArr.size(); i++) { // Bucle para detectar todas las balas
            Bullet bullet = bulletArr.get(i);
            bullet.getHitbox().y -= bulletSpeed; // Movimiento hacia arriba

            // Detecta la Colision con Enemigos
            for (int j = 0; j < playing.enemyManager.getEnemies().size(); j++) {
                Enemy alien = (Enemy) playing.enemyManager.getEnemies().get(j);

                if (!bullet.active && alien.active && DetectCollision(alien, bullet)) {
                    alien.lives--;
                    if(alien.lives == 1){
                        alien.newState(HIT);
                    } else if(alien.lives == 0){
                        alien.disableHitbox();
                        alien.newState(DEAD); // Metodo para hacer que empiece la animacion de DEAD
                        playing.alienCount--;
                        playing.score += 10;
                    }
                    bullet.active = true;
                }
            }

            // Remueve las balas que lleguen al limite
            while (!bulletArr.isEmpty() && (bulletArr.getFirst().active || bulletArr.getFirst().y < 0)) {
                bulletArr.removeFirst();
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
        bulletArr.add(bullet);
    }

    /// Interface IRenderable
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
}
