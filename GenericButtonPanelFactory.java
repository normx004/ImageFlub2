package imageflubber;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;


public class GenericButtonPanelFactory {
	//
	private   JButton[] buttons   = null;
	
	//private   boolean debug = false;
	private   boolean debug = true;
	public    boolean isDebug() {		return debug;	}
	public    void    setDebug(boolean debug) {		this.debug = debug;}
	
	private   MainFlub  fm_ = null;
	public MainFlub getFm_() {
		return this.fm_;
	}
	public void setFm_(MainFlub fm) {
		this.fm_ = fm;
	}
	
	// variables use to decide which screen we're on in multi-display environment ("\Display0" = primary, "\Display1" = secondary, etc)
	GraphicsConfiguration gc_ = null;
	//String screenID_ = null;
	
	private MakeArbitraryPanel mapa = null;
	
	GenericButtonPanelFactory (MakeArbitraryPanel imap) {
		// this object probably will not work from this contructor...
		mapa = imap;
	}
	GenericButtonPanelFactory (MakeArbitraryPanel imap, MainFlub fm) {
		mapa = imap;
		setFm_(fm);
	}
	class PleaseHandleAltButton extends MouseAdapter
	{
		MainFlub mf = null;
		public PleaseHandleAltButton(MainFlub mf_) {
			mf = mf_;
		}
		private MouseEvent event = null;
		private JButton    theButt = null;
		
		public void mouseClicked(MouseEvent e)
		{
			event = e;
			out("altcopy button clicked");
			theButt = ((JButton)e.getComponent());			String buttName = ((JButton)e.getComponent()).getText();
			gc_ = theButt.getGraphicsConfiguration();
			
			out("ok it is the '"+buttName+"' button");
			out("so, button may be " + e.getButton());
			int buttPressType = e.getButton();
			if (buttPressType == MouseEvent.BUTTON1) {
				out("....it was left button");
				// here's where we should write the 'move' line to the output cmd file
				if (buttName.compareTo(MainFlub.AltCopy)==0) {
					handleCopy();
				} else {
					if (buttName.compareTo(MainFlub.AltMove)==0) {
						handleMove();
					}
				} 
				return;
			} else {
				if (buttPressType == MouseEvent.BUTTON2) {
					out("....it was center button");
					return;
				} else {
					if (buttPressType == MouseEvent.BUTTON3) {
						out("....it was right button");
						if (buttName.compareTo(MainFlub.AltCopy)==0) {
							handleSetAltMoveCopyTarget(MainFlub.AltCopy, theButt);
						} else {
							if (buttName.compareTo(MainFlub.AltMove)==0) {
								handleSetAltMoveCopyTarget(MainFlub.AltMove, theButt);
							}
						} 
				}		
			}		
		}
	  }  // end mouseClickedEvent
	  private void handleCopy() {
		  out("handleCopy entered");
	  }
	  private void handleMove() {
		  out("handleMove entered");
	  }
	  
	  private void handleSetAltMoveCopyTarget(String moveCopy, JButton jb) {
		  out("handleSetAltMoveCopyTarget ---------set " + moveCopy + " altcopy target to directory from alt list");
		
		//String screenID = getGraphicsConfiguration().getDevice().getIDstring();
		fm_.setScreenID(gc_.getDevice().getIDstring());
		out("handleSetAltCopyMoveTarget: screen ID is "+ fm_.getScreenID());
		
		Point pointy = jb.getLocationOnScreen();
		out("button is at X:" + pointy.x + ", and Y:" + pointy.y);
		
		JList lizt = mf.getCopyList();
		out("handleSetAltMoveCOpyTarget: assuming copy list...unless...");
		if (moveCopy.compareTo(fm_.getAltcopy()) != 0) {
			out("handleSetAltMoveCopyTarget: NO! it is the move list!!");
			lizt = mf.getMoveList();
		}
  	    ListDial ld = new ListDial ("choose from these dirs", /*mf.getCopyList()*/ lizt, fm_);
  	    int listDialHeight = ld.getHeight();
  	    String st = new String("listDial height is "+listDialHeight);
  	    out(st);
  	    ld.setOnOk(e -> System.out.println("handleSetAltCopyMoveTarget: Chosen item: " + ld.getSelectedItem()));
  	    // now want to find location of the selection buttons so can pop the dialog just above it
  	    // instead of just centering it which is what the code currently does.
  	    int    putMe = /*panelY*/ 1000 - listDialHeight - 10; // panelY hardcoded in MakeFileYornPanel - do same here
  	    String nst   = new String("ackshun: new Y value for dialog is "+putMe);
  	    out(nst);	    	    
          //ld.show(panelX+50, putMe);
  	    ld.show(pointy.x , putMe);
  	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
  	    String cpDir = (String)ld.getSelectedItem();
  	    out("WOW it worked, maybe, selected item was "+cpDir);
  	    if (cpDir == null ) {
  	    	out("uh oh...get the 'copy (or move) to path' from the popup but it was null");
  	    	// treat it as a 'keep'
  	    	return;
  	    }
  	    if (moveCopy.compareTo(MainFlub.AltCopy)==0) {
  	      mf.setAltCopyToPath(cpDir);
  	    } else
  	    	if (moveCopy.compareTo(MainFlub.AltMove)==0) {
  	    		mf.setAltMoveToPath(cpDir);
  	    	} else {
  	    		System.err.println("AAAAAGH move copy string for ListDial MUST BE 'move' or 'copy'");
  	    		System.exit(1);
  	    	}
  	    String currentTip = jb.getToolTipText();
  	    String a = new String("right-click to choose alt directory");
  	    String b = new String("; now set to "+cpDir);
  	    String ttip = null;
  	    if ( currentTip == null) {
  	    	ttip = new String(a);
  	    } else {
  	    	ttip = new String(a+b);
  	    }
  	    //ttip = new String("right-click to choose alt-move directory; now set to "+cpDir);
  	    out("Setting alt " + moveCopy + " tool tip to "+ttip);
 	    jb.setToolTipText(ttip);
	  }
	  
	  
	  
	  
	  /*
	  private void handleSetAltCopyTarget(JButton jb) {
		  out("handleSetAltCopyTarget ---------set altcopy target to directory from alt list");
		
		//String screenID = getGraphicsConfiguration().getDevice().getIDstring();
		fm_.setScreenID(gc_.getDevice().getIDstring());
		out("handleSetAltCopyTarget: screen ID is "+ fm_.getScreenID());
  	    ListDial ld = new ListDial ("choose from these dirs", mf.getCopyList(), fm_);
  	    int listDialHeight = ld.getHeight();
  	    String st = new String("listDial height is "+listDialHeight);
  	    out(st);
  	    ld.setOnOk(e -> System.out.println("Chosen item: " + ld.getSelectedItem()));
  	    // now want to find location of the selection buttons so can pop the dialog just above it
  	    // instead of just centering it which is what the code currently does.
  	    //     putMe = panelY, except it is hardcoded in MakeFileYorNPanel; so do same here
  	    int    putMe = 1000 - listDialHeight - 10; // panelY hardcoded in MakeFileYornPanel - do same here
  	    String nst   = new String("ackshun: new Y value for dialog is "+putMe);
  	    out(nst);	    	    
          //ld.show(panelX+50, putMe);
  	    ld.show(900, putMe);
  	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
  	    String cpDir = (String)ld.getSelectedItem();
  	    out("WOW it worked, maybe, selected item was "+cpDir);
  	    if (cpDir == null ) {
  	    	out("uh oh...get the 'copy to path' from the popup but it was null");
  	    	// treat it as a 'keep'
  	    	return;
  	    }
  	    mf.setAltCopyToPath(cpDir);
  	    String currentTip = jb.getToolTipText();
  	    String a = new String("right-click to choose alt-move directory");
  	    String b = new String("; now set to "+cpDir);
  	    String ttip = null;
  	    if ( currentTip == null) {
  	    	ttip = new String(a);
  	    } else {
  	    	ttip = new String(a+b);
  	    }
  	    //ttip = new String("right-click to choose alt-move directory; now set to "+cpDir);
  	    out("Setting alt-move tool tip to "+ttip);
 	    jb.setToolTipText(ttip);
	  }
	
	  private void handleSetAltMoveTarget(JButton jb) {
		out("handleSetAltMoveTarget entered");
		fm_.setScreenID(gc_.getDevice().getIDstring());
		out("handleSetAltCopyTarget: screen ID is "+ fm_.getScreenID());
		
		ListDial ld = new ListDial ("choose from these dirs", mf.getMoveList(), fm_);
  	    int listDialHeight = ld.getHeight();
  	    String st = new String("ackshun: listDial height is "+listDialHeight);
  	    out(st);
  	    ld.setOnOk(e -> System.out.println("Chosen item: " + ld.getSelectedItem()));
  	    //     putMe = panelY, except it is hardcoded in MakeFileYorNPanel; so do same here
  	    int    putMe =  1000 - listDialHeight - 10; // panelY hardcoded in MakeFileYornPanel - do same here
  	    //String nst = new String("ackshun: new Y value for dialog is "+putMe);
  	    //out(nst);	 
  	    
        ld.show(900, putMe);
  	    //String cpDir = fm.getCopyToPath();  // just does a file chooser, shold work as is
  	    String mvDir = (String)ld.getSelectedItem();
  	    out("WOW it worked, maybe, selected item was "+mvDir);
  	    if (mvDir == null ) {
  	    	out("uh oh...get the 'move to path' from the popup but it was null");
  	    	// treat it as a 'keep'
  	    	return;
  	    }
  	    mf.setAltMoveToPath(mvDir);
  	    String ttip = new String("right-click to choose alt-move directory; now set to "+mvDir);
  	    jb.setToolTipText(ttip);
	  }
	}
     */
	  
	}
	  
	//----------------------GETBUTTONS-------------------------------------------
	public JPanel getButtons(String borderTitle, String[] labels) {
		setDebug(MainFlub.isStaticDebug());
		
		Color[] bColor = new Color[]{Color.green, Color.pink, Color.lightGray, Color.cyan, Color.orange, Color.yellow, Color.magenta, Color.white};
		
		
		int nButtons = labels.length;
		out("GenericButtonPanelFactory:getButtons(title, lables[]): buttons  to create: "+nButtons);
		buttons = new JButton[nButtons];
		int i=0;
		while (i < nButtons) {	
			out("Labels[" +i + "] is "+labels[i]);
			buttons[i]   = new JButton( labels[i]);
			
			if (labels[i].compareTo(MainFlub.AltCopy) == 0 || 
				labels[i].compareTo(MainFlub.AltMove) == 0 ) {
				 	out("Setting up button for "+labels[i]);
				 	PleaseHandleAltButton ph = new PleaseHandleAltButton(fm_);
				 	buttons[i].addMouseListener (ph); //(new MouseAdapter(){
				 	buttons[i].setToolTipText("right-click to choose alt-move/copy directory");
			}
			buttons[i].setBackground(bColor[i]);
			i += 1;
		}
		// NOTE: copyFile button's place in the list is different depending on whether or not
		//       the 'altMove' item is in the props file
		JList dummy = getFm_().getMoveList();
		int copyFileButtIdx = 3;
		if (dummy != null) {
			copyFileButtIdx = 4;
		}
			
		if (fm_.getCopyToPath() != null) {
			buttons[copyFileButtIdx].setToolTipText(fm_.getCopyToPath());
		}
		if (fm_.getMoveToPath() != null) {
			buttons[2].setToolTipText(fm_.getMoveToPath());
		}
	
		ButtonGroup bgroup = new ButtonGroup();
		i = 0; 
		while (i < nButtons) {
			bgroup.add(buttons[i]);
			i += 1;
		}
		out("GenericButtonPanelFactory:getButtons():Button group created");
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(1, nButtons));
		i = 0; 
		Dimension bdim = null;
		
		while (i < nButtons) {
			bPanel.add(buttons[i]);
			JButton thisButt = buttons[i];
			
			if (thisButt.getText().compareTo("moveFile") == 0) {
				// should probably make the string "moveFile" a global constant, in case it ever changes...
				if (fm_ != null) {
					out("setting tooltip for move to path");
					buttons[i].setToolTipText(fm_.getMoveToPath());
				}
			}
			if (thisButt.getText().compareTo("copyFile") == 0) {
				// should probablyl make the string "copyFile" a global constant, in case it ever changes...
				if (fm_ != null) {
					out("setting tooltip for copy to path");
					buttons[i].setToolTipText(fm_.getCopyToPath());
				}
			}
			i += 1;
		}
    	
   
		bPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), borderTitle));
		
	    out("GenericButtonPanelFactory:getButtons();  returning the button panel");
		return bPanel;
   }
	
	
	 private void out(String s) {
		 if (isDebug()) {
			 String s1 = new String("GenericButtonPanelFactory: "+s);
			 System.out.println(s1);
			 }
		 }
	 
	 public void addAction(JPanel p) {
		   int i = 0;
		   int nButtons = p.getComponentCount();
		   out("GenericButtonPanelFactory:getButtons():total buttons that need action: "+nButtons);
		   while (i < nButtons) {
			
		    	((JButton)(p.getComponent(i))).addActionListener(	new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				         mapa.ackshun(evt);
				        }});
		    	i += 1;
		    }
	 }
	
	
 }

