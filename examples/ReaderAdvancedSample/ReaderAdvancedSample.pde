import processing.video.*;
import org.enkatsu.zxingp5.*;

Capture cam;
QRReader reader;

void setup() {
  size(320, 240);

  cam = new Capture(this, 320, 240);
  cam.start();
  reader = new QRReader(this);
}

void draw() {
  if (cam.available() == true) {
    cam.read();
    image(cam, 0, 0);
    tint(255, 200);
    String txt = reader.decode(cam);
    if (txt != null) {
      PVector[] points = reader.getPoints();
      PVector lowerLeft = points[0];
      PVector upperLeft = points[1];
      PVector upperRight = points[2];

      // Get QR Code position
      PVector vec = PVector.sub(lowerLeft, upperRight);
      PVector center = PVector.mult(vec, 0.5).add(upperRight);
      
      // Get QR Code angle
      PVector qrVertical = PVector.sub(upperLeft, lowerLeft);
      PVector camVertical = new PVector(0, 1);
      float angle = atan2(qrVertical.y - camVertical.y, qrVertical.x - camVertical.x);
      if (angle < 0) { 
        angle += TWO_PI;
      }
      
      // Get QR Code scale
      float scale = PVector.dist(upperLeft, upperRight);
      
      noStroke();
      fill(255, 200);
      rectMode(CENTER);
      pushMatrix();
      translate(center.x, center.y);
      rotate(angle);
      rect(0, 0, scale, scale);
      popMatrix();
      
      textAlign(CENTER, CENTER);
      textSize(scale);
      fill(#193EA2);
      text(txt, center.x, center.y);
    }
  }
}

float myAngleBetween (PVector myPVector1, PVector myPVector2) {
  float a = atan2(myPVector1.y-myPVector2.y, myPVector1.x-myPVector2.x);
  if (a<0) { 
    a+=TWO_PI;
  }
  return a;
}