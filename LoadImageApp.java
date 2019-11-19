package imageflubber;


	import java.awt.*;

	import java.awt.image.*;
	import java.io.*;
	import javax.imageio.*;
	import javax.swing.*;
	import net.coobird.thumbnailator.*;
	import net.coobird.thumbnailator.Thumbnails.*;

	/**
	 * This class demonstrates how to load an Image from an external file
	 */
	public class LoadImageApp extends JComponent {
	          
		
		private boolean debug = false;
	    public boolean isDebug() {			return debug;		}
		public void setDebug(boolean debug) {			this.debug = debug;		}
		
		
		BufferedImage img;
	    private            File4Img theFile  = null;
	    public             File4Img getTheFile() {return theFile;}
	    
		public void        setTheFile(File4Img theFile) {this.theFile = theFile;}
/*
		private int imagesAfterMe = 0;
		public int getImagesAfterMe() {	return imagesAfterMe;	}
		public void setImagesAfterMe(int imagesAfterMe) {this.imagesAfterMe = imagesAfterMe;}
*/

		private int  originalW = 0;
		private int  originalH = 0;
	    private long originalSize = 0;
	    private int  filesInDirCount = 0;
	    private int  thisFileNumberInList = 0;
	    private int  remainingCount = 0;
	    
	    private int  filesSaved = 0;
	    private int  filesDeleted = 0;	    
	    private int  filesMoved = 0;
	    private int  filesCopyed = 0;
	    
	    CountCarrier k = CountCarrierFactory.getCarrier();
	    public int  getFilesSaved() {return k.getFilesSaved();}
		public void setFilesSaved(int filesSaved) {	k.setFilesSaved(filesSaved);}
		public int  getFilesDeleted() {	return k.getFilesDeleted();}
		public void setFilesDeleted(int filesDeleted) {	k.setFilesDeleted(filesDeleted);}
		public int  getFilesMoved()   {	return k.getFilesMoved();}
		public void setFilesMoved(int filesDeleted) {	k.setFilesMoved(filesDeleted);}
		public int  getFilesCopyed()   {	return k.getFilesCopyed();}
		public void setFilesCopyed(int filesCopyed) {	k.setFilesCopyed(filesCopyed);}
		
		public void bumpBytesDeleted (long count) {
			long tCount = k.getBytesDeleted();
			tCount += count;
			k.setBytesDeleted(tCount);
		}
		public long getBytesDeleted ( ) {
			return k.getBytesDeleted();
		}
	

	    public int getOriginalW() {
			return originalW;
		}
		public void setOriginalW(int originalW) {
			this.originalW = originalW;
		}
		public int getOriginalH() {
			return originalH;
		}
		public void setOriginalH(int originalH) {
			this.originalH = originalH;
		}
		public long getOriginalSize() {
			return originalSize;
		}
		public void setOriginalSize(long originalSize) {
			this.originalSize = originalSize;
		}
		public String toString() {
			StringBuffer me = new StringBuffer("");
			me.append("h " + originalH + ", w "+originalW + ", size "+originalSize + ", name "+theFile.getName() + " is "+ thisFileNumberInList + " of "+ filesInDirCount);
			return me.toString();
		}
        //-------------------------------------------------------
	    public void paint(Graphics g) {
	    	//super.paint(g);
	    	//out("graphics in Paint: "+g.toString());
	    	//out("{{{{{{LoadImageApp{{{{{{{{{{{paint Called}}}}}}}}}}}}}}}}}}}}");
	    	
	    	
	    	//------ just for fun-------------
	    	/*
	    	Thread t = Thread.currentThread();
	    	StackTraceElement ste[] = t.getStackTrace();
	        int loopx = ste.length;
	        int stx = 0;
	        while (stx < loopx) {
	        	out("stack: "+ste[stx].toString());
	        	stx += 1;
	        }
	        */
	        //-------------------------------
	        
	        
	    	g.drawImage(img, 0, 0, null);
	    	//super.repaint();
	    }
	    public void paintComponent(Graphics g) {
	    	out("graphics in PaintComponent: "+g.toString());
	    	out("{{{{{{LoadImageApp{{{{{{{{{{{{paintComponent Called}}}}}}}}}}}}}}}}}}}}");
	        g.drawImage(img, 0, 0, null);
	    }
        //-----------------------DEFAULT CONSTRUCTOR-------------------------------------
	    public LoadImageApp() {
	       try {
	           img = ImageIO.read(new File("c:\\temp\\earthx.jpg"));
	       } catch (IOException e) {
	       }

	    }
	    
	    public void scaleIt() {
	    	boolean DOIT = false;
	    	if (DOIT) {
	    	// Create new (blank) image of required (scaled) size
	    	int width = 1200;
	    	int height = 800;
	    	BufferedImage scaledImage = new BufferedImage(	width, height, BufferedImage.TYPE_INT_ARGB);
	    	 
	    	// Paint scaled version of image to new image
	    	 
	    	Graphics2D graphics2D = scaledImage.createGraphics();
	    	graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    	graphics2D.drawImage(img, 0, 0, width, height, null);
	    	img = scaledImage;
	    	 
	    	// clean up
	    	 
	    	graphics2D.dispose();
	    	}
	    }
	    public void scaleItfixed() {
	    	//out("doing a 'scale it fixed' to 1200,800");
	    	try {
	    	     BufferedImage thumbnail = Thumbnails.of(img).size(1200, 800).asBufferedImage();
	    	     img = thumbnail;
	    	} catch (IOException ioe) {
	    		System.err.println("io error: "+ioe.getMessage());
	    		ioe.printStackTrace();
	    	}
	    }
	    public void scaleItRatio() {
	    	//out ("doing a 'scale it ratio' to .25");
	    	try {
	    		BufferedImage thumbnail = Thumbnails.of(img).scale(0.25f).asBufferedImage();
	    		img = thumbnail;
	    	} catch (IOException ioe) {
	    		System.err.println("io error: "+ioe.getMessage());
	    		ioe.printStackTrace();
	    	} catch (Exception ex) {
	    		System.err.println("io error: "+ex.getMessage());
	    		ex.printStackTrace();
	    	}
	    	
	    	//out ("done with ratio scale for fixed .25");
	    }
	    public void scaleItRatio(float rat) {
	    	//out ("doing a 'scale it ratio' with input ratio "+rat);
	    	try {
	    		BufferedImage thumbnail = Thumbnails.of(img).scale(rat).asBufferedImage();
	    		img = thumbnail;
	    		//out("image has been scaled!");
	    	} catch (IOException ioe) {
	    		System.err.println("io error: "+ioe.getMessage());
	    		ioe.printStackTrace();
	    	}catch (Exception ex) {
	    		System.err.println("io error: "+ex.getMessage());
	    		ex.printStackTrace();
	    	}
	    	//out ("Done with scaleit (input ratio)");
	    }
	    
	  //-----------------------CONSTRUCTOR-------------------------------------
	    
	    public LoadImageApp(File4Img f)throws IIOException, NullPointerException {
	    	   super();
	    	   
	    	   setDebug(MainFlub.isStaticDebug());
	    	   //setDebug(true);
	    	   out("+++++++++++entering LoadImageApp(file) constructor---------------" + f.getAbsolutePath() + " is to be loaded into img");
	    	   setTheFile(f);
	    	   this.setFilesInDirCount(f.getFilesInDirCount());
	    	   this.setThisFileNumberInList(f.getThisFileNumberInList());
	    	   out("just created LIA with filename "+f.getName()+", number "+this.getThisFileNumberInList()+" of "+this.getFilesInDirCount());
	    	   Thread t = Thread.currentThread();
	    	   //t.dumpStack();
		       try {
		           img = ImageIO.read(f);
		          
		           int w = img.getWidth(null);
		           int h = img.getHeight(null);
		           //out("Loading img in (file) construcgture, H:    " + h);
		           //out("Loading img in (file) construcgture, W:    " + w);
		           //out("Loading img in (file) construcgture, size: " + f.length());
		           setOriginalW(w);
		           setOriginalH(h);
		           setOriginalSize(f.length());
		           
		           if ((w > 1200)|| h >800) {
		        	  
		        	  //scale to 1200 if very wide or 800 if very high
		        	  //but in no case get an image taller than 800
		        
		              double rat = 0.0;
		              double wid = (double) w;
		              double hig = (double) h;
		              double scaleTo = 800;
		              if ( hig > 800) {
		            	//out("higher than 800 so scaling based on img width");
			            rat = scaleTo/hig;  
		              } else {
		            	//out("scaling based on img height");
		            	scaleTo = 1200.0;
		                rat = scaleTo/wid;
		              } // end 'if wide or tall'
		              scaleItRatio((float)rat);
		           }
		       } catch (IIOException ie ) {
		    	   System.err.println("Image load exception "+ie.getMessage());
		    	   ie.printStackTrace( System.err);
		    	   throw ie;
		    	   
		       } catch (IOException e) {
		    	   System.err.println("IO Error loading image "+e.getMessage());
		    	   e.printStackTrace(System.err);
		    	   
		       } catch ( NullPointerException npe) {
		    	   System.err.println("--------------must be invalid image!!!!!!!");
		    	   npe.printStackTrace(System.err);
		    	   throw npe;
		       }       
		       
              uncout("+++++++++exiting LoadImageApp(file) construtor, img number " + this.getThisFileNumberInList() + " of " + this.getFilesInDirCount());
		    }
	    
	    public void loadNewImage(File f){
	    	   out("-----entering loadNewImageFile-------------" + f.getAbsolutePath() + " is to be loaded into img");
		       try {
		             img = ImageIO.read(f);
		             int w = img.getWidth(null);
			         int h = img.getHeight(null);
			         //out("Loading img in (file) loadNewImage,  H: " + h);
			         //out("Loading img in (file) loadNewImage , W: " + w);
			         // if image is larger than 1200x800, scale it down
			         // if landscape, scale width relative to 1200
			         if (h < 1201 && w < 801) {
			        	 return;
			         }
			         if (w > h) {
			        	 if (w > 1200) {
			        		 double rat = 0.25;
			        		 double wid = (double) w;
			        		 double scaleTo = 1200.0;
			        		 rat = scaleTo/wid;
			        		 //scaleIt();
			        		 //scaleItfixed();
			        		 scaleItRatio((float)rat);
			        	 }
			         }else{
			        	 // if portrait scale height relative to 800
			        	 if (h > w || h == w) {
			        		 if (h > 800) {
				        		 double rat = 0.25;
				        		 double hi = (double) h;
				        		 double scaleTo = 800.0;
				        		 rat = scaleTo/hi;
				        		 //scaleIt();
				        		 //scaleItfixed();
				        		 scaleItRatio((float)rat);
			        		 	}
			        	 }
			         }
		           } catch (IOException e) {
		        	   System.err.println("Uh OH "+e.getMessage());
		        	   System.exit(1);
		           }
              
		    }
	    //-------------------------------------------------------------------
	    public void out(String s) { if (isDebug()){ System.out.println("LoadImageApp:" +s);}}
	    public void uncout(String s) {  System.out.println("LoadImageApp:" +s);}
	    public Dimension getPreferredSize() {
	        if (img == null) {
	             return new Dimension(100,100);
	        } else {
	        	
	        	  //Thread t = Thread.currentThread();
	             // t.dumpStack();
	              
	           int w = img.getWidth(null);
	           int h = img.getHeight(null);
	           //out("h/w is "+ h + "/" +w);
	           /*
	           float rat = (float) w / (float) h;
	           out("ratio w to h is "+rat);
	           //float wid = (float)1200.;
	           int   neww = (int) (800.0 * rat);
	           out("new width is "+neww);
	           w = neww;
	           h = 800;
	           */
	           return new Dimension(w,h);
	           //return new Dimension(img.getWidth(null), img.getHeight(null)/*+75*/);
	       }
	    }
		public void setThisFileNumberInList(int _thisFileNumberInList) {
			this.thisFileNumberInList = _thisFileNumberInList;
		}
		public int getThisFileNumberInList() {
			return thisFileNumberInList;
		}
		public void setFilesInDirCount(int _filesInDirCount) {
			this.filesInDirCount = _filesInDirCount;
		}
		public int getFilesInDirCount() {
			return filesInDirCount;
		}
		public void setRemainingCount(int remainingCount) {
			this.remainingCount = remainingCount;
		}
		public int getRemainingCount() {
			return remainingCount;
		}
}
