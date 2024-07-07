package org.enkatsu.zxingp5;

import processing.core.PApplet;
import processing.core.PImage;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;

/**
 * @author Katsuya Endoh
 */

public class QRCode {
    // app is a reference to the parent sketch
    @SuppressWarnings("unused")
    private PApplet app;
    private PImage img;
    private int colorOn;
    private int colorOff;
    static final int IMAGE_FORMAT = PApplet.RGB;
    /**
     * @param app A PApplet representing the user sketch, i.e "this"
     * @param bitmap bitmap
     */
    public QRCode(PApplet app, BinaryBitmap bitmap) {
        this.app = app;
        this.img = app.createImage(bitmap.getWidth(), bitmap.getHeight(), QRCode.IMAGE_FORMAT);
        this.initColor();
        this.setPixels(bitmap);
    }

    /**
     * @param app A PApplet representing the user sketch, i.e "this"
     * @param bitmap bitmap
     */
    public QRCode(PApplet app, boolean[][] bitmap) {
        this.app = app;
        this.img = app.createImage(bitmap[0].length, bitmap.length, QRCode.IMAGE_FORMAT);
        this.initColor();
        this.setPixels(bitmap);
    }

    /**
     * @param app A PApplet representing the user sketch, i.e "this"
     * @param bitmap binary array
     * @param width image width
     * @param height image width
     */
    public QRCode(PApplet app, boolean[] bitmap, int width, int height) {
        this.app = app;
        this.img = app.createImage(width, height, QRCode.IMAGE_FORMAT);
        this.initColor();
        this.setPixels(bitmap, width, height);
    }

    /**
     *
     */
    private void initColor() {
        this.colorOn = 0x000000;
        this.colorOff = 0xFFFFFF;
    }

    /**
     * @param bitmap
     */
    private void setPixels(BinaryBitmap bitmap) {
        for(int y = 0; y < bitmap.getHeight(); y++) {
            for(int x = 0; x < bitmap.getWidth(); x++) {
                try {
                    this.img.pixels[y * bitmap.getWidth() + x]
                            = bitmap.getBlackMatrix().get(x, y) ? colorOn : colorOff;
                } catch (NotFoundException e) {
                   System.err.println(e.getMessage());
                }
            }
        }
    }

    private void setPixels(boolean[][] bitmap) {
        for(int y = 0; y < bitmap.length; y++) {
            for(int x = 0; x < bitmap[y].length; x++) {
                this.img.pixels[y * bitmap[y].length + x] = bitmap[y][x] ? colorOn : colorOff;
            }
        }
    }

    private void setPixels(boolean[] bitmap, int width, int height) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                this.img.pixels[y * width + x] = bitmap[y * width + x] ? colorOn : colorOff;
            }
        }
    }

    /**
     * @return QR code image
     */
    public PImage getImage() {
        return this.img;
    }

    /**
     * @param width image width
     * @param height image height
     * @return QR code image
     */
    public PImage getImage(int width, int height) {
        PImage img = this.img.copy();
        img.resize(width, height);
        return img;
    }
}
