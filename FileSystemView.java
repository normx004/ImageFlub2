package imageflubber;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;

import java.util.*;
import java.lang.*;

//import javax.swing.filechooser.FileSystemView;

public class FileSystemView extends javax.swing.filechooser.FileSystemView {
	
	private boolean debug = true;

	public FileSystemView() {
		// TODO Auto-generated constructor stub
	}
	public File createNewFolder(File f) {
		System.out.println("trying to createnew folder in file system view class:  " + f.getName());
		File g = new File(f.getName());
		return g;
	}
	private void out (String s) {
		if (debug) {
			System.out.println(s);
		}
	}
	
	public File[] getFiles(File dir,  boolean useFileHiding) {
		
		File [] dirList = dir.listFiles();
		// return list in last-modified-first order.
		//
		// -------except...the chooser UI shows it in 
		// -------oldest first anyway! so why bother!
		//
		//
		/*
		int k = dirList.length;
		out("Reading "+k+" file system entries");
		File [] theList = new File[k];
		List <File4Sort> flist = new ArrayList <File4Sort>(k);
		int idx=0;
		while (idx < k) {
			File4Sort f = new File4Sort(dirList[idx], dirList[idx].lastModified());
			flist.add(f);
			out("in getFiles, collection file list, Added: "+f.fyle.getName());
			idx += 1;
		}
		
		Collections.sort(flist);
		
		idx =0;
		for ( File4Sort fli: flist) {
			out("Adding to File List to return to GUI: "+fli.fyle.getName());
			theList[idx] = fli.fyle;
			idx += 1;
		}
		
		return theList;
		*/
		return dirList;
	}

}
