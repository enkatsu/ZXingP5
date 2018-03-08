import processing.video.*;
import com.github.endoh0509.zxingp5.*;

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
    tint(255, 255);
    image(cam, 0, 0);
    String txt = reader.decode(cam);
    if(txt != null){
      println(txt);
    }
  }
}