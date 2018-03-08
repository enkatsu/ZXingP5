import com.github.endoh0509.zxingp5.*;

QRWriter writer;
PImage img;

void setup() {
  size(200, 200);

  writer = new QRWriter(this);
   //writer.encode(EncodeString, QRCodeVersion);
  writer.encode("Hello QR Code!!", 2);
  img = writer.getImage(width, height);
  println(writer);
}

void draw() {
  image(img, 0, 0);
}