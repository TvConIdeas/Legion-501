package utilz;

import main.Game;

public class Constants {

    public static final int ANI_SPEED_PLAYER = 15;
    public static final int ANI_SPEED_ALIEN = 20;
    public static final int ANI_SPEED_ATTACK = 50; // Temporizador Disparo de Enemy || 2000 Milisegundos (2 segundos)
    public static final int ANI_RESTART_LEVEL = 150; // Temporizador para reiniciar nivel
    public static final int ANI_ERROR_MESSAGE = 400; // Temporizador para mostrar mensaje de error en Register


    public static class EnemyConstants{
        public static final int Alien1 = 0; // Numero para identificar el Alien
        public static final int Alien2 = 1; // Numero para identificar el Alien
        public static final int Alien3 = 2; // Numero para identificar el Alien
        public static final int Alien4 = 3; // Numero para identificar el Alien

        public static final int MOVING = 0; // State = En movimiento
        public static final int DEAD = 1; // State = Muerto
        public static final int HIT = 2; // State = Golpe

        public static final int Alien_WIDHT_DEFAULT = 32; // Tamaño por default
        public static final int Alien_HEIGHT_DEFAULT = 32;

        public static final int Alien_WIDTH = (int) (Alien_WIDHT_DEFAULT * Game.SCALE); // Tamaño segun el Scale
        public static final int Alien_HEIGHT = (int) (Alien_HEIGHT_DEFAULT * Game.SCALE);

        // Method para conseguir la cantidad de sprite para cada State
        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch (enemy_type){ // Segun que enemigo
                case Alien1: // En caso Alien 1
                    switch (enemy_state){ // Segun su estado
                        case MOVING:
                            return 5;
                        case DEAD:
                            return 1;
                    }
                case Alien2:
                    switch (enemy_state){
                        case MOVING:
                            return 5;
                        case DEAD:
                            return 1;
                        case HIT:
                            return 5;
                    }
                case Alien3:
                    switch (enemy_state){
                        case MOVING:
                            return 5;
                        case DEAD:
                            return 1;
                    }
                case Alien4:
                    switch (enemy_state){
                        case MOVING:
                            return 5;
                        case DEAD:
                            return 1;
                        case HIT:
                            return 5;
                    }
            }
            return 0;
        }
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 62;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * (Game.SCALE+0.3));
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * (Game.SCALE+0.3));
        }
    }

    public static class PlayerConstants {
        // Constante de Estados del Jugador
        public static final int IDLE = 0;
        public static final int EXPLODE = 1;

        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case IDLE:
                    return 7; // La cantidad de sprites para esa accion (Actualizar)
                case EXPLODE:
                    return 11;
                default:
                    return 1;
            }
        }
    }
}
