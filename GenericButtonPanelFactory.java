package imageflubber;
import javax.swing.*;
import java.awt.*;


public class GenericButtonPanelFactory {
	//
	private   JButton[] buttons   = null;
	
	//private   boolean debug = false;
	private   boolean debug = true;
	public    boolean isDebug() {		return debug;	}
	public    void    setDebug(boolean debug) {		this.debug = debug;}
	private   MainFlub  fm_ = null;
	
	private MakeArbitraryPanel mapa = null;
	
	GenericButtonPanelFactory (MakeArbitraryPanel imap) {
		mapa = imap;
	}
	GenericButtonPanelFactory (MakeArbitraryPanel imap, MainFlub fm) {
		mapa = imap;
		fm_ = fm;
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
			buttons[i]   = new JButton( labels[i]);
			buttons[i].setBackground(bColor[i]);
			i += 1;
		}
		if (fm_.getCopyToPath() != null) {
			buttons[3].setToolTipText(fm_.getCopyToPath());
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
			/*if ( i == (nButtons - 1)) {
				// try to create a spacer button that does nothing...maybe remove border?
				bdim = buttons[i].getSize();
				JButton b = new JButton();
				b.setSize(bdim);
				bPanel.add(b);
			}
			*/
			bPanel.add(buttons[i]);
			JButton thisButt = buttons[i];
			
			if (thisButt.getText().compareTo("moveFile") == 0) {
				// should probablyl make the string "moveFile" a global constant, in case it ever changes...
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
		 if (isDebug()) {System.out.println(s);}
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
