package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import gameState.GameState;
import utilz.LoadSave;
import static utilz.Constants.UI.Buttons.*;

/***
 * MenuButton ==>
 *
 * Creamos los botones pricipales del menu, Jugar, Opciones y Salir
 */
public class MenuButton {
    // ====================> ATRIBUTOS <====================
    private int xPos, yPos, rowIndex, index; // rowIndex: Define la fila en la que se encuentran las imágenes del botón
    private int xOffsetCenter = B_WIDTH / 2; // El centro a lo ancho
    private GameState state; // Para cambiar el State
    private BufferedImage[] imgs; // Imagenes
    private boolean mouseOver, mousePressed; // Booleanos del mouse
    private Rectangle hitbox; // Hitbox del boton

    // ====================> CONSTRUCTOR <====================
    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs(); // Cargar Imagenes
        initBounds(); // Cargar Hitbox
    }

    // ====================> GET || SET <====================
    public boolean isMouseOver() {
        return mouseOver;
    }
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    public boolean isMousePressed() {
        return mousePressed;
    }
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    // ====================> METODOS <====================

    private void initBounds() {
        hitbox = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    // Cargamos las imagenes del boton
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    // Dibujamos el Boton
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    // Index, controla cuál de las imágenes del botón se muestra, según el estado del mouse.
    public void update() {
        index = 0; // Inactivo
        if (mouseOver)
            index = 1; // Sobre el Boton
        if (mousePressed)
            index = 2; // Presiona el Boton
    }

    // Cambiamos el State
    public void applyGamestate() {
        GameState.state = state;
    }

    // Reseteamos los booleanos a False
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
