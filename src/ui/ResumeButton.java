package ui;


import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.Buttons.*;

//public class ResumeButton extends ClassButton {
//
//    // ====================> ATRIBUTOS <====================
//    private BufferedImage[] imgs;
//    private int xOffsetCenter = B_WIDTH / 2; // El centro a lo ancho
//    private int rowIndex, index;
//    private boolean mouseOver, mousePressed;
//
//    // ====================> CONSTRUCTOR <====================
//    public ResumeButton(int x, int y, int rowIndex) {
//        super(x, y, B_WIDTH, B_HEIGHT);
//        this.rowIndex = rowIndex;
//        loadImgs();
//    }
//
//    // ====================> GET | SET <====================
//    public int getRowIndex() {
//        return rowIndex;
//    }
//
//    public void setRowIndex(int rowIndex) {
//        this.rowIndex = rowIndex;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public boolean isMouseOver() {
//        return mouseOver;
//    }
//
//    public void setMouseOver(boolean mouseOver) {
//        this.mouseOver = mouseOver;
//    }
//
//    public boolean isMousePressed() {
//        return mousePressed;
//    }
//
//    public void setMousePressed(boolean mousePressed) {
//        this.mousePressed = mousePressed;
//    }
//
//    // ====================> METODOS <====================
//    public void update() {
//        index = 0;
//        if (mouseOver)
//            index = 1;
//        if (mousePressed)
//            index = 2;
//
//    }
//
//    public void draw(Graphics g) {
//        g.drawImage(imgs[index], x - xOffsetCenter, y, B_WIDTH, B_HEIGHT, null);
//    }
//
//    private void loadImgs() {
//        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.RESUME_BUTTONS);
//        imgs = new BufferedImage[3];
//        for (int i = 0; i < imgs.length; i++)
//            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
//    }
//
//    public void resetBools() {
//        mouseOver = false;
//        mousePressed = false;
//    }
//
//}
