package imageflubber;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.*;

import javax.imageio.IIOException;
//  this object controls the creating of image object to be used by the
//  display.  it executes in a separate thread, and processes the imagefile
//  linkedlist sequentially until the queue is full, then waits for space;
//  when space is available it adds an image to the back of the queue
//  

public class ImgQFiller extends Thread {
     private LinkedBlockingQueue<LoadImageApp> myLbq = null;
     private MainFlub fm = null;
     private LinkedList<File4Img> bigList = null;
     
     public ImgQFiller (MainFlub x) {
    	 fm = x;
    	 out("Constructing image q filler");
    	 if (fm == null) {
    		 out("Uh oh, the constructor got a 'null' MainFlub argument");
    	 }
    	 myLbq   = fm.getMyLbq();
    	 out("in imgqfiller constructor, myLbq   is "+myLbq.toString());
     }
     
     public void out(String s){if(fm.isDebug()) {System.out.println("ImgQFiller:"+s);}}
     public void uncout(String s){System.out.println("ImgQFiller:"+s);}
     public boolean firsttime = true;
     public void run () {
    	 boolean done = false;
    	 if (firsttime) {
    		 out("First Time!");
    		 firsttime=false;
    	 }
    	 
    	 while (!done) {   		 
    		 int listIdx = -1;
    		 try {
    			 bigList = fm.getBigList();
    			 int listSize = bigList.size();
    			 out("bigList size is "+listSize);
    			 listIdx = 0;
    			 int listRemaining = listSize-1;
    			 while (listIdx < listSize) {
    				 out("TOPTOP of while loop, list IDX is "+listIdx+", of list size "+listSize);
    				
    				 out("Loading img for image "+listIdx+" which is "+bigList.get(listIdx).getPath()+", myLbq.size() is "+myLbq.size());
    				 int sz = myLbq.size();
    				 boolean goodimage = false;
    				 LoadImageApp lia = null;
    				 try {
    					 lia = new LoadImageApp(bigList.get(listIdx));
    					 lia.setRemainingCount(listRemaining);
    					 listRemaining -= 1;
    					 goodimage = true;
    				 } catch (IIOException ie) {
    					 System.err.println("ERROR: failed to load image (IO) " + bigList.get(listIdx).getPath());
    					 //	bad format image.  maybe show a "invalid image" substitute?
    					 goodimage = false;
    					 listRemaining -= 1;
    				 } catch (NullPointerException npe) {
    					 // bad image formats sometimes cause iomageIO to return null
    					 System.err.println("ERROR: failed to load image (FORMAT) " + bigList.get(listIdx).getPath());
    					 //	bad format image.  maybe show a "invalid image" substitute?
    					 goodimage = false;    					 
    					 listRemaining -= 1;
    				 }
    				 
    				 if ( goodimage) {
    					 out(".....putting image, before queue size is "+sz);
    					 myLbq.put(lia);
    					 sz = myLbq.size();
    					 out(".....put image, list idx is " + 
    							 listIdx + ", biglist length " + 
    							 bigList.size() + ", after  queue size is "+
    							 sz + ", remaining in list: "+listRemaining);
    				 }else{
    					 out("Uh Oh, load image failed");
    				 }
    				 listIdx += 1;
    			 } // end 'while list idx < size of list'
    			    			 
    			 // 	ran out of the original file list...want to do another?
    			 int emph = 5;
    			 int ex   = 0;
    			 while (ex++ < emph) {
    				 out("Qfiller has hit end of bigList at number "+bigList.size());
    			 }
    			 // wait until image queue drains, so user is not confused by early 
    			 // directory chooser popup
    			 int qsz = myLbq.size();
    			 while ( qsz > 0 ) {
    				 Thread.sleep(1000);
    				 out("SLEEEEEEPING waiting for queue to drain. current size "+myLbq.size());
    				 qsz = myLbq.size();
    			 }
    			 
    			 out("Images-to-display queue has finally reached zero");
    			 // get another directory to work on
    			 GetDir gf = fm.getDirObj();
    			 fm.setFile(null);
    			 
    			 
    			 //gf.doit();
    			 //-----------do a file chooser--------------------
    			 gf.doitRemote();
    			 
    			 
    			 if (gf.isDone()) {
    				 done = true;
    			 }
    			 DirReader dr = fm.getDirReader();
    			 String fpath = fm.getDir().getPath();
    			 String fname = fm.getFile().getName();
    			 out("call dirreader with args "+fpath+", " + fname);
    			 dr.getList(fpath, fname);
    	 	 
    		 } catch (InterruptedException ie) {
    			 System.err.println("Uh Oh, ImgQFiller was interrupted: "+ie.getMessage());
    		 } // end "try" this list
    	 } // end 'while ! done'
      
    	 out("Qfiller has hit end of bigList at number "+bigList.size());
    	 fm.setQFillerDone(true);
     }
     
}
