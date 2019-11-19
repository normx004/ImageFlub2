package imageflubber;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;
import java.lang.*;
import java.io.*;

public class RemoteChooserClient {
	
	//  -Dprops=somefile
	// somefile:
	//   fileToPush=theFiletoMove
	//   fileToPushBase=\\dira\\dirb\\thedirofthefiletomove
	//   alt.rmi.addr=rmi://192.168.1.33:14049/RemoteAuditService
	//
		public  RemoteChooser raserver_     = null;
		public  RemoteChooser getServer() { return raserver_;}
	    private MainFlub fm = null;
	    
	 //---------------costructor---------------
	 public RemoteChooserClient (MainFlub fmm) {
	        fm = fmm;
	         // Get connection to server
	        this.getRaserver();
	        System.out.println("remoteChooserClient: Got server");
	}
	 
	 public String  getNextFilePath(String thePath){
		 out("remoteChooserClient: getting next file path, starting with "+thePath);
		 try {
		      String result = getServer().getNextFilePath(thePath);
		      out("remoteChooserClient: client called remote server got back '"+result+"'");
		      File4Img theFile = new File4Img (result);
		      File theDir = new File(theFile.getParent());
		      fm.setDir(theDir);
		      fm.setFile(theFile);
		      return result;
		 } catch (java.rmi.RemoteException re) {
			 System.err.println("remoteChooserClient: REmote exception "+re.getMessage());
			 re.printStackTrace(System.err);
		 }
		 return null; // only get here if it fails!
	 }

	//------------------STDPRINT--------------------------
	public void out(String s){System.out.println(s);}

	

	//--------------getRaserver-------------------------
	public void getRaserver() {
		   try {
			String uri     = new String("rmi://localhost:1099/RemoteChooser");
	      
			
			raserver_ = (RemoteChooser) Naming.lookup(uri);
	 	    System.out.println("remoteChooserClient: I have bound to " + uri);

		    }
		    catch (Exception e) {
		    	System.err.println("remoteChooserClient: oops, lookup failed: " + e);
			System.err.println("remoteChooserClient: message: " + e.getMessage());
			e.printStackTrace(System.err);
			System.exit(1);
		    }
		}	
		//---------------------MAIN----------------------------------
		public static void main (String args[]) {
		
			String userdir = System.getProperties().getProperty("user.dir");
			MainFlub fm = null;
			RemoteChooserClient rac = new RemoteChooserClient(fm);
			rac.out("remoteChooserClient: working dir is "+userdir);
			   
			
			
			// use a dummy-ish  directory for the test call
			File   theFile = new File ("c:\\temp\\junk");
			String fileName = theFile.getName();
			String   dirForFile = theFile.getParent();
			File   theDir = new File (dirForFile);
			rac.out("remoteChooserClient: Looking for " + fileName + " in " + dirForFile);
			if ( !theDir.exists()) {
				System.err.println("remoteChooserClient: Error, directory " + theFile.getAbsolutePath() + " is not found");
				//System.exit(1);
			} 
			if ( theDir.exists()) {
				rac.out("remoteChooserClient: " + theFile.getName() + " exists and isDirectory = "+theDir.isDirectory());
			}
			
			
			 
			 rac.out("Calling remoteChooser for "+theFile.getPath());
			 String result = rac.getNextFilePath(theFile.getPath());
			 rac.out("the result: " + result);
		}
	}


	
	
	
	


