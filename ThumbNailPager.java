package imageflubber;
import java.lang.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;

public class ThumbNailPager {
	
	Graphics      g_ = null;
	BufferedImage result_ = null;

	public ThumbNailPager() {
		// TODO Auto-generated constructor stub
	}
	public void makeCanvas(int width, int height) {
	 result_ = new BufferedImage(
             width, height, //work these out
             BufferedImage.TYPE_INT_RGB);
     g_ = result_.getGraphics();
	}
	public void addThumbs(Vector<String> images) {
		int x = 0;
		int y = 0;
		BufferedImage bi = null;
		for(String image : images){
			try {
				bi = ImageIO.read(new File(image));
			} catch (IOException iox) {
				System.err.println("oops, couldn't read this image file: " + image + " "+ iox.getMessage());
			}
	        g_.drawImage(bi, x, y, null);
	        x += 256;
	        if(x > result_.getWidth()){
	            x = 0;
	            y += bi.getHeight();
	        }
	    }
	}
	public void writeCanvas(String resultFileName) {
		try {
			ImageIO.write(result_,"png",new File(resultFileName));
		} catch (IOException ioe) {
			System.err.println("IO Error writing thumb canvas to " + resultFileName + " " + ioe.getMessage());
		}
	}
	public  static void main(String[] args) {
		ThumbNailPager tnp = new ThumbNailPager();
		tnp.makeCanvas(1024,856);
		Vector<String> iV = new Vector<String>();
		iV.add(new String("c:\\temp\\test\\img1.jpg"));
		iV.add(new String("c:\\temp\\test\\img34.jpg"));
		iV.add(new String("c:\\temp\\test\\img85.jpg"));
		iV.add(new String("c:\\temp\\test\\img102.jpg"));
		iV.add(new String("c:\\temp\\test\\img27.jpg"));
		iV.add(new String("c:\\temp\\test\\img126.jpg"));
		iV.add(new String("c:\\temp\\test\\img120.jpg"));
		tnp.addThumbs(iV);
		tnp.writeCanvas("c:\\temp\\test\\Canvas.png");
		
	}
}
