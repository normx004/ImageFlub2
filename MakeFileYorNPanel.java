package imageflubber;
	import javax.swing.*;
	import java.util.*;
	import javax.swing.border.TitledBorder;
	import java.awt.*;
	import java.io.*;
	import java.util.LinkedList;
	
public class MakeFileYorNPanel  extends MakeArbitraryPanel {
	
	    //private static    int       BUTTCOUNT = 8;
		//private String[]           buttLabels =  new String[BUTTCOUNT];
		private Vector<String>    buttLabels = new Vector<String>();
		private String            borderTitle = new String("Keep or Move or Delete File?");
		private MainFlub                   fm = null;
		private GenericButtonPanelFactory grp = null;
		private JPanel                      p = null;
		private Writer                    wrt = null;
		private Writer                          getWriter(){return wrt;}
	    public int keepidx = 1;
	    public int discardidx = 2;
	    public int moveFileIdx = 3;
	    public int moveListIdx = 0;
	    public int copyListIdx = 0;
	    public int copyFileIdx = 0;
	    public int setMoveDirIdx = 0;
	    public int setCopyDirIdx = 0;
	    
	    		
		
		public MakeFileYorNPanel(MainFlub b) {
			fm = b;
			out("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			out("Constructing make file yorn panel");
			out("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			grp = new GenericButtonPanelFactory(this, fm);
	
			int k = 0;
			buttLabels.add(new String("keep"));
			k += 1;
			buttLabels.add(new String("discard"));	
			k += 1;
			buttLabels.add(new String("moveFile"));
			k += 1;
			if (fm.getMoveList() != null) {
				int kx = k+1;
				out("adding altMove button at "+kx);
				buttLabels.add( new String("altMove"));
				k += 1;
				moveListIdx = k;
			}
			
			buttLabels.add(new String("copyFile"));
			k += 1;			
			copyFileIdx = k;
			out("coyp file idx is "+copyFileIdx);
			
			if (fm.getCopyList() != null) {
				int kx = k + 1;
				out("adding altCopy button at "+kx);
				buttLabels.add( new String("altCopy"));
				k += 1;				
				copyListIdx = k;
				out ("alt copy idx is "+copyListIdx);
			}
			buttLabels.add(new String("setMoveDir"));
			k += 1;
			setMoveDirIdx = k;
			buttLabels.add(new String("setCopyDir"));
			k += 1;			
			setCopyDirIdx = k;
			
			out(" indexs for keepidx, discardidx, moveidx, moveListidx, copyListidx, copyFileidx,setMoveDiridx, setCopyDiridx are "+ 
			keepidx + " " +  discardidx + " " + moveFileIdx + " " + moveListIdx + " " +
	        " " + copyListIdx + 
	        " " + copyFileIdx +
	        " " + setMoveDirIdx +
	        " " + setCopyDirIdx);
			
			wrt = new Writer();
		}
		// -----------------------GETBUTTONS--------------------------------------------
		public JPanel getButtons() {
			LoadImageApp lia = fm.getIma();
			String bTitle = makeBorderTitle(lia);
			
			out("^^^^^^^^entered MakeFileYorNPanel:getButtons(): "+bTitle);
			String[] blab = new String[buttLabels.size()];
			for (int idx = 0; idx < buttLabels.size(); idx++) {
				blab[idx] = buttLabels.elementAt(idx);
			}
			p = grp.getButtons(bTitle, blab) ;
			out("adding action");
			grp.addAction(p);
			out("^^^^^^^^leaving MakeFileYorNPAnel:getButtons(): returning button panel  with title "+bTitle);
			return p;
			
		}
		
		 public void out(String s) { 
			if (fm.isDebug()) {
				 System.out.println("MakeFileYorNPanel:" + s);
		 	}
		 }
		 public void uncout(String s) {
			 System.out.println("MakeFileYornPanel:"+s);
		 }
		 //-----------------------------ACKSHUN---------------------------------------
		 // ------------this is where it all goes "down"-------------------
		 public void ackshun(java.awt.event.ActionEvent vent) {
			
		 if (fm.isDebug()) {
		     out("MakeFileYorNPanel:ackshun(event)-------------------------decisionButtonpress-------------------------------------");
		     //out("MakeFileYorNPanel:ackshun(event)--------------------------------------------------------------");
		     //out("MakeFileYorNPanel:ackshun(event)decision Button: " + vent.toString());
		     out("MakeFileYorNPanel:ackshun(event)Event value :    " + vent.getActionCommand());
  		     //out("MakeFileYorNPanel:ackshun(event)--------------------------------------------------------------");
		     //out("MakeFileYorNPanel:ackshun(event)--------------------------------------------------------------");
		  }
		 
		    int qval = -1;
		    Object[] jbut = p.getComponents();
		    int i = 0;
		    JButton b = null;
		    while (i < jbut.length) {
		    		b = (JButton)jbut[i]; 
		    		if (b.getText().compareTo(vent.getActionCommand())==0) {
		    			out("Figuring which button, text was "+b.getText());
		    			break;
		    		}
		    		i += 1;
	        }  // end while
		    int j = i+1;
		    qval = j;
		    out("done with 'check which button loop', i is " + i + " which means it was button " +j);
		    
		    String s = new String("keep");
		    if (qval == discardidx) {
	           s = "discard";
	           File4Img fy = fm.getIma().getTheFile();
	           //out("--------command: del "+fm.getIma().getTheFile());
	           out("--------command: del "+fy.getPath());
	           String fyString = fy.getPath();
	           int lastSlash = fyString.lastIndexOf('\\');
	           
               long bCount = fm.getIma().getTheFile().length();
               out("...adding file to delete list with byte length "+bCount);
               CountCarrier cc = CountCarrierFactory.getCarrier();
               long ccCount = cc.getBytesDeleted();
               cc.setBytesDeleted(ccCount + bCount);
               uncout ("NefCheck says select='"+fm.getNefCheck().isSelected()+ "'");
               out("and dump of variable is: "+fm.getNefCheck());
               
               if (fm.getNefCheck().isSelected()) {
            	   File f4i       = getNefFile(fy);
            	   uncout("nef path: "+f4i.getPath());
            	   long moreBytes = f4i.length();
            	   uncout("nef path file size is "+moreBytes);
            	   ccCount = cc.getBytesDeleted();
            	   cc.setBytesDeleted(ccCount+moreBytes);
               }
	           //fm.addToDelList(fm.getIma().getTheFile());
	           //saveFileLine(fm.getIma().getTheFile());
	           fm.addToDelList(fy);
	           saveFileLine(fy);
	        } else  if (qval == keepidx) { // KEEP
	        	//s="keep";	    
	        	out ("-----Keeping the file");
	        	saveLastKeptFile(fm.getIma().getTheFile());
	        } else if (qval == moveFileIdx) {   //  MOVE FILE
	            s = "move";
	           	out("----------setMoveFile");
	           	saveMoveLine(fm.getMoveToPath(), fm.getIma().getTheFile());
	        } else  if (qval == setMoveDirIdx) {  // set MOVE targe
	    	    s = "setMoveDir";
	    	    out("----------setMoveFile Directory");
	    	    String mvDir = fm.getMoveToPath();
	    	    if (mvDir == null ) {
	    	    	out("MakeFileYorNPanel: uh oh...get the 'move to path' from MainFlub but was null!!!");
	    	    	mvDir = new String("c:\\temp\\dummyTargetDir");
	    	    }
	    	    // now call the routine that pops a file chooser
	    	    this.setMoveDir(mvDir);
	    	    b.setToolTipText(fm.getMoveToPath());
	    	    return;
	      	}  else if (qval == copyFileIdx) {  // COPY FILE
	            s = "copy";
	           	out("----------setCopyFile");
	           	saveCopyLine(fm.getCopyToPath(), fm.getIma().getTheFile());
	        } else   if (qval == setCopyDirIdx) {  // set COPY target
	    	    s = "setCopyDir";
	    	    out("----------setCopyFile Directory");
	    	    String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
	    	    if (cpDir == null ) {
	    	    	out("MakeFileYorNPanel: uh oh...get the 'copy to path' from MainFlub but was null!!!");
	    	    	cpDir = new String("c:\\temp");
	    	    	fm.setCopyToPath(cpDir);
	    	    }
	    	    //now call routine that pops a file chooser
	    	    this.setCopyDir(cpDir);
	    	    b.setToolTipText(fm.getCopyToPath());
	    	    return;
	        } else   if (qval == moveListIdx) {  // it was the altMove button - select move target from list
	    	    s = "altMove";
	    	    out("----------move to directory from alt list");
	    	    
	    	    ListDial ld = new ListDial ("choose from these dirs", fm.getMoveList());
	    	    ld.setOnOk(e -> System.out.println("Chosen item: " + ld.getSelectedItem()));
	            ld.show();
	    	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
	    	    String mvDir = (String)ld.getSelectedItem();
	    	    out("WOW it worked, maybe, selected item was "+mvDir);
	    	    if (mvDir == null ) {
	    	    	out("MakeFileYorNPanel: uh oh...get the 'move to path' from the popup but it was null");
	    	    	// treat it as a 'keep'
	    	    }
	    	    saveAltMoveLine(mvDir, fm.getIma().getTheFile());
	    	    
	    	    //return;
	    	    
	        } else   if (qval == copyListIdx) {  // it was the altMove button - select move target from list
	    	    s = "altCopy";
	    	    out("----------copy to directory from alt list");
	    	    
	    	    ListDial ld = new ListDial ("choose from these dirs", fm.getCopyList());
	    	    ld.setOnOk(e -> System.out.println("Chosen item: " + ld.getSelectedItem()));
	            ld.show();
	    	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
	    	    String cpDir = (String)ld.getSelectedItem();
	    	    out("WOW it worked, maybe, selected item was "+cpDir);
	    	    if (cpDir == null ) {
	    	    	out("MakeFileYorNPanel: uh oh...get the 'copy to path' from the popup but it was null");
	    	    	// treat it as a 'keep'
	    	    }
	    	    saveAltCopyLine(cpDir, fm.getIma().getTheFile());
	    	    
	    	    //return;
	    	    
	      	}  else {
	   	        	 // assume it was a "keep" then
	   	        	 uncout("---------unknown command, assume 'keep'");
	   	        	 saveLastKeptFile(fm.getIma().getTheFile());
	       	}
	        

	      out("END BUTTON-base switch statement, looks like '"+s+"'");	
	       
	        // next file please!
	        out("calling  nextFile");
	        fm.getDirReader().nextFile();
	        uncout("Returned from get next file");
	        
	        if ( fm.getIma() != null ) { 
	        	out("just displayed file "+ fm.getIma().getTheFile().getParent() + "/" + fm.getIma().getTheFile().getName());
	        	LoadImageApp lia = fm.getIma();
	        	/*
				String bTitle = new String(borderTitle + "  " + fm.getIma().getTheFile().getName()
				+ "   (w:" + lia.getOriginalW() + "  h:" + lia.getOriginalH()
				+ ")   bytes: " + lia.getOriginalSize() 
				+ "  (" + lia.getThisFileNumberInList() 
				+ " of " 
				+ lia.getFilesInDirCount()
				);
			 */
	        	String bTitle = makeBorderTitle(lia);
				TitledBorder tb = (TitledBorder ) p.getBorder();
				tb.setTitle(bTitle);
				JFrame f = fm.getYornFrame();
				out("..........calling f.repaint()..............");
				f.repaint();
				out("..........calling fm.doit()................");
				//Thread.dumpStack();
	        	fm.doit();
				//out("LOCKING..........returned from fm.doit()..........");
				// make sure next action (returning from button click) does not 'step on'
				// the display and response to a file chooser popup
				//fm.lock(); // lock or wait for lock
				
	        } else {
	        	out("end of files in directory");
	        	// Modal dialog with yes/no button
	        	JFrame fra = new JFrame();
	        	String msg = new String("No More Files in Directory; save list?");
	        	int answer = JOptionPane.showConfirmDialog(fra, msg);
	        	if (answer == JOptionPane.YES_OPTION) {
	        	    out("YES!!!");
	        	} else if (answer == JOptionPane.NO_OPTION) {
	        	    out("Nahhhhh");
	        	}
	        	fm.setAllDone(true);
	        }
	    }
		 // derive "NEF" file name from to-be-deleted filename
		 File  getNefFile(File4Img fyIm) {
			    File fy = null;
				StringBuffer sb = new StringBuffer (fyIm.getPath());
				int rindex = sb.lastIndexOf("\\");
				out ("index of last slash is "+rindex);
				sb.insert(rindex, "\\nef");
				rindex = sb.lastIndexOf(".");
				String type = sb.substring(rindex,rindex +4);
				out ("Looking at extension of "+type);
				if (type.compareToIgnoreCase(".jpg")==0) {
					sb.replace(rindex+1, rindex+4, "nef");
				}
			 out("getNefFile returning file for "+sb.toString());
			 fy = new File(sb.toString());
			 return fy;			 
		 }
		 //-------------------------DIGEST SIZE-------------------------
		 private String digestSize(long sz) {
			 StringBuffer result = new StringBuffer();
			 String       mod = new String("");
			 Long majsz = new Long(0);
			 Long frac = new Long(0);
			 if ( sz < 1000000) {
				 mod=new String("K");
				 majsz = sz / 1000;
				 frac = sz - (majsz * 1000);
				 frac = frac/100;
			 }
			 else {
			    mod = new String ("M");
			    majsz = sz / 1000000;
				frac = sz - (majsz * 1000000);
				frac = frac/100000;
			 }
			 Long s = new Long(majsz);
			 result.append( s.toString());
			 result.append(".");
			 result.append(frac.toString());
			 result.append(mod);
			 return result.toString();
		 }
		 //------------------MAKE BORDER TITLE---------------------------------------
		 private String makeBorderTitle(LoadImageApp lia) {
			 String digestedSize = digestSize(lia.getOriginalSize());
			 String bTitle = new String(
					    //borderTitle + "  " +
					    fm.getIma().getTheFile().getName()
						+ "   (w:" + lia.getOriginalW() + "  h:" + lia.getOriginalH()
						+ ")   bytes: " + digestedSize//lia.getOriginalSize() 
						+ "  (" + lia.getThisFileNumberInList() 
						+ " of " 
						+ lia.getFilesInDirCount()
						+ ")   "
						+"( saved " 
						+ lia.getFilesSaved() 
						+")   ( deleted: "
						+ lia.getFilesDeleted()
						+")   ( bytes deleted: "
						+ this.digestSize(lia.getBytesDeleted())
						+")   ( moved: "
						+ lia.getFilesMoved()
						+ ")"
						);
			 return bTitle;
		 }
		 //----------------------------SaveLastDirectory----------------------------------------------
		 public void saveLastKeptFile(File f) {
			 uncout("Save last OK file " + f.getPath());
			 String path = new String("\\temp\\lastGoodFile.txt");
			 boolean append= false;
			 Writer w = getWriter();
			 boolean okwrite = w.tryWrite(path, "q:", "c:", f.getPath(), append);
		     if (!okwrite) {
				System.err.println("Uh Oh, failed to write to "+path);	
			 }
		     saveLastKeptFileAndDir(path, "q:", "c:", f.getPath(),append);
		     
		     int num = fm.getIma().getFilesSaved();
		     fm.getIma().setFilesSaved(num+1);
		 }
		 //----------------------------Move file, don't delete or just keep----------------------------------------------
		 public void moveLastKeptFile(String path, File f) {
			 if ( fm.getMoveToPath()==null) {
				 out("Not moving, no 'move to' path specified in props");
				 return;
			 }
			 out("Move last OK file");
			 String apath = new String("\\temp\\lastGoodFile.txt");
			 boolean append= false;
			 Writer w = getWriter();
			 boolean okwrite = w.tryWrite(apath, "q:", "c:", f.getPath(), append);
		     if (!okwrite) {
				System.err.println("Uh Oh, failed to write to "+path);	
			 }
		     
		     int num = fm.getIma().getFilesMoved();
		     fm.getIma().setFilesMoved(num+1);
		 }
		//----------------------------SaveLastDirectory----------------------------------------------
		 public void saveLastKeptFileAndDir(String wrteFilePath, String dir1, String dir2, String pathOfLastFile, boolean append ) {
			 uncout("Save last OK file with path info");
			 StringBuffer path = new StringBuffer("\\temp\\");//lastGoodFile.txt");
			 StringBuffer add2path = new StringBuffer(pathOfLastFile);
			 int rindex = add2path.lastIndexOf("\\");
			 out("saveLastkeptFileAndDir: add2path is "+add2path.toString());
			 out("saveLastkeptFileAndDir: rindex of last backslash is "+rindex);
			 add2path.delete(rindex, add2path.length());
			 out("saveLastkeptFileAndDir: after truncation: "+add2path.toString());
			 int lindex = add2path.indexOf(":");
			 out("saveLastkeptFileAndDir: lindex of colon is "+lindex);
			 add2path.delete(0,lindex+1);
			 uncout("saveLastkeptFileAndDir: after removing drive: " +add2path.toString());
			 //add2path.replace(0, add2path.length()-1, "\\"
			 boolean done = false;
			 int stopper = 0;
			 while(!done) {
				 lindex = add2path.indexOf("\\");
				 out("found backslash at "+lindex);
				 if (lindex < 0) {
					 done = true;
				 } else {
					 add2path.replace(lindex,lindex+1,".");
				 }
				 stopper += 1;
				 if (stopper > 15) {
					 done = true;
				 }
			 }
			 out("saveLastkeptFileAndDir: replaced backslashes "+add2path);
			 add2path.delete(0,1);
			 add2path.append("-lastGoodFile.txt");
			 add2path.insert(0,"\\temp\\");
			 out("saveLastkeptFileAndDir: replaced backslashes "+add2path);
			 			 
			 Writer w = getWriter();
			 boolean okwrite = true;
			 okwrite = w.tryWrite(add2path.toString(), "q:", "c:", pathOfLastFile, append);
		     if (!okwrite) {
				System.err.println("Uh Oh, failed to write to "+path);	
			 }
		 }
		 
		
		 //------------------------SAVEFileLine - that is, save the delete command--------------------

			public void saveFileLine(File f) {
				out("SAVE item  TO BAT FILE");
				//new String("\\temp\\delum.bat");
				String cmdFilePath = fm.getCmdFilePath();
				String cmd  = new String ("del \""+f.getPath()+"\"\n");
				int num     = fm.getIma().getFilesDeleted();
				fm.getIma().setFilesDeleted(num+1);
				boolean append = true;
				Writer w   = getWriter();
				boolean okwrite = w.tryWrite(cmdFilePath, "q:", "c:", cmd, append);
				if (!okwrite) {
					System.err.println("Uh Oh, failed to write delete to "+cmdFilePath);	
				}
				// if NEF box is checked on fileYorNPanel, write additional delete command,
				// with 1) \NEF subdirectory assumed, and
				//      2) .jpg on file name changed to .nef
				if (fm.getNefCheck().isSelected()) {
				
					File4Img fy = fm.getIma().getTheFile();
					File fyBuf = getNefFile(fy);
					StringBuffer sb = new StringBuffer ("del \""+fyBuf.getPath()+"\"\n");
					uncout ("new delete command is "+sb.toString());
					cmd = new String (sb);
					okwrite = w.tryWrite(cmdFilePath, "q:", "c:", cmd, append);
					if (!okwrite) {						
						System.err.println("Uh Oh, failed to write NEF delete to "+cmdFilePath);	
					}
				}
				
			}
			 //------------------------SAVEmoveLine - that is, save the move command--------------------

			public void saveMoveLine(String mpath, File f) {
				out("SAVE move cmd of item  TO BAT FILE");
				if (fm.getMoveToPath() == null) {
					JOptionPane.showMessageDialog(fm.getFrayme(), "no 'move to' directory set. cannot move file.");
					return;
				}
				String path = new String("\\temp\\delum.bat");
				String cmd = new String ("move \"" + f.getPath() + "\" \"" + fm.getMoveToPath() + "\"\n");
				int num = fm.getIma().getFilesMoved();
				fm.getIma().setFilesMoved(num+1);
				boolean append = true;
				Writer w = getWriter();
				boolean okwrite = w.tryWrite(path, "q:", "c:", cmd, append);
				if (!okwrite) {
					System.err.println("Uh Oh, failed to write to "+path);	
				}
				
			}
			
			public void saveAltMoveLine(String mdir, File f) {
				out("SAVE move cmd of item  TO BAT FILE");
				if (mdir == null) {
					JOptionPane.showMessageDialog(fm.getFrayme(), "no 'move to' directory set from list. cannot move file.");
					return;
				}
				String path = new String("\\temp\\delum.bat");
				String cmd = new String ("move \"" + f.getPath() + "\" \"" + mdir + "\"\n");
				int num = fm.getIma().getFilesMoved();
				fm.getIma().setFilesMoved(num+1);
				boolean append = true;
				Writer w = getWriter();
				boolean okwrite = w.tryWrite(path, "q:", "c:", cmd, append);
				if (!okwrite) {
					System.err.println("Uh Oh, failed to write to "+path);	
				}
				
			}
			
			 //------------------------SAVECopyLine - that is, save the Copy command--------------------

			public void saveCopyLine(String cpath, File f) {
				out("SAVE Copy cmd of item  TO BAT FILE");
				
				if (fm.getCopyToPath() == null) {
					JOptionPane.showMessageDialog(fm.getFrayme(), "no 'copy to' directory set. cannot move file.");
					return;
				}
				
				String path = new String("\\temp\\delum.bat");
				String cmd = new String ("Copy \"" + f.getPath() + "\" \"" + cpath + "\"\n");
				int num = fm.getIma().getFilesCopyed();
				fm.getIma().setFilesCopyed(num+1);
				boolean append = true;
				Writer w = getWriter();
				boolean okwrite = w.tryWrite(path, "q:", "c:", cmd, append);
				if (!okwrite) {
					System.err.println("Uh Oh, failed to write to "+path);	
				}
				
			}
			
			public void saveAltCopyLine(String cpdir, File f) {
				out("SAVE copy cmd of item  TO BAT FILE");
				if (cpdir == null) {
					JOptionPane.showMessageDialog(fm.getFrayme(), "no 'copy to' directory set from list. cannot move file.");
					return;
				}
				String path = new String("\\temp\\delum.bat");
				String cmd = new String ("copy \"" + f.getPath() + "\" \"" + cpdir + "\"\n");
				int num = fm.getIma().getFilesCopyed();
				fm.getIma().setFilesCopyed(num+1);
				boolean append = true;
				Writer w = getWriter();
				boolean okwrite = w.tryWrite(path, "q:", "c:", cmd, append);
				if (!okwrite) {
					System.err.println("Uh Oh, failed to write to "+path);	
				}
			}
				
			GetMoveCopyTarget gmctMove = null;
			GetMoveCopyTarget gmctCopy = null;
			
			public void setMoveDir(String mpath) {
				
				if (gmctMove == null) {
					gmctMove = new GetMoveCopyTarget("move", fm);
				}
				//String dir = fm.getMoveToPath();
				gmctMove.setDir(mpath);
			}
			public void setCopyDir(String mpath) {
				
				if (gmctCopy == null) {
					gmctCopy = new GetMoveCopyTarget("copy", fm);
				}
				//String dir = fm.getMoveToPath();
				gmctCopy.setDir(mpath);
			}
			 //------------------------SAVEmoveLine - that is, save the move command--------------------
/*
			public void setMoveDir(String mpath) {
				out("set target directory for 'move' cmd, dir WAS '"+mpath+"'");
	
				
				
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
				 out("MakeFileOrNPanel: remote object about to create a new blank file chooser, but did we leave off somewhere?");
				 String lyne = mpath;
				
				 String oldPath = fm.getMoveToPath();
				 if (oldPath == null) {
					 oldPath = "c:\\temp";
					 fm.setMoveToPath(oldPath);
				 }
				lyne = new String(fm.getMoveToPath());
				out("setting chooser path to " + lyne);
				
				
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
			 
			 
			    out("returned from show file chooser");
			    //fm.setDir(fc.getCurrentDirectory());
			    //fm.setFile(fc.getSelectedFile());
			    String newPath = fc.getCurrentDirectory().getPath();
			    String newName = fc.getSelectedFile().getName();
			    if (newPath == null) {
			    	newPath = new String ("none");
			    }
			    if (newName == null) {
			    	newName = new String("none");
			    }
			    out("filechooser in GetDir returned directory "
					 +newPath
					 + "\\"
					 +newName);
			    
			    if (newPath.compareTo(("none")) == 0) {
			    	out("MakeYorNFile: no new path specified for getMoveToPath");
			    	return;
			    }
			    
			    StringBuffer toPathBuf = new StringBuffer(newPath);
			    toPathBuf.insert(0, "\"");
			    toPathBuf.append("\\");
			    toPathBuf.append(newName);
			    toPathBuf.append("\"");
			    String toPath = toPathBuf.toString();
			    fm.setMoveToPath(toPath);
				
				
				
			}
			//-----------------------------------------------------------------------------------
			// NOTE: this is clone of "setMoveDir" and probably one routine, a bit more generic,
			// could do both functions...
			public void setCopyDir(String mpath) {
				out("set target directory for 'copy' cmd, dir WAS '"+mpath+"'");
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
				 out("MakeFileOrNPanel: remote object about to create a new blank file chooser, but did we leave off somewhere?");
				 String lyne = mpath;
				
				 String oldPath = fm.getCopyToPath();
				 if (oldPath == null) {
					 oldPath = "c:\\temp";
					 fm.setCopyToPath(oldPath);
				 }
				lyne = new String(fm.getCopyToPath());
				out("setting chooser path to " + lyne);
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
			 
			 
			    out("returned from show file chooser");
			    //fm.setDir(fc.getCurrentDirectory());
			    //fm.setFile(fc.getSelectedFile());
			    String newPath = fc.getCurrentDirectory().getPath();
			    String newName = fc.getSelectedFile().getName();
			    if (newPath == null) {
			    	newPath = new String ("none");
			    }
			    if (newName == null) {
			    	newName = new String("none");
			    }
			    out("filechooser in GetDir returned directory "
					 +newPath
					 + "\\"
					 +newName);
			    
			    if (newPath.compareTo(("none")) == 0) {
			    	out("MakeYorNFile: no new path specified for getCopyToPath");
			    	return;
			    }
			    
			    			    
			    StringBuffer toPathBuf = new StringBuffer(newPath);
			    toPathBuf.insert(0, "\"");
			    toPathBuf.append("\\");
			    toPathBuf.append(newName);
			    toPathBuf.append("\"");
			    String toPath = toPathBuf.toString();
			    fm.setCopyToPath(toPath);
				
				
				
			}
			*/
	}
		

