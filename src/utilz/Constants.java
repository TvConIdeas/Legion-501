package utilz;

import main.Game;

public class Constants {

    public static class EnemyConstants{
        public static final int Alien1 = 0; // Numero para identificar el Alien

        public static final int IDLE = 0; // State = Inactivo
        public static final int MOVING = 1; // State = En Movimiento
        public static final int DEAD = 2; // State = Muerto

        public static final int Alien_WIDHT_DEFAULT = 32; // Tamaño por default
        public static final int Alien_HEIGHT_DEFAULT = 32;

        public static final int Alien_WIDTH = (int) (Alien_WIDHT_DEFAULT * Game.SCALE); // Tamaño segun el Scale
        public static final int Alien_HEIGHT = (int) (Alien_HEIGHT_DEFAULT * Game.SCALE);

        // Method para conseguir la cantidad de sprite para cada State
        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch (enemy_type){ // Segun que enemigo
                case Alien1: // En caso Alien 1
                    switch (enemy_state){ // Segun su estado
                        case IDLE:
                            return 5;
                        case MOVING: // Nada por ahora
                            return 4;
                        case DEAD:
                            return 7;
                    }
            }
            return 0;
        }
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        // Constante de Estados del Jugador
        public static final int IDLE = 0;
        public static final int MOVING = 1;
        public static final int DEAD = 2;

        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case IDLE:
                    return 6; // La cantidad de sprites para esa accion (Actualizar)
                case MOVING:
                    return 6;
                case DEAD:
                    return 7;
                default:
                    return 1;
            }
        }
    }
}
