package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.PauseButtons.*;

public class ClassButton {
    // ====================> ATRIBUTOS <====================
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = PB_SIZE / 2;
    private boolean mouseOver, mousePressed;
    private BufferedImage[] imgs;
    private Rectangle hitbox;

    // ====================> CONSTRUCTOR <====================
    public ClassButton(int xPos, int yPos, int rowIndex) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        loadImgs(); // Cargar Imagenes
        initBounds();
    }
    // ====================> GET | SET <====================
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
    public void initBounds() {
        hitbox = new Rectangle(xPos - xOffsetCenter, yPos, PB_SIZE, PB_SIZE);
    }

    // Cargamos las imagenes del boton
    public void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.PAUSE_BUTTONS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * PB_DEFAULT_SIZE, rowIndex * PB_DEFAULT_SIZE, PB_DEFAULT_SIZE, PB_DEFAULT_SIZE);
    }

    // Dibujamos el Boton
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, PB_SIZE, PB_SIZE, null);
    }

    // Index, controla cuál de las imágenes del botón se muestra, según el estado del mouse.
    public void update() {
        index = 0; // Inactivo
        if (mouseOver)
            index = 1; // Sobre el Boton
        if (mousePressed)
            index = 2; // Presiona el Boton
    }

    // Reseteamos los booleanos a False
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}
