package com.github.endoh0509.zxingp5;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import com.google.zxing.*;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;

/**
 * @author Katsuya Endoh
 */

public class QRReader {

    // app is a reference to the parent sketch
    private PApplet app;
    private QRCodeReader reader;
    private Result result = null;
    private final static String VERSION = "1.0.0";

    /**
     * @param app A PApplet representing the user sketch, i.e "this"
     */
    public QRReader(PApplet app) {
        this.app = app;
        this.reader = new QRCodeReader();
    }

    /**
     *
     * @param img Image to decode
     * @param globalHistogram Global histogram
     * @param printError Flag for displaying error message at decoding
     * @return Decoded text
     */
    public String decode(PImage img, boolean globalHistogram, boolean printError) {
        BufferedImage buf = (BufferedImage) img.getNative();
        LuminanceSource source = new BufferedImageLuminanceSource(buf);
        BinaryBitmap bitmap;
        if (globalHistogram) {
            bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
        } else {
            bitmap = new BinaryBitmap(new HybridBinarizer(source));
        }
        try {
            this.result = reader.decode(bitmap);
        } catch (NotFoundException e) {
            this.result = null;
            if(printError) PApplet.println(e);
        } catch (ChecksumException e) {
            this.result = null;
            if(printError) PApplet.println(e);
        } catch (FormatException e) {
            this.result = null;
            if(printError) PApplet.println(e);
        }
        if (this.result != null && this.result.getText() != null) {
            return this.result.getText();
        }
        return null;
    }

    /**
     *
     * @param img Image to decode
     * @param globalHistogram Global histogram
     * @return Decoded text
     */
    public String decode(PImage img, boolean globalHistogram) {
        return this.decode(img, globalHistogram, false);
    }

    /**
     * @param img Image to decode
     * @return Decoded text
     */
    public String decode(PImage img) {
        return this.decode(img, true, false);
    }

    /**
     * @return Three points of QR code
     */
    public PVector[] getPoints() {
        if(result == null) {
            return null;
        }
        ResultPoint[] resultPoints = this.result.getResultPoints();
        PVector[] points = new PVector[resultPoints.length];
        for (int i = 0; i < resultPoints.length; i++) {
            points[i] = new PVector(resultPoints[i].getX(), resultPoints[i].getY());
        }
        return points;
    }

    /**
     * @return Decode result
     */
    public Result getResult() {
        return this.result;
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