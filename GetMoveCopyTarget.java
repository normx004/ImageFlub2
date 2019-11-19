package imageflubber;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.*;

public class GetMoveCopyTarget {
	private MainFlub fm = null;
    private boolean  isCopy = true; // if not, is MOVE
    private String   dirType = null;
    private void     out(String s) { System.out.println(s); }
    Vector<String>   usedPaths = null;
    
    public GetMoveCopyTarget() {
    	//convenience constructor in cast I need the getLIstFromPRops subroutine
    }
	 //------------------------SAVEmoveLine - that is, save the move/copy command--------------------
    public GetMoveCopyTarget(String mOrC, MainFlub _fm) {
    	fm = _fm;
    	dirType = mOrC;
    	if (mOrC.compareToIgnoreCase("move") == 0) {
    		isCopy = false;
    		out("GetMoveCopyTarget: working on a move");
    	} else {
    		if (mOrC.compareToIgnoreCase("copy") == 0) {
    			//no change
    			out("GetMoveCopyTarget: workign on a copy");
    		} else {
    			System.err.println("uh oh, undecipherable argement to GetMoveOrCopyTarget: " + mOrC);
    			System.exit(1);
    		}
    		
    	}
    }
    
    static public JList getListFromProps(String fileList, String delim) {
    	System.out.println("list of move-to directories is "+fileList);
	    	String[] targets = fileList.split(delim,0);
	    	System.out.println("list contains "+targets.length+" strings");
	    	JList<String> list = new JList<String>(targets);
	    	return list;
    }
	
	
	public void setDir(String mpath) {
		 out("set target directory for " + dirType + " cmd, dir WAS '"+mpath+"'");
		
		 JFileChooser fc = new JFileChooser()
		 {
			 protected JDialog createDialog(Component parent) throws HeadlessException {
			    JDialog dialog = super.createDialog(parent);
			    // …
			    //Point p = calculateCenter(dialog);
			    dialog.setLocation(10,20);
			    return dialog;
			 }
		  };
		 out("GetMoveCopyTarget: remote object about to create a new blank file chooser, but did we leave off somewhere?");
		 String lyne = mpath;
		 /*
			*/
		 String oldPath = null;
		 if (isCopy) {
			 oldPath = fm.getCopyToPath();
		 } else {
			 oldPath = fm.getMoveToPath();
		 }
		 if (oldPath == null) {
			 oldPath = "c:\\temp";
			 if (isCopy) {
				 fm.setCopyToPath(oldPath);	 
			 } else {
				 fm.setMoveToPath(oldPath);
			 }
		 }
			 
		if (isCopy) {
			lyne = new String(fm.getCopyToPath());	
		} else {
			lyne = new String(fm.getMoveToPath());
		}
		
		out("GetMoveCopyTarget: setting chooser path to " + lyne);
		
		
		File parn = new File(lyne);
		//out("parent of old path read in from file is "+parn.getParent());
		//File parnparn = new File(parn.getParent());
		//out("parent of parent is "+parnparn.getParent());
		//File targetDir = new File(parnparn.getParent());
		File targetDir = parn;
		 
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    fc.setCurrentDirectory(targetDir);
		//out("Setting selected file to "+parnparn.getPath());
		// except this doesn't work
		//fc.setSelectedFile(parnparn);
		//fc.setSelectedFile(targetDir);
		fc.ensureFileIsVisible(targetDir);
	 
	    //fc.setLocation(100,90);
		 
    	fc.showOpenDialog(null);
	 
	 
	    out("GetMoveCopyTarget: returned from show file chooser");
	    //fm.setDir(fc.getCurrentDirectory());
	    //fm.setFile(fc.getSelectedFile());
	    String newPath = fc.getCurrentDirectory().getPath();
	    String newName = fc.getSelectedFile().getName();
	    if (newPath == null) {
	    	newPath = new String ("none");
	    }
	    if (newName == null) {
	    	newName = new String("");
	    }
	    out("GetMoveCopyTarget: filechooser in GetDir returned directory "
			 +newPath
			 + "\\"
			 +newName);
	    
	    if (newPath.compareTo(("none")) == 0) {
	    	out("GetMoveCopyTarget: no new path specified for " + dirType + " path");
	    	return;
	    }
	    
	    StringBuffer toPathBuf = new StringBuffer(newPath);
	    toPathBuf.insert(0, "\"");
	    toPathBuf.append("\\");
	    toPathBuf.append(newName);
	    toPathBuf.append("\"");
	    String toPath = toPathBuf.toString();
	    out("GetMoveCopyTarget: built path to be '"+toPath+"'");
	    if ( isCopy) {
	    	fm.setCopyToPath(toPath);
	    } else {
	    	fm.setMoveToPath(toPath);
	    }
	    if (usedPaths == null) {
	    	out ("GetMoveCopyTarger: adding first path to path vector for "+dirType+ ", "+toPath);
	    	usedPaths = new Vector<String>();
	    	usedPaths.add(toPath);
	    } else {
	    	int imax = usedPaths.size();
	    	out("GetMoveCopyTarget: size of 'used-names' vector is "+imax);
	    	int idx = 0;
	    	boolean found = false;
	    	while (idx < imax) {
	    		String s = usedPaths.get(idx);
	    		if (s.compareTo(toPath)==0) {
	    			found = true;
	    		}
	    		idx += 1;
	    	}
	    	if (found == false) {
	    		usedPaths.add(toPath);
	    	}
	    	idx = 0;
	    	imax = usedPaths.size();
	    	while (idx < imax) {
	    		out("GetMoveCopyTarget: used paths[" + idx + "] is " + usedPaths.get(idx));
	    		idx += 1;
	    	}
	    }
		
		
		
	}

}
