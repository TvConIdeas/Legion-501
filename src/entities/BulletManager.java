package entities;

import gameState.Playing;
import main.Game;

import java.awt.*;
import java.util.ArrayList;

public class BulletManager {
    // ====================> ATRIBUTOS <====================
    private Playing playing;
    private ArrayList<Bullet> bulletArr = new ArrayList<>(); ;
    private float bulletSpeed = 5.0f;

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
                g.fillRect((int)bullet.x, (int)bullet.y, bullet.width, bullet.height);
            }
        }
    }


    public void move() {
        for (int i = 0; i < bulletArr.size(); i++) {
            Bullet bullet = bulletArr.get(i);
            bullet.y -= bulletSpeed;
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
