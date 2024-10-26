package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        // Constante de Estados del Jugador
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int DEAD = 2;

        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case IDLE:
                    return 6; // La cantidad de sprites para esa accion (Actualizar)
                case RUNNING:
                    return 6;
                case DEAD:
                    return 7;
                default:
                    return 1;
            }
        }





    }

}
