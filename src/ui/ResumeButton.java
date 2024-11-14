package ui;


import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.Buttons.*;
import static utilz.Constants.UI.PauseButtons.PB_DEFAULT_SIZE;
import static utilz.Constants.UI.PauseButtons.PB_SIZE;

public class ResumeButton{
    // ====================> ATRIBUTOS <====================
    private int xPos, yPos, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private boolean mouseOver, mousePressed;
    private BufferedImage[][] imgs;
    private Rectangle hitbox;

    // ====================> CONSTRUCTOR <====================
    public ResumeButton(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        loadImgs();
        initBounds();
    }

    // ====================> GET | SET <====================


    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

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

    // ====================> METODOS <====================
    public void initBounds() {
        hitbox = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    // Cargamos las imagenes del boton
    public void loadImgs() {
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.RESUME_BUTTONS);

        imgs = new BufferedImage[1][3];
        for (int j = 0; j < imgs.length; j++)
            for (int i = 0; i < imgs[j].length; i++)
                imgs[j][i] = temp.getSubimage(i * B_WIDTH_DEFAULT, j*B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    // Dibujamos el Boton
    public void draw(Graphics g) {
        g.drawImage(imgs[0][index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
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
