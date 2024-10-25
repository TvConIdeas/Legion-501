package utilz;

import main.Game;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height) {
        /*  Se revisan las cuatro esquinas del hitbox:
            - Arriba a la izquierda.
            - Abajo a la derecha.
            - Arriba a la derecha.
            - Abajo a la izquierda.
            Si alguna es sólida retorna false y no se permite el movimiento.
            Si todas las posiciones están libres, entonces retorna true.
         */

        if (!IsSolid(x, y))
            if (!IsSolid(x + width, y + height))
                if (!IsSolid(x + width, y))
                    if (!IsSolid(x, y + height))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y) {
        // Esta funcion se encarga de si una coordenada (x, y) está fuera de los límites del área de juego.
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        return false;
    }

}
