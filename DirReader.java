package imageflubber;
import java.io.File;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;


public class DirReader {
	
	 File startDir = null;

	 int              lastIdx = -1;
	 MainFlub              fm = null;
	 String[]           filez = null;
	 private boolean beenHere = false;
	 private int    nextCount = 0;
	 //------------------------------------------------------------------------------------------
	 private void out (String s) { if ( fm.isDebug()/*|| true*/) {	System.out.println("DirReader: " + s); }}
	 private void uncout (String s) { 	System.out.println("DirReader: " + s); }
	 //-------------------------------NEXTFILE------------------------------------
	 public void  nextFile() {
		 uncout("++++++++++++++++++++++Entering 'nextFile', count " + ++nextCount+", lbq size is "+fm.getMyLbq().size());
		 fm.setIma(nextImg());
	 	}
	 //--------------------------------NEXTIMG---------------------------------------
	 public LoadImageApp nextImg() {
		 
		 LoadImageApp                      lia = null;		 
		 LinkedBlockingQueue<LoadImageApp> lbq = fm.getMyLbq();
		 
		 if (!beenHere) {
			 out("=================================nextImg first call");
			 beenHere = true;
			 while (lbq.isEmpty()) {
				 out("waiting for queue to fill === first time");
				 Thread myThread = Thread.currentThread();
				 try {
					  myThread.sleep(1000);
				 } catch (InterruptedException ex) {
					 System.err.println("Interupted in DirReader next file sleep");
				 }
			 }
			 try {
				 uncout("in first 'nextImg' call, queue size is "+lbq.size());
				 lia = lbq.take();
				 uncout("in first 'nextImg' call, lia is "+lia.toString());
				 return lia;
			 } catch (InterruptedException e) {
				 System.err.println("DIrReader: interrupted in first 'take' "+e.getMessage());
				 return null;
			 }
		 } 
			
		 
		 // ok, not the first time here
		 if (lbq.isEmpty() && fm.isQFillerDone()) {
					 uncout("lbq was empty!");
					 return null;
				 } 
		 if (lbq.isEmpty()) {
		     while (lbq.isEmpty()) {
						 uncout("waiting for queue to fill again");
						 Thread myThread = Thread.currentThread();
						 try {
							 myThread.sleep(1000);
						 } catch (InterruptedException ex) {
							 System.err.println("Interupted in DirReader next file sleep");
						 }
			   } // end while
		     
			   //Whew, finally got something
			   try {
			          uncout("after waiting for a while, lbq size is "+lbq.size());
		   		      lia = lbq.take(); 
		   		      int rem = lia.getRemainingCount();
		   		      if ( rem == 0) {
		   		    	  int a1 = 10;
		   		    	  int a2 = 0;
		   		    	  while (a2 < a1) {
		   		    		  out("WARNING---last image------");
		   		    		  a2 += 1;
		   		    	  }
		   		      }
			    } catch (InterruptedException e) {
					 System.err.println("DIrReader: interrupted in first 'take' "+e.getMessage());
					 return null;
			    }
			    return lia;
			 } 
			 //OK, Q not empty
		try {
				out("non-first nextImg call, lbq size is "+lbq.size());
				lia = lbq.take();
				int rem = lia.getRemainingCount();
				out("got next lia, file "+lia.getTheFile().getName()+ " number " + lia.getThisFileNumberInList()+", of "+lia.getFilesInDirCount());
	   		    if ( rem == 0) {
	   		      int a1 = 10;
	   		      int a2 = 0;
	   		      while (a2 < a1) {
	   		    	  out("WARNING---last image------");
	   		    	  a2 += 1;
	   		      }
	   		    }
			} catch (InterruptedException e) {
					 System.err.println("DIrReader: interrupted in first 'take' "+e.getMessage());
					 return null;
				 }
		 
		 return lia;
	 }
	 //--------------------------CONTSTRUCTOR-------------------------------
	 public DirReader(File f, MainFlub x) {
		 fm = x;
		 out("constructor1 with input file dir "+ f.getParent()+", and file name "+f.getName());
		 getList(f.getParent(), f.getName());
	 }
	 public DirReader(String dirName, MainFlub x)
	  {
		fm = x;
		out("constructor2 with directory name only "+dirName);
		getList(dirName, null);
		
	  }
	 // --------------GETLIST---------------------------------------------
	 // got a directory and starting file, now get the list of files
	 public void getList(String dirName, String _fname) {
	    File startDir = new File( dirName);
	    String fname = _fname;
	   
	   LinkedList<File4Img> bigList = new LinkedList<File4Img>();
	   fm.setBigList(bigList);
	   
	    try
	    {
	      
	      if ( startDir.isDirectory() ) {
	      	out("Ok, its a directory");
	      } else {
	      	out("aaaaaaaaaaah. NOT a directory");
	      	System.exit(1);
	      }	
	      filez = startDir.list();
	      out("directory " + dirName + " contains " + filez.length + " files or directories");
	      
	      int idx = 0;
	      
	      boolean okToAdd = false;	
	      int     addCount = 0;
          
	      while (idx < filez.length) {
	    	    String s = filez[idx];
	    	    if ( fname == null && idx == 0) {
	    	    	fname = s;
	    	    }
                
			 	int    i = s.lastIndexOf('.')+1;
			 	//out("String of next file is " + s + ", last dot is "+ i + ", length is " + s.length());
			 	String tail = s.substring(i, s.length());
			 	if ( tail.compareToIgnoreCase("jpg")==0 ||
			 		 tail.compareToIgnoreCase("png")==0 ||
			 		 tail.compareToIgnoreCase("jpeg")== 0 ||
			 		 tail.compareToIgnoreCase("bmp")==0){
			 
			 			//out("OK its a type we like");
			 			if (!okToAdd) {
			 				//out("looking for "+fname+", right now got "+s);
			 			}
			 			if ( s.compareToIgnoreCase(fname)==0) {
			 				out("ok --- "+s+" that's what we were looking for");
			 				okToAdd = true;
			 			}
			 			if (okToAdd) {
			 				File4Img fim = new File4Img(startDir, filez[idx]);
			 				fim.setFilesInDirCount(filez.length);
			 				fim.setThisFileNumberInList(idx);
			 				fm.getBigList().add(fim);
			 				//out("for file " + fim.getName() + ", number " + fim.getThisFileNumberInList() + " of " + fim.getFilesInDirCount());
			 				addCount += 1;
			 		    }
			 	}
			 	idx += 1;
	      } // end while more files in list
	      out("added " + addCount + " of " + idx + " files to biglist");
	     
	      }  catch (Exception e) {
	    	  System.err.println("error opening " + dirName + ": " + e.getMessage());
	    	  System.exit(1);
	      }
	      // OK, have populated the biglist. can start the q filler
	      out("Finished with Directory Scan");
	  }

	
}
