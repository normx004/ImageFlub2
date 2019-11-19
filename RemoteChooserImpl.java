package imageflubber;
import java.io.*;
import java.lang.*;
import java.util.*;

public class RemoteChooserImpl extends java.rmi.server.UnicastRemoteObject 
  implements RemoteChooser {

   private        Properties   props_ = null; 
   
 //-----------------CONSTRUCTOR----------------------
 public RemoteChooserImpl() throws java.rmi.RemoteException {
    super();   
}
private void stdprint(String s) {
	System.out.println(s);
}

//---------------------------WRITEONE------------------------------
  public String getNextFilePath( String fn) throws java.rmi.RemoteException {
	  File f = new File (fn);
	  
	  String result = new String("c:\\temp");
             return result;
  }
	  
	  
  }; // end class
