package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gameState.Playing;
import main.Game;
import utilz.LevelConfig;

import static main.Game.GAME_WIDTH;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.DetectCollision;

public class EnemyManager <T extends Enemy> {
    // ====================> ATRIBUTOS <====================
    private Playing playing; // Traemos el State Playing

    private ArrayList<T> enemies = new ArrayList<>(); // ArrayList con los aliens, (revisar, cambiar a Enemy)

    private int alienRows = 5; // Cantidad de Filas de aliens
    private int alienCount = 0; // Numero de Aliens a vencer
    private float alienVelocityX = 0.05f; // Velocidad de los aliens

    private String curretLevel = "Easy"; // Nivel actual (inicializado en "Easy")

    // ====================> CONSTRUCTOR <====================
    public EnemyManager(Playing playing) {
        this.playing = playing;
    }

    // ====================> GET | SET <====================
    public int getAlienCount() {
        return alienCount;
    }

    public ArrayList<T> getEnemies() {
        return enemies;
    }

    public void setCurretLevel(String curretLevel) {
        this.curretLevel = curretLevel;
    }

    // ====================> METODOS <====================
    public void update(){
        for(T alien : enemies){
            alien.update();
            move();
        }
    }

    public void draw(Graphics g){
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
                alien.x += alienVelocityX;

                // SI el alien toca con su Hitbox.X las paredes
                if (alien.x + alien.width >= GAME_WIDTH || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2; // eeeee revisar

                    // Movemos todos los aliens una fila con su Hitbox.Y
                    for (int j = 0; j < enemies.size(); j++) {
                        enemies.get(j).y += Alien_HEIGHT;
                    }
                }

                if (DetectCollision(alien, playing.getPlayer())) {
                    System.out.println("Colision Jugador");
                    playing.getPlayer().newState(DEAD);
                }

                alien.updateHitbox();
            }
        }
    }

    /** loadConfigLevel() ==> Se encarga de Cargar la configuracion por dificultad del nivel. */
    public void loadConfigLevel(Map<String, LevelConfig> levelManager){
        // Facil
        Map<String, Integer> aliensEasy = new HashMap<>();
        aliensEasy.put("alien1", 8);
        aliensEasy.put("alien2", 2);
        levelManager.put("Easy", new LevelConfig(aliensEasy));

        // Medio
        Map<String, Integer> aliensMedium = new HashMap<>();
        aliensMedium.put("alien1", 10);
        aliensMedium.put("alien2", 5);
        levelManager.put("Medium", new LevelConfig(aliensMedium));

        // Dificil
        Map<String, Integer> aliensHard = new HashMap<>();
        aliensHard.put("alien1", 8);
        aliensHard.put("alien2", 12);
        levelManager.put("Hard", new LevelConfig(aliensHard));
    }

    /** createAliens() ==> Generar y organiza los aliens de acuerdo con la configuración del nivel actual.*/
    public void createAliens() {
        enemies.clear(); // Limpiamos la lista de enemigos de niveles anteriores

        LevelConfig config = playing.levelManager.get(curretLevel); // Obtenemos la configuración actual
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
                    if (j >= alienRows) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
        alienCount = enemies.size(); // Actualizamos el contador total de aliens
    }

    /** spawnAlien() ==> Crea una instancia de un alien específico según el tipo dado. */
    private T spawnAlien(String tipoAlien, int x, int y) {
        T alien = null;
        switch (tipoAlien) {
            case "alien1":
                alien = (T) new Alien1(x, y);
                break;
            case "alien2":
                alien = (T) new Alien2(x, y);
                break;
//            case "alien3":
//                alien = (T) new Alien3(x, y);
//                break;
//            case "alien4":
//                alien = (T) new Alien4(x, y);
//                break;
        }
        return alien;
    }

}
