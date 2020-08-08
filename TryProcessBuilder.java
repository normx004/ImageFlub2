package imageflubber;

import java.lang.*;
import java.util.*;
import java.io.*;
//mport javafx.application.*;

class TryProcessBuilder  {
	  private String cmd_ = new String("start q:\\bin\\RunQdoit.bat");
	  //private String cmd_ = new String("start c:\\temp\\test.bat");
      public void out(String s) {
         System.out.println("TryProcessBuilder: " + s);
      }
      public TryProcessBuilder() {
    	  out("Void constructor for TryProcessBuilder");
    	  //out("TryProcBld will execdute this command:"+cmd_);
      }
      public TryProcessBuilder(String newCmd) {
      	cmd_ = newCmd;
      	out("TryProcBld: one-arg constructor");
      	out("TryProcBld will execdute this command:"+cmd_);
      }
      public void doit() {
           List<String> cmds = new ArrayList<String>();
           cmds.add("cmd.exe");
           cmds.add("/C");
           cmds.add(cmd_);
           out("OK: here's what's being passed to processbuilder....");
           int k = cmds.size();
           int j = 0;
           while (j < k) {
        	   out(": cmds[" + j + ": " + cmds.get(j));
        	   j +=1;
           }
           out("OK---all done--------------------------------------");
           
           ProcessBuilder pb = new ProcessBuilder(cmds);
           try {
					 pb.start();
					} catch (IOException ioe) {
						out("Uh Oh, IO.   msg:"+ioe.getMessage());
					}
      }
      public void doit(ArrayList<String> doWhat) {
          List<String> cmds = new ArrayList<String>();
          cmds.add("cmd.exe");
          cmds.add("/C");
          int j  = doWhat.size();
          out("Input array of cmds is of size "+j);
          int k = 0;
          StringBuffer cmdStrings = new StringBuffer();
          while (k < j) {
        	  cmdStrings.append(doWhat.get(k));
        	  cmdStrings.append(" ");
        	  out("Adding "+doWhat.get(k));
        	  cmds.add(doWhat.get(k));
        	  k += 1;
          }
          boolean usePB = true;
          //out("Just added '"+cmds+"'");.0
          if (usePB) {
        	  ProcessBuilder pb = new ProcessBuilder(cmds);
        	  try {
					 File ctemp = new File("c:\\temp\\undoit.log");
					 pb.redirectOutput(ctemp);
					 pb.start();
					 //BufferedReader br=new BufferedReader(
					 //           new InputStreamReader(
					 //              pb.getInputStream()));
					 //           String line;
					 //           while((line=br.readLine())!=null){
					 //              System.out.println(line);
					 //           }
					} catch (IOException ioe) {
						out("Uh Oh, IO.   msg:"+ioe.getMessage());
					}
          }  else {
        	  ExecWrapper e = new ExecWrapper(cmdStrings.toString());
        	  e.init();
        	  e.doit();
          }
     }
      
      public static void main(String[] args) {
        TryProcessBuilder tp = new TryProcessBuilder();
        tp.doit();         
      }
      
}