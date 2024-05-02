package imageflubber;

import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;

class Graphics2D01{
	int resx;
	int widthx;
	int heightx;

  //public static void main(String[] args){
  public Graphics2D01 () {
       GUI guiObj = new GUI();
       setWidthx(guiObj.getWidth());
       setHeightx(guiObj.getHeight());
  }//end main

public int getWidthx() {
	return widthx;
}
public void setWidthx(int widthx) {
	this.widthx = widthx;
}
public int getHeightx() {
	return heightx;
}
public void setHeightx(int heightx) {
	this.heightx = heightx;
}
}//end controlling class Graphics2D01


class GUI extends Frame{
  int res;//store screen resolution here
  int width;//store screen width here
  int height;//store screen height here

  GUI(){//constructor
    //Get screen resolution, width, and height
    res = Toolkit.getDefaultToolkit().
    getScreenResolution();
    width = Toolkit.getDefaultToolkit().
    getScreenSize().width;
    height = Toolkit.getDefaultToolkit().
    getScreenSize().height;

    //Display screen resolution,
    // width, and height.

    //System.out.println("Graphics2D01: " + res + " pixels per inch");
    //System.out.println("Graphics2D01: " +width + " pixels wide");
    //System.out.println("Graphics2D01: " +height + " pixels high");

/*
    //Set Frame size to two-inch by two-inch

    this.setSize(2*res,2*res);

    this.setVisible(true);

    this.setTitle("opyright 1999, R.G.Baldwin");


    //Window listener to terminate program.

    this.addWindowListener(new WindowAdapter(){

      public void windowClosing(WindowEvent e){

        System.exit(0);}});
*/
  }//end constructor


public int getRes() {
	return res;
}
public int getWidth() {
	return width;
}
public int getHeight() {
	return height;
}

  //Override the paint() method to draw a one-inch by
  // one-inch rectangle centered in the Frame.
  /*
  public void paint(Graphics g){

    //Downcast the Graphics object to a Graphics2D object
    // to make the features of the Graphics2D class
    // available
    Graphics2D g2 = (Graphics2D)g;
    //Instantiate and draw an object of the
    // Rectangle2D.Double class that is centered in the
    // Frame and is one inch on each side.  Center the
    // rectangle in the Frame by placing its upper left-
    // hand corner at a position that is one-half inch to
    // the right and one-half inch below the upper left-hand
    // corner of the Frame.  Note that the screen
    // resolution in pixels per inch is used to establish
    // the location and size of the rectangle in inches.
    g2.draw(new Rectangle2D.Double( res*0.5,res*0.5,res*1.0,res*1.0));
  }//end overridden paint()
*/
}//end class GUI

//===================================//

