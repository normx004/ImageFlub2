package imageflubber;

import java.lang.*;
import java.util.*;
import java.io.*;
//mport javafx.application.*;

class TryProcessBuilder  {
	  private String cmd_ = new String("start q:\\bin\\RunQdoit.bat");
	  //private String cmd_ = new String("start c:\\temp\\test.bat");
      public void out(String s) {
         System.out.println(s);
      }
      public TryProcessBuilder() {
    	  out("Void constructor for TryProcessBuilder");
    	  out("TryProcBld will execdute this command:"+cmd_);
      }
      public TryProcessBuilder(String newCmd) {
      	cmd_ = newCmd;
      	out("TryProcBld will execdute this command:"+cmd_);
      }
      public void doit() {
           List<String> cmds = new ArrayList<String>();
           cmds.add("cmd.exe");
           cmds.add("/C");
           cmds.add(cmd_);
           //out("Just added '"+cmdString+"'");
           ProcessBuilder pb = new ProcessBuilder(cmds);
           try {
					 pb.start();
					} catch (IOException ioe) {
						out("Uh Oh, IO.   msg:"+ioe.getMessage());
					}
      }
      
      public static void main(String[] args) {
        TryProcessBuilder tp = new TryProcessBuilder();
        tp.doit();         
      }
      
}