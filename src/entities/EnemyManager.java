package entities;

import java.awt.Graphics;
import java.util.*;

import gameState.Playing;
import main.Game;
import utilz.IRenderable;
import utilz.LevelConfig;

import static main.Game.GAME_WIDTH;
import static utilz.Constants.ANI_SPEED_ATTACK;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.PlayerConstants.EXPLODE;
import static utilz.HelpMethods.DetectCollision;

public class EnemyManager <T extends Enemy> implements IRenderable {
    // ====================> ATRIBUTOS <====================
    private Playing playing; // Traemos el State Playing
    private ArrayList<T> enemies; // ArrayList con los aliens
    private int alienColumns = 5; // Cantidad de Columnas de aliens
    private int aniTick; // Contador para el disparo de enemigo
    private float alienVelocityX = 1f; // Velocidad de los aliens
    public boolean stopEnemys = false; // Booleano para frenar a los enemigos

    // ====================> CONSTRUCTOR <====================
    public EnemyManager(Playing playing) {
        this.playing = playing;
        enemies = new ArrayList<>();
    }

    // ====================> GET | SET <====================
    public ArrayList<T> getEnemies() {
        return enemies;
    }

    public float getAlienVelocityX() {
        return alienVelocityX;
    }

    public void setAlienVelocityX(float alienVelocityX) {
        this.alienVelocityX = alienVelocityX;
    }

    public int getRemainingAliens() {
        return getEnemies().size();
    }

    // ====================> METODOS <====================
    /** move() ==> Se encarga de mover la ubicacion de los aliens1. */
    public void move(){
        if(!stopEnemys){
            for (int i = 0; i < enemies.size(); i++) {
                T alien = enemies.get(i);
                if (alien.active) { // Si el Alien esta vivo
                    alien.x += alienVelocityX;

                    // Si el alien toca con su Hitbox.X las paredes
                    if (alien.x + alien.width >= GAME_WIDTH || alien.x <= 0) {
                        alienVelocityX *= -1;
                        alien.x += alienVelocityX * 2;

                        // Movemos todos los aliens una fila con su Hitbox.Y
                        for (int j = 0; j < enemies.size(); j++) {
                            enemies.get(j).y += Alien_HEIGHT;
                        }
                    }

                    // Colision con Jugador
                    if (DetectCollision(alien, playing.getPlayer())) {
                        System.out.println("Colision Jugador");
                        playing.getPlayer().disableHitbox(); // Se desactiva la hitbox para que no siga habiendo colisión
                        playing.getPlayer().newState(EXPLODE); // Se cambia el estado de jugador a muerto, mostrando animacion
                    }

                    alien.updateHitbox();
                }
            }
        }
    }

    /** shootEnemy() ==> Disparo de enemigo. */
    public void shootEnemy(){
        if (enemies.isEmpty()) return; // Si no hay enemigos, salir del méthod

        Random random = new Random();
        int num = random.nextInt(enemies.size()); // Generar nro random de posicion de ArrayList
        if (enemies.get(num).attack && enemies.get(num).active) { // Si puede atacar y esta activo
            playing.bulletManager.createBulletAlien(enemies.get(num)); // Disparar
        }
    }

    /*** hitEnemy() ==> En caso de que una bala de Player colisione con un enemigo, le resta una vida. */
    public void hitEnemy(T alien){
        alien.lives--;
        if(alien.lives == 1){ // Si el queda una vida (para Aliens 2 y 4)
            alien.newState(HIT); // Cambiar estado a HIT
        } else if(alien.lives == 0){
            alien.disableHitbox(); // Desactivar hitbox para que no haya mas colisiones
            alien.newState(DEAD); // Metodo para hacer que empiece la animacion de DEAD
            playing.alienCount--;
            playing.score += 10;
        }
    }

    /** loadConfigLevel() ==> Se encarga de Cargar la configuracion por dificultad del nivel. */
    public void loadConfigLevel(Map<String, LevelConfig> levelManager){
        // Facil
        Map<String, Integer> aliensEasy = new LinkedHashMap<>();
        aliensEasy.put("alien2", 5);
        aliensEasy.put("alien1", 10);
        levelManager.put("easy", new LevelConfig(aliensEasy));

        // Medio
        Map<String, Integer> aliensMedium = new LinkedHashMap<>();
        aliensMedium.put("alien3", 5);
        aliensMedium.put("alien2", 10);
        aliensMedium.put("alien1", 5);
        levelManager.put("medium", new LevelConfig(aliensMedium));

        // Dificil
        Map<String, Integer> aliensHard = new LinkedHashMap<>();
        aliensHard.put("alien4", 5);
        aliensHard.put("alien3", 10);
        aliensHard.put("alien2", 5);
        levelManager.put("hard", new LevelConfig(aliensHard));
    }

    /** createAliens() ==> Generar y organiza los aliens de acuerdo con la configuración del nivel actual.*/
    public void createAliens() {
        enemies.clear(); // Limpiamos la lista de enemigos de niveles anteriores

        LevelConfig config = playing.levelManager.get(playing.getCurrentLevel()); // Obtenemos la configuración actual
        Map<String, Integer> alienCounts = config.getAlienTypes(); // Tipos y cantidades de aliens

        int i = 0, j = 0; // 'i' para las filas y 'j' para las columnas en cada fila

        // Recorremos el mapa de tipos y cantidades de aliens
        for (Map.Entry<String, Integer> entry : alienCounts.entrySet()) {
            String tipoAlien = entry.getKey();
            int cantidad = entry.getValue();

            // Creamos la cantidad indicada de aliens del tipo actual
            for (int k = 0; k < cantidad; k++) {
                T alien = spawnAlien(tipoAlien,
                        Game.TILES_SIZE + j * Alien_WIDTH,
                        Game.TILES_SIZE + i * Alien_HEIGHT);
                if (alien != null) {
                    enemies.add(alien);
                    j++;
                    if (j >= alienColumns) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    /** spawnAlien() ==> Crea una instancia de un alien específico según el tipo dado. */
    private T spawnAlien(String alienType, int x, int y) {
        T alien = switch (alienType) {
            case "alien1" -> (T) new Alien1(x, y);
            case "alien2" -> (T) new Alien2(x, y);
            case "alien3" -> (T) new Alien3(x, y);
            case "alien4" -> (T) new Alien4(x, y);
            default -> null;
        };
        return alien;
    }

    /** <======= Interface IRenderable =======> */
    @Override
    public void update(){
        // Remover enemigos inactivos antes de actualizar
        enemies.removeIf(enemy -> !enemy.active); // Elimina enemigos inactivos

        for (T alien : enemies) {
            alien.update();
        }

        move();

        if (!stopEnemys) {
            aniTick++;
            if (aniTick >= ANI_SPEED_ATTACK) {
                aniTick = 0;
                shootEnemy();
            }
        }
    }

    @Override
    public void draw(Graphics g){
        // Crear una copia inmutable de la lista de enemigos activos
        List<T> enemiesSnapshot = List.copyOf(enemies);

        for (T alien : enemiesSnapshot) {
            if (alien.active) {
                alien.draw(g);
            }
        }
    }
}
