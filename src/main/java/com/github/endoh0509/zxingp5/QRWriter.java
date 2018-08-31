package com.github.endoh0509.zxingp5;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author Katsuya Endoh
 */

public class QRWriter {

    // app is a reference to the parent sketch
    private PApplet app;

    private QRCodeWriter writer;
    private boolean[][] bits;
    private BitMatrix bitMatrix;

    public final static String VERSION = "1.0.0";

    /**
     * @param app A PApplet representing the user sketch, i.e "this"
     */
    public QRWriter(PApplet app) {
        this.app = app;
        this.writer = new QRCodeWriter();
    }

    /**
     * @param width int
     * @param height int
     * @return Encoded QR code image.
     */
    public PImage getImage(int width, int height) {
        PGraphics pg = app.createGraphics(width, height);
        pg.beginDraw();
        pg.background(255);
        pg.fill(0);
        pg.noStroke();
        float w = width / bits.length;
        for (int y = 0; y < bits.length; y++) {
            for (int x = 0; x < bits[y].length; x++) {
                if (bits[y][x]) {
                    pg.rect(y * w, x * w, w, w);
                }
            }
        }
        pg.endDraw();
        return pg.get();
    }

    /**
     * @param width int
     * @param height int
     * @return Encoded QR code instance.
     */
    public QRCode getQRCode(int width, int height) {
        QRCode qr = new QRCode(this.app, this.bits);
        return new QRCode(this.app, this.bits);
    }

    /**
     * @param src Text to encode into QR code
     * @param format Barcode format, i.e "BarcodeFormat.QR_CODE"
     * @param width int
     * @param height int
     * @return Bits from encoded QR code.
     */
    public boolean[][] encode(String src, BarcodeFormat format, int width, int height) {
        if(21 > width || width > 177 || 21 > height || height > 177) {
            return null;
        }
        try {
            bitMatrix = this.writer.encode(src, format, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        bits = new boolean[bitMatrix.getHeight() - 1][bitMatrix.getWidth() - 1];
        for (int y = 0; y < bits.length; y++) {
            for (int x = 0; x < bits[y].length; x++) {
                bits[y][x] = bitMatrix.get(y, x);
            }
        }
        return bits;
    }

    /**
     * @param src Text to encode into QR code
     * @param width int
     * @param height int
     * @return Bits from encoded QR code.
     */
    public boolean[][] encode(String src, int width, int height) {
        if(21 > width || width > 177 || 21 > height || height > 177) {
            return null;
        }
        this.encode(src, BarcodeFormat.QR_CODE, width, height);
        return bits;
    }

    /**
     * @param src Text to encode into QR code
     * @param version QR code version
     * @return Bits from encoded QR code.
     */
    public boolean[][] encode(String src, int version) {
        if(1 > version || version >= 40) {
            return null;
        }
        int size = 21 + version * 4;
        this.encode(src, size, size);
        return bits;
    }

    public BitMatrix getBitMatrix() {
        return this.bitMatrix;
    }

    @Override
    public String toString() {
        return this.bitMatrix.toString();
    }

    /**
     * return the version of the Library.
     *
     * @return String
     */
    public static String version() {
        return VERSION;
    }
}