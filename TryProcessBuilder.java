package imageflubber;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.io.IOException;
//mport javafx.application.*;

class TryProcessBuilder  {
	  private boolean isWindows = false;
	  private String cmdx_ = new String("start q:\\temp\\RunQdoit.bat");
	  private String cmd_ = cmdx_.replace('z', ' ');
	  //private String cmd_ = new String("start c:\\temp\\test.bat");
      public void out(String s) {
         System.out.println("TryProcessBuilder: " + s);
      }
      public void setW() {
    	  WorLinux w = new WorLinux();
    	  setIsWindows(w.izzit());
      }
      public TryProcessBuilder() {
    	  out("Void constructor for TryProcessBuilder");
    	  setW();
    	  //out("TryProcBld will execdute this command:"+cmd_);
      }
      public TryProcessBuilder(String newCmd) {
    	setW();
      	cmd_ = newCmd;
      	out("TryProcBld: one-arg constructor");
      	out("TryProcBld will execdute this command:"+cmd_);
      }
      public void doit() {
    	  out("no-arguments 'doit' entered");
    	  if (getIsWindows()) {
    		  out("doing it win");
    		  doitWin();
    	  } else {
    		  out("doing it linux");
    		  doitLinux();
    	  }
      }
      public void doitLinux() {
    	  out("Now in 'doitLinux'");
    	  
    	  try {
              // Change to the directory where your shell script is located
              ProcessBuilder pb = new ProcessBuilder("bash", "-c", "cd /tmp && chmod 777 delum.bash && /tmp/delum.bash");
              Process p = pb.start();
              // Wait for the script to finish executing
              int exitCode = p.waitFor();
              if (exitCode == 0) {
                  System.out.println("Shell script executed successfully! A");
              } else {
                  System.err.println("Shell script execution failed with exit code: " + exitCode);
              }
             
              
              out("now gonna read output from delum process");
              InputStream inputStream = p.getInputStream();
              BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
              StringBuilder output = new StringBuilder();
              String line;
              while ((line = reader.readLine()) != null) {
            	  out("from delum: "+line);
                  //output.append(line).append("\n");
              }
              out("Finished reading");
             
          } catch (IOException | InterruptedException e) {
              e.printStackTrace();
          }
    	  
    	  
    	 
      }
    	  
  
      public void doitWin() {
    	   out("now in 'doitWin");
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
    	  out("now in 'doit' with an array-list argument passed in");
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
	public boolean getIsWindows() {
		return isWindows;
	}
	public void setIsWindows(boolean isWindows) {
		this.isWindows = isWindows;
	}
      
}