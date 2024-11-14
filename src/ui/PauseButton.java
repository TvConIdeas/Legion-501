package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;

public class PauseButton extends ClassButton {
    // ====================> ATRIBUTOS <====================
    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    // ====================> CONSTRUCTOR <====================
    public PauseButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    // ====================> GET | SET <====================
    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.PAUSE_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * PB_DEFAULT_SIZE, rowIndex * PB_DEFAULT_SIZE, PB_DEFAULT_SIZE, PB_DEFAULT_SIZE);

    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;

    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, PB_SIZE, PB_SIZE, null);
    }

}
