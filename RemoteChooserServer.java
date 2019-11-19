package imageflubber;
import java.io.BufferedReader;
import java.awt.HeadlessException;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;


public class RemoteChooserServer implements RemoteChooser {
//	WARNING: RMIREGISTRY must see same classpath as server program!
	GetDir gd = new GetDir();

	public RemoteChooserServer() {
			out("______________________________________________________________________");
			out("______________________________________________________________________");
			out("______________________________________________________________________");
			out("Ok, have constructed remote chooser server object");
			out("______________________________________________________________________");
			out("______________________________________________________________________");
			out("______________________________________________________________________");
			
		} // end constructor

private void out(String s) { System.out.println(s);}
		
			
//------------------------------GET THE PATH------------------------------------
private String getThePath() {
	 JFileChooser fc = new JFileChooser() {
		 protected JDialog createDialog(Component parent) throws HeadlessException {
			 JDialog dialog = super.createDialog(parent);
			 dialog.setLocation(10,20);
			 return dialog;
		 }
     };
	 out("RemoteChooserServer: remote object about to create a new blank file chooser, but did we leave off somewhere?");
	 String lyne = gd.getLastFileRead();
	
	File parn = new File(lyne);
	out("RemoteChooserServer: parent of old path read in from file is "+parn.getParent());
	File parnparn = new File(parn.getParent());
	out("RemoteChooserServer: parent of parent is "+parnparn.getParent());
	File targetDir = new File(parnparn.getParent());
	 
	 //fc = new JFileChooser(parn);
	 fc.setCurrentDirectory(targetDir);
	 imageflubber.FileSystemView myFSV = new imageflubber.FileSystemView();
	 fc.setFileSystemView(myFSV);
	 fc.setFileFilter(new ImageFilter());
	 out("RemoteChooserServer: Setting selected file to "+parnparn.getPath());
	 // except this doesn't work
	 fc.setSelectedFile(parnparn);
	 //fc.setSelectedFile(targetDir);
	 fc.ensureFileIsVisible(targetDir);
 
 //fc.setLocation(100,90);
	 
 fc.showOpenDialog(null);
 
 
 out("RemoteChooserServer: returned from show file chooser");
 //fm.setDir(fc.getCurrentDirectory());
 //fm.setFile(fc.getSelectedFile());
 
 out("RemoteChooserServer: filechooser in GetDir returned directory "
		 +fc.getCurrentDirectory().getPath()
		 + ", file: "
		 +fc.getSelectedFile().getName());
 File f = new File(fc.getCurrentDirectory().getPath(), fc.getSelectedFile().getName());
 return f.getPath();
}
//---------------------------GETFILEPATH-------------
public String getNextFilePath( String fn) throws java.rmi.RemoteException {
	  File f = new File (fn);
	  String result = getThePath();
	  out("RemoteChooserServer: this is the remote obj,  got back '"+result+"'");
      return result;
}
//-----------------------------MAIN-------------------------------------
		public static void main (String args[]) {
			  // protected void getProps(String ps){
			System.out.println("RemoteChooserServer: USE:   java -classpath imageflubber.RemoteChooserServer.jar -p <propsfile> ");
			System.out.println("RemoteChooserServer: Remote Chooser Server starting.............................");
			String ps = new String(args[1]);
			System.out.println("RemoteChooserServer: props file name from args is "+ps);
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(ps));     
			} catch (IOException e) {	    	 
				System.err.println("RemoteChooserServer: Failed to read properties file " + ps + ", must quit!");
			    System.exit(1);
			}
			
			 System.out.println("RemoteChooserServer: Putting all props to system props");
			 Properties sysProps = System.getProperties();
			 sysProps.putAll(p);
			 System.out.println("RemoteChooserServer: hostname is " + System.getProperty("java.rmi.server.hostname"));
		    //String props = System.getProperties().getProperty("props");
			try {
				RemoteChooserServer x = new RemoteChooserServer();
				RemoteChooser stub = (RemoteChooser) UnicastRemoteObject.exportObject(x,0);
				System.out.println("RemoteChooserServer: Have constructed STUB");
				Registry registry = LocateRegistry.getRegistry();
				System.out.println("RemoteChooserServer: Have Located registry");
				registry.bind("RemoteChooser", stub);
				System.out.println("RemoteChooserServer: Server Ready");
			} catch (Exception e) {
				System.err.println("RemoteChooserServer: Error in main of server class: " +e.getMessage());
				e.printStackTrace(System.err);
				System.exit(1);
			}
		  }
}// end class
