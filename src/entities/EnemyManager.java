package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameState.Playing;
import main.Game;
import utilz.LoadSave;

import static main.Game.GAME_WIDTH;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    // ====================> ATRIBUTOS <====================
    private Playing playing;
    private BufferedImage[][] alien1Arr; // Matriz con las animaciones del Alien1

    private ArrayList<Alien1> aliens1 = new ArrayList<>(); // ArrayList con los aliens
    int alienRows = 4; // Cantidad de Filas de aliens
    int alienColumns = 4; // Cantidad de Columnas de aliens
    int alienCount = 0; // Numero de Aliens a vencer
    private float alienVelocityX = 0.2f; // Velocidad de los aliens (Revisar)

    // ====================> CONSTRUCTOR <====================
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        createAliens();
    }

    // ====================> METODOS <====================
    public void update(){
        for(Alien1 alien1 : aliens1){
            //alien1.update(); Revisar
            move();
        }
    }

    public void draw(Graphics g){
        drawAlien1(g);
    }

    /** move() ==> Se encarga de mover la ubicacion de los aliens1 */
    public void move(){
        for (int i = 0; i < aliens1.size(); i++) {
            Alien1 alien = aliens1.get(i);
            if (alien.alive) { // Si el Alien esta vivo
                alien.hitbox.x += alienVelocityX;

                // SI el alien toca con su Hitbox.X las paredes
                if (alien.hitbox.x + alien.width >= GAME_WIDTH || alien.hitbox.x <= 0) {
                    alienVelocityX *= -1;
                    alien.hitbox.x += alienVelocityX*2;

                    // Movemos todos los aliens una fila con su Hitbox.Y
                    for (int j = 0; j < aliens1.size(); j++) {
                        aliens1.get(j).hitbox.y += Alien_HEIGHT;
                    }
                }
            }
        }
    }

    /** drawAlien1() ==> Method "sub-draw" para dibujar los Aliens 1*/
    private void drawAlien1(Graphics g){
         for(Alien1 alien1 : aliens1){ // Hasta que se recorra el Array completo
             if (alien1.alive){ // Si esta vivo
                 g.drawImage(alien1Arr[alien1.getEnemyState()][alien1.getAniIndex()],
                         (int)alien1.getHitbox().x,
                         (int)alien1.getHitbox().y,
                         Alien_WIDTH,
                         Alien_HEIGHT, null);
             } // Dibuja en Alien, UTILIZANDO SU HITBOX
        }
    }

    /** createAliens() ==> Ubica los aliens segun las filas y columnas y los agrega al
     * ArrayList */
    public void createAliens() {
        for (int i = 0; i < alienColumns; i++) {
            for (int j = 0; j < alienRows; j++) {
                Alien1 alien = new Alien1(
                        Game.TILES_SIZE + i * Alien_WIDTH,
                        Game.TILES_SIZE + j * Alien_HEIGHT);
                aliens1.add(alien); // Lo agregamos al ArrayList
            }
        }
        alienCount = aliens1.size(); // Lo agregamos al contador
    }

    /** loadEnemyImgs() ==> Separa el SpriteSheat y los ubica en una matriz. */
    private void loadEnemyImgs() {
        alien1Arr = new BufferedImage[2][7];
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.Alien1_ATLAS);
        for (int j = 0; j < alien1Arr.length; j++)
            for (int i = 0; i < alien1Arr[j].length; i++)
                alien1Arr[j][i] = temp.getSubimage(i * Alien_WIDHT_DEFAULT, j * Alien_HEIGHT_DEFAULT, Alien_WIDHT_DEFAULT, Alien_HEIGHT_DEFAULT);
    }

}
