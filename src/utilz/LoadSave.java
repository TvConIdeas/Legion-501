package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    // Constante con cada Sprite que utilizemos
    public static final String Player_ATLAS = "player_sprites.png";
    public static final String Alien1_ATLAS = "alien1_sprites.png";
    public static final String Alien2_ATLAS = "alien2_sprites.png";
    public static final String Alien3_ATLAS = "alien3_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";

    // Funcion que retorna esas imagenes
    public static BufferedImage GetSpritesAtlas(String fileName){
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }

}
