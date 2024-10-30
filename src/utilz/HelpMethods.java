package utilz;

import entities.Entity;
import main.Game;

public class HelpMethods {

    /// DetectCollision ==> Detecta la colision entre 2 entidades
    public static boolean DetectCollision(Entity a, Entity b) {
        /* Compara si el lado izquierdo de 'a' está a la izquierda del lado derecho de 'b'
         y si el lado derecho de 'a' está a la derecha del lado izquierdo de 'b'*/
        return  a.getHitbox().x < b.getHitbox().x + b.getHitbox().width &&   // Colisión en el eje X (izquierda-derecha)
                a.getHitbox().x + a.getHitbox().width > b.getHitbox().x &&    // Colisión en el eje X (derecha-izquierda)

                // Compara si la parte superior de 'a' está por encima de la parte inferior de 'b'
                // y si la parte inferior de 'a' está por debajo de la parte superior de 'b'
                a.getHitbox().y < b.getHitbox().y + b.getHitbox().height &&   // Colisión en el eje Y (arriba-abajo)
                a.getHitbox().y + a.getHitbox().height > b.getHitbox().y;     // Colisión en el eje Y (abajo-arriba)
    }

    /// CanMoveHere ==> Detecta si un area puede moverse sin chocar con las paredes
    public static boolean CanMoveHere(float x, float y, float width, float height) {
    /* Revisa las cuatro esquinas del hitbox:
         - Si alguna esquina está fuera de los límites, retorna false.
         - Si todas están dentro de los límites, retorna true.
    */

        // Verifica esquina superior izquierda
        if (!IsSolid(x, y))
            // Verifica esquina inferior derecha
            if (!IsSolid(x + width, y + height))
                // Verifica esquina superior derecha
                if (!IsSolid(x + width, y))
                    // Verifica esquina inferior izquierda
                    if (!IsSolid(x, y + height))
                        return true;  // Movimiento permitido si todas las esquinas están dentro

        return false;  // Movimiento bloqueado si alguna esquina está fuera
    }

    /// IsSolid ==> Comprueba si la posición (x, y) está fuera de los límites del juego.
    public static boolean IsSolid(float x, float y) {

    // Si (x, y) está fuera de los límites, retorna true; si está dentro, retorna false.
        if (x < 0 || x >= Game.GAME_WIDTH) return true;  // Fuera del ancho permitido
        if (y < 0 || y >= Game.GAME_HEIGHT) return true;  // Fuera del alto permitido
        return false;  // Dentro de los límites
    }


}
