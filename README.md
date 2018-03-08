# ZXingP5
![top](https://github.com/endoh0509/ZXingP5/raw/master/img/top.png)

## Introduction

ZXingP5 is QR Code library for Processing.  
ZXingP5 is based on [ZXing](https://github.com/zxing/zxing).

## How to

### QRWriter

```
import enkatsu.zxingp5.*;

QRWriter writer;
PImage img;

void setup() {
  size(200, 200);

  writer = new QRWriter(this);
  // writer.encode(EncodeString, QRCodeVersion);
  writer.encode("Hello QR Code!!", 2);
  img = writer.getImage(width, height);
  println(writer);
}

void draw() {
  image(img, 0, 0);
}
 ```

### QRReader

```
import processing.video.*;
import enkatsu.zxingp5.*;

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
    String txt = reader.decord(cam, true);
    println(txt);
    PVector[] points = reader.getPoints();
    if (points != null) {
      for (PVector p : points) {
        println(p);
        ellipse(p.x, p.y, 10, 10);
      }
    }
  }
}
```

This software includes the work that is distributed in the Apache License 2.0
