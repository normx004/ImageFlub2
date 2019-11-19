package imageflubber;

import java.awt.event.WindowAdapter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.io.*;
import java.util.*;
import java.lang.*;



public class DialogEx2 extends WindowAdapter implements ActionListener
{
	JFrame frame;
	JLabel label1;
	JTextField field1;
	JButton button1, button2, button3;
	JDialog modelMyDialog, d2, d3;

	DialogEx2() {}
	
	public void start(Vector<String> filez)
	{
		frame   = new JFrame("Frame");
		button1 = new JButton("Open Modal Dialog");
		label1 = new JLabel("Click on the button to open a Modal Dialog");
		frame.add(label1);
		frame.add(button1);
		button1.addActionListener(this);
		
		int vlen = filez.size();
		int idx = 0;
		JButton y = null;
		int maxNameLen = 0;
		int nsz = 0;
		while (idx < vlen) {
			String fname = filez.elementAt(idx);
			y = new JButton(fname);
			y.addActionListener(this);
			frame.add(y);
			idx += 1;
			nsz = fname.length();
			if (nsz > maxNameLen) {
				maxNameLen = nsz;
			}
		}
		out("Max name length is " + nsz);

		JButton x = new JButton("x button");
		x.addActionListener(this);
		frame.add(x);

		frame.pack();

		frame.setLayout(new FlowLayout());
		frame.setSize(nsz*10,250);
		frame.setVisible(true);
	}
	private void out(String s){System.out.println(s);}
	public void actionPerformed(ActionEvent ae)
	{

		if(ae.getActionCommand().equals("Open Modal Dialog"))
		{
			out("open model dialog was pushed");
			//Creating a non-modeless blocking Dialog
			modelMyDialog= new JDialog(frame,"Ex2 Modal Dialog",true);
			JLabel label = new JLabel("You must close this dialog window to use Frame window",JLabel.CENTER);
			modelMyDialog.add(label);

			/*
	JButton c = new JButton("another button");
	c.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
			{
			out("Button pushed was the C button '"+e.getActionCommand()+"'");
			// display/center the jdialog when the button is pressed
			JDialog d = new JDialog(frame, "Hello", true);
			d.setLocationRelativeTo(frame);
			d.setVisible(true);
			frame.dispose();
			}
		}
	); // end "new Actionlistener()"
			 */

			//modelMyDialog.add(c);
			modelMyDialog.addWindowListener(this);
			modelMyDialog.pack();
			modelMyDialog.setLocationRelativeTo(frame);
			modelMyDialog.setLocation(new Point(100,100));
			modelMyDialog.setSize(400,200);
			modelMyDialog.setVisible(true);
		} else {
			out("was not 'modal dialog'");
			String buttLabel = ae.getActionCommand();
			out("Button pushed was the '"+buttLabel+"' button");
			//wasThisButton = buttLabel;
			setWasThisButton(buttLabel);
			Window w = frame;
			WindowEvent we = new WindowEvent(w, WindowEvent.WINDOW_CLOSING);
			this.frameClosing(we);
		}
	}
    public String wasThisButton = null;
    public void setWasThisButton(String b) {
    	wasThisButton = b;
    }
	public void windowClosing(WindowEvent we)
	{
		out("Closing modelMyDialog");
		modelMyDialog.setVisible(false);
	}
	public void frameClosing(WindowEvent we)
	{
		out("Closing frame");
		ActionListener listener = new ActionListener(){
		      public void actionPerformed(ActionEvent event){
		      System.out.println("Timer Evevnt Fired");
		     }
		 };
		 this.out("starting TIMER");
		 Timer timer = new Timer(5000, listener);
		 timer.setRepeats(false);
		 timer.start();
		 this.out("Ending Timer");
		 
		frame.setVisible(false);
	}

	public static void main(String...ar)
	{
		System.out.println("Starting DialogEx2");
		DialogEx2 x = new DialogEx2();
		Vector<String> vfiles = new Vector<String>();
		String a = new String("E:\\Libraries\\Pictures\\Camera Roll\\WIN_20170325_15_19_20_Pro.mp4");
		String b = new String("C:\\Users\\norm\\Documents\\AnyDVD_logs\\AnyDVD_7.6.9.0_Info_G_RIPLEY.zip");
		String c = new String("E:\\Libraries\\Music\\AC-DC\\RockOrBust\\(07) [AC-DC] Hard Times.flac");
		vfiles.add(a);
		vfiles.add(b);
		vfiles.add(c);
		
		x.start(vfiles);
		
		  
		 
	
		System.out.println("the button label pressed was " + x.wasThisButton);
		//Enter data using BufferReader 
		System.out.println("Want to try another?");
		BufferedReader reader =  
				new BufferedReader(new InputStreamReader(System.in)); 

		// Reading data using readLine 
		try {
			String name = reader.readLine();
		} catch (IOException ioe) 
		{ System.err.println("oops: " + ioe.getMessage());}
		System.out.println("Second Try on the button label pressed was " + x.wasThisButton);
		
        vfiles.add(c);
        vfiles.add(b);
        vfiles.add(a);
		x.start(vfiles);

	}

}
