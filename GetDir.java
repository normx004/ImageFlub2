package imageflubber;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import javax.swing.*;



public class GetDir  {
	
	private boolean done = false;
	public boolean  isDone() {	return done;}
	public void     setDone(boolean done) {	this.done = done;}
	
	 JFileChooser fc = null;
	 
	 int timez = 0;



	private MainFlub fm = null;
	public void out(String s) { 
		//outAll(s);
		if (fm == null ) {
			System.out.println("GetDirA: "+s);
			return;
		}
		if (fm.isDebug()/*|| true)*/) {
			System.out.println("GetDirB: "+s);
		}
	}
	public void outAll(String s) {
		System.out.println("GetDirC: "+s);
	}
	
	public GetDir(MainFlub afm) {
		fm = afm;
		out("GetDir: GetDir constructor");
		this.doit();
	}
	public GetDir() {
		out("GetDir: To be used ONLY for access to 'getLastFileRead' method....");
	}
	
	public void doitRemote() {
		RemoteChooserClient rc = new RemoteChooserClient(fm);
		rc.getNextFilePath(new String("Dummy"));
	}
	
	public void doit() {
	     // FilePath can be entered on command line as "arg0" but normally will have
	     // a filechooser do the work
	    
	     if (fm.getFile() != null) {
	    	 File4Img z = fm.getFile();
	    	 if ( z.isDirectory() ) {
	    		 out("GetDir: ()()()()()()()()()()()(---doit passed a DIRECTORY----"+z.getPath()+")()()()()()()))()()()()");
	    		 fm.setDir(z);
	    		 fm.setFile(null);
	    	 } else {
	    		 File4Img d = new File4Img(z.getParent());
	    		 fm.setFile(z);
	    		 fm.setDir(d);
	    		 DirReader dr = new DirReader(d.getPath(),fm);
	    		 fm.setDirReader(dr);
	    	 }
	    	 return;
	     } else {
	    	 out("GetDir: fm.getFile() returned NULL, popping up a file browser");
	     }
	     
	     String oldPath = null;
	     File   oldFile = fm.getDir();
	     if ( oldFile != null) {
	    	 oldPath = fm.getDir().getPath();
	    	 out("vbefore file chooser created, fm dir is "+fm.getDir().getPath());
	     } else {
	    	 out("GetDir: Old File (from fm.getDir() was null");
	     }
	     String approve = new String("OK?");
	    
	     // start where we left off, if any previous loc found
	     if ( oldPath != null) {
	    	 out("GetDir: Creating chooser from "+oldPath);
	    	 File oldp = new File(oldPath);
	    	 File parnt = new File(oldp.getParent());
	    	 out("parent of old path is "+parnt.getPath());
	    	 
	    	 //fc = new JFileChooser(parnt);
	    	 fc.setCurrentDirectory(parnt);
	    	 //out("Setting position with 'ensure visible' of "+oldp.getPath());
	    	 // except this doesn't work
	    	 fc.setSelectedFile(oldp);
	    	 fc.ensureFileIsVisible(oldp);
	     } else {
	    	 fc = new JFileChooser();
	    	 fc.setFileFilter(new ImageFilter());
	    	 out("GetDir: about to create a new blank file chooser, but did we leave off somewhere?");
	    	 /*
	    	 boolean done = false;
	 		 boolean ok = false;
	 		 int times = 0;
	 		String drive[] = {"q:", "c:"};
	 		//String drive[] = {drive1, drive2};
	 		String lastDirPath = null;
	 		File batf = null;
	 		String lyne = null;
	 		while (!done && times < 2) {
	 			String dval = drive[times];
	 		    String readFilePath    = new String(dval +"\\temp\\lastGoodFile.txt");
	 		    batf   = new File(readFilePath);
	 		
	 			try {
	 				FileReader fin = new FileReader(batf);
	 				BufferedReader br = new BufferedReader(fin);
	 			   lyne = br.readLine();
	 				fin.close();
	 				out("Read  "+lyne);
	 				done = true;
	 				ok = true;
	 			} catch (Exception e) {
	 				System.err.println("exception "+e.getMessage());
	 				//e.printStackTrace(System.err);
	 			}
	 			times += 1;
	 		} // while ! done
	 		if ( !ok) {
	 			System.err.println("Uh Oh, failed to read to "+ batf.getPath());
	 			
	 		}
	 		*/
	    	String lyne = this.getLastFileRead();
	    	if (lyne==null) {
	    		out("GetDir: no 'last file' so defaulting to a documents\\doc file");
	    		lyne = new String("C:\\Users\\Norm\\Documents\\doc\\BikeInBY.jpg");
	    	}
	 		out("GetDir: Looking at file "+lyne);
	 		File parn = new File(lyne);
	    	out("GetDir: parent of old path read in from file is "+parn.getParent());
	    	 
	    	 //fc = new JFileChooser(parn);
	    	 fc.setCurrentDirectory(parn);
	    	 out("GetDir: Setting selected file to "+parn.getPath());
	    	 // except this doesn't work
	    	 fc.setSelectedFile(parn);
	    	 //fc.ensureFileIsVisible(new File(parn.getParent()));
	    	 fc.ensureFileIsVisible(new File(parn.getPath()));
	     }
	     
	     // Wait until sure that queue is drained.....
	     LinkedBlockingQueue<LoadImageApp> myLbq = null;
	     myLbq   = fm.getMyLbq();
	     int qsz = myLbq.size();
	     out("GetDir: initial queue size is "+qsz);
	     try {
	    	  while ( qsz > 0 ) {
  	             Thread.sleep(1000);
			     out("GetDir: SLEEEEEEPING waiting for queue to drain b4 file chooser. current size "+qsz);
			     qsz = myLbq.size();
	    	 }
		 } catch ( InterruptedException ie) {
			 out ("GetDir: waiting for queue drain before file chooser pop, interrupted... "+ie.getMessage());
		 }
	
         fc.showOpenDialog(/*frame*/null);
	     out("GetDir: returned from show file chooser");
	     fm.setDir(fc.getCurrentDirectory());
	     out("GetDir: according to filechooser, current dir is "+fc.getCurrentDirectory());
	     File4Img fim = new File4Img(fc.getSelectedFile().getPath());
	     out("GetDir: File with first image is "+fim.getName());
	     fm.setFile(fim);
	     
	     out("GetDir: filechooser in GetDir returned directory "+fc.getCurrentDirectory()+ ", file: "+fc.getSelectedFile());
	     out("GetDir: got chooser full dir path is "           + fm.getDir().getPath());
		
	}
	
public String getLastFileRead() {
	    boolean isWindows = fm.isWindows();
	    boolean done = false;
		boolean ok = false;
		int times = 0;
		String drive[] = {"q:", "c:"};
		//String drive[] = {drive1, drive2};
		String lastDirPath = null;
		File batf = null;
		String lyne = null;
		String readFilePath = null;
		if ( isWindows) {
		  while (!done && times < 2) {
		  	  String dval = drive[times];
		      readFilePath    = new String(dval +"\\temp\\lastGoodFile.txt");
		      outAll("GetDir: getLastFileread Looking for "+readFilePath);
		      batf   = new File(readFilePath);
		/*
			  try {
				  FileReader fin    = new FileReader(batf);
				  BufferedReader br = new BufferedReader(fin);
			      lyne = br.readLine();
				  fin.close();
				  outAll("GetDir: Read  "+lyne);
				  done = true;
				  ok   = true;
			  } catch (Exception e) {
				  System.err.println("GetDir: LastGoodFile read exception "+e.getMessage());
				  //e.printStackTrace(System.err);
			  }
	     */
		      lyne = getFilePath(batf);
		      if ( lyne == null ) {
		    	  ok = false;
		      } else {
		    	  done = true;
		    	  ok   = true;
		    	  out("setting 'done' to TRUE");
		      }
			  times += 1;
		  } // while ! done
		  if ( !ok) {
			  System.err.println("GetDir: Uh Oh, failed to read to "+ batf.getPath());
		  }
		}  else {     //end of "if running on windows"; must be linux
	       	readFilePath = new String("/tmp/lastGoodFile.txt");
	       	batf         = new File(readFilePath);
	       	lyne         = getFilePath(batf);
		}
		return lyne;
	}
private String getFilePath (File batf) {
	  String lyne = null;
	  try {
		  FileReader fin    = new FileReader(batf);
		  BufferedReader br = new BufferedReader(fin);
	      lyne = br.readLine();
		  fin.close();
		  outAll("GetDir: from " + batf.getAbsolutePath()  + " I have Read  "+lyne);
	  } catch (Exception e) {
		  System.err.println("GetDir: LastGoodFile read exception "+e.getMessage());
		  //e.printStackTrace(System.err);
	  }
	  return lyne;
}
	
} // end class
