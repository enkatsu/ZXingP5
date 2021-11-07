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
  if (cam.available()) {
    cam.read();
    tint(255, 255);
    image(cam, 0, 0);
    String txt = reader.decode(cam);
    if (txt != null) {
      println(txt);
    }
  }
}
