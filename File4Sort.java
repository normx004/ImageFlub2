package imageflubber;

import java.io.File;

import java.net.URI;
import java.util.*;
import java.lang.*;

// mostly a data container for a sortable file

public class File4Sort implements Comparator<File4Sort>, Comparable<File4Sort> {
	
	public long lastMod;
	public File fyle;
	
	private boolean debug = true;

	
	public File4Sort(File arg0, long _lastmod) {
		 fyle = arg0;
		 lastMod = _lastmod;
	}
	public int compareTo (File4Sort d) {
		out("CompareToing " + this.fyle.getName() + " to "+ d.fyle.getName());
		//int result = (int)(this.lastMod - d.lastMod);
		long hey = d.lastMod - this.lastMod;
		int result = 0;
		if (hey > 0) {
			result = 1;
		} else if (hey < 0) {
			result = -1;
		}
		
		//reverse result
		if ( result == -1) {
			result = 1;
		} else {
			if (result == 1) {
				result = -1;
			}
		}
		
		
		
		out(".........."+result);
		//return (int) (this.lastMod - d.lastMod);
		return result;
	}
	
	private void out (String s) {
		if (debug) {
	    	System.out.println(s);
		}
	}
	
	public int compare(File4Sort d1, File4Sort d2) {
		out("Comparing " + d1.fyle.getName() + " to "+d2.fyle.getName());
		int result = (int)( d2.lastMod - d1.lastMod);
		//return (int)( d1.lastMod - d2.lastMod);
		out("..........."+result);
		return result;
	}

}
