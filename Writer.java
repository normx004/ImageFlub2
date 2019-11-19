package imageflubber;

import java.io.File;
import java.io.FileWriter;

public class Writer {
	
	public Writer() {
		
	}
	public void out(String s) { System.out.println("Writer: "+s);}
	
	public boolean tryWrite( String writeItPath, String drive1, String drive2, String stuffToWrite, boolean append) {
	    boolean done = false;
		boolean ok = false;
		int times = 0;
		//String drive[] = {"q:", "c:"};
		String drive[] = {drive1, drive2};
		String delFilePath = null;
		String lastDirPath = null;
		while (!done && times < 2) {
			String dval = drive[times];
		    //delFilePath    = new String(dval +"\\temp\\delum.bat");
		    delFilePath    = new String(dval +writeItPath);
			File batf   = new File(delFilePath);
			try {
				FileWriter fout = new FileWriter(batf, append);
				//String sa = new String("del \""+ f.getPath()+  "\"\n");
				String sa = stuffToWrite;
				fout.write(sa);
				fout.close();
				out("Wrote  "+stuffToWrite+" to "+delFilePath);
				done = true;
				ok = true;
			} catch (Exception e) {
				System.err.println("exception "+e.getMessage());
				//e.printStackTrace(System.err);
			}
			times += 1;
		} // while ! done
		if ( !ok) {
			System.err.println("Uh Oh, failed to write to "+delFilePath);
			return false;
		}
		return true;			
 }
}
