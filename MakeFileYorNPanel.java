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
		private GenericButtonPanelFactory gbpfct = null;
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
			gbpfct = new GenericButtonPanelFactory(this, fm);

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
				buttLabels.add( new String(MainFlub.AltMove));
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
				buttLabels.add( new String(MainFlub.AltCopy));
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
				out("button["+idx+"] = "+blab[idx]);
			}
			p = gbpfct.getButtons(bTitle, blab) ;
			out("adding action");
			gbpfct.addAction(p);
			out("^^^^^^^^leaving MakeFileYorNPAnel:getButtons(): returning button panel  with title "+bTitle);
			return p;
			
		}
		
		 public void out(String s) { 
			//if (fm.isDebug()) {
				 System.out.println("MakeFileYorNPanel:" + s);
		 	//}
		 }
		 public void uncout(String s) {
			 System.out.println("MakeFileYornPanel:"+s);
		 }
		 //-----------------------------ACKSHUN---------------------------------------
		 // ------------this is where it all goes "down"-------------------
		 public void ackshun(java.awt.event.ActionEvent vent) {
			
		 if (fm.isDebug()) {
		     out("ackshun(event)-------------------------decisionButtonpress-------------------------------------");
		     //out("MakeFileYorNPanel:ackshun(event)--------------------------------------------------------------");
		     //out("MakeFileYorNPanel:ackshun(event)decision Button: " + vent.toString());
		     out("ackshun(event)Event value :    " + vent.getActionCommand());
  		     //out("MakeFileYorNPanel:ackshun(event)--------------------------------------------------------------");
		     //out("MakeFileYorNPanel:ackshun(event)--------------------------------------------------------------");
		  	}
		    // first lets find out where the panel is
		    Rectangle rec = p.getVisibleRect();
		    Point panelP = rec.getLocation();
		    //String nes = new String("ok, button panel is at point "+ panelP.x + "," + panelP.y);
		    //out (nes);
		    
		 	int panelX = p.getX();
		 	int panelY = p.getY();
		 	
		 	out("asked for panel location, x is " + panelX + ", y is " + panelY);
		 	Graphics2D01 ghw = new Graphics2D01();
		 	out("ok, screen dimensions are "+ghw.getWidthx() + ", "+ ghw.getHeightx());
		 	//ok, just hard code it for now
		 	panelX = 600;
		 	panelY = 1000;
		 	
		 	String msg = new String("ackshun: panel x, y is " + panelX + " " + panelY);
		 	out(msg);
		 	// now figure out which button was pushed
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
		    // OK, now we know which button it was...do something about it!
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
	    	    	out("uh oh...get the 'move to path' from MainFlub but was null!!!");
	    	    	mvDir = new String("c:\\temp\\dummyTargetDir");
	    	    }
	    	    // new move-to target
	    	    this.setMoveDir(mvDir);
	    	    b.setToolTipText(fm.getMoveToPath());
	    	    return;
	      	}  else if (qval == copyFileIdx) {  // COPY FILE target
	            s = "copy";
	           	out("----------setCopyFile");
	           	saveCopyLine(fm.getCopyToPath(), fm.getIma().getTheFile());
	        } else   if (qval == setCopyDirIdx) {  // set COPY target
	    	    s = "setCopyDir";
	    	    out("----------setCopyFile Directory");
	    	    String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
	    	    if (cpDir == null ) {
	    	    	out("uh oh...get the 'copy to path' from MainFlub but was null!!!");
	    	    	cpDir = new String("c:\\temp");
	    	    	fm.setCopyToPath(cpDir);
	    	    }
	    	    //now call routine that pops a file chooser
	    	    this.setCopyDir(cpDir);
	    	    b.setToolTipText(fm.getCopyToPath());
	    	    return;
	        } else   if (qval == moveListIdx) {  // it was the altMove button - select move target from list
	    	    s = MainFlub.AltMove;
	    	    out("----------move to directory from alt list");
	    	    /*
	    	    ListDial ld = new ListDial ("choose from these dirs", fm.getMoveList());
	    	    int listDialHeight = ld.getHeight();
	    	    String st = new String("ackshun: listDial height is "+listDialHeight);
	    	    out(st);
	    	    ld.setOnOk(e -> System.out.println("Chosen item: " + ld.getSelectedItem()));
	    	    int putMe = panelY - listDialHeight - 10;
	    	    String nst = new String("ackshun: new Y value for dialog is "+putMe);
	    	    out(nst);	 
	    	    
	            //ld.show();
	            ld.show(900, putMe);
	    	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
	    	    String mvDir = (String)ld.getSelectedItem();
	    	    out("WOW it worked, maybe, selected item was "+mvDir);
	    	    if (mvDir == null ) {
	    	    	out("uh oh...get the 'move to path' from the popup but it was null");
	    	    	// treat it as a 'keep'
	    	    }
	    	    */
	    	    String mvDir = fm.getAltMoveToPath();
	    	    saveAltMoveLine(mvDir, fm.getIma().getTheFile());
	    	    
	    	    //return;
	    	    
	        } else   if (qval == copyListIdx) {  // it was the altCopy button - select move target from list
	    	    s = MainFlub.AltCopy;
	    	    out("----------copy to directory from alt list");
	    	    /*
	    	    ListDial ld = new ListDial ("choose from these dirs", fm.getCopyList());
	    	    int listDialHeight = ld.getHeight();
	    	    String st = new String("ackshun: listDial height is "+listDialHeight);
	    	    out(st);
	    	    ld.setOnOk(e -> System.out.println("ackshun: Chosen item: " + ld.getSelectedItem()));
	    	    // now want to find location of the selection buttons so can pop the dialog just above it
	    	    // instead of just centering it which is what the code currently does.
	    	    int putMe = panelY - listDialHeight - 10;
	    	    String nst = new String("ackshun: new Y value for dialog is "+putMe);
	    	    out(nst);	    	    
	            //ld.show(panelX+50, putMe);
	    	    ld.show(900, putMe);
	    	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
	    	    String cpDir = (String)ld.getSelectedItem();
	    	    out("WOW it worked, maybe, selected item was "+cpDir);
	    	    if (cpDir == null ) {
	    	    	out("uh oh...get the 'copy to path' from the popup but it was null");
	    	    	// treat it as a 'keep'
	    	    }
	    	    */
	    	    String cpDir = fm.getAltCopyToPath();
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
	        	
	        	String bTitle = makeBorderTitle(lia);
				TitledBorder tb = (TitledBorder ) p.getBorder();
				JFrame f = fm.getYornFrame();
				tb.setTitle(bTitle);
				String fTitle = new String( "Keep, Discard or Move File Controls from " + fm.getDir().getPath());
				f.setTitle(fTitle);
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
	        	String msga = new String("No More Files in Directory; save list?");
	        	int answer = JOptionPane.showConfirmDialog(fra, msga);
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
						+")   ( moved: "
						+ lia.getFilesMoved()
						+")   ( copied: "
						+ lia.getFilesCopyed()
						+")   ( bytes deleted: "
						+ this.digestSize(lia.getBytesDeleted())
						
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

	}
		

