package imageflubber;

import java.lang.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainFlub  {
	
/*
	 * REGISTRY

c:\temp>rmiregistry
C:\temp>set %CLASSPATH%
Environment variable .;C:\Program Files (x86)\Java\jre6\lib\ext\QTJava.zip;"c:\temp\IMFlubServer.jar";"c:\temp\IMFlubServer.jar";c:\temp;c:\temp\rmi;c:\temp\rmi\example;c:\temp\rmi\example\hello not defined

SERVER
       C:\temp\server>java -classpath . imageflubber.RemoteChooserServer
Ok, have constructed server object
Have constructed STUB
Have Located registry
Server Ready
remote object about to create a new blank file chooser, but did we leave off somewhere?
exception q:\temp\lastGoodFile.txt (The system cannot find the path specified)
Read  C:\Users\normx004\Pictures\2006-04-09-collegetrip\Brown\shrink.jpg
parent of old path read in from file is C:\Users\normx004\Pictures\2006-04-09-collegetrip\Brown
Setting selected file to C:\Users\normx004\Pictures\2006-04-09-collegetrip\Brown\shrink.jpg
returned from show file chooser
filechooser in GetDir returned directory C:\Users\normx004\Pictures\2006-04-09-collegetrip\Brown, file: IMG_7200.JPG
this is the remote obj,  got back 'C:\Users\normx004\Pictures\2006-04-09-collegetrip\Brown\IMG_7200.JPG'

C:\temp\server>set CLASSPATH
CLASSPATH=.;"c:\temp\IMFlubServer.jar";flubrmi

C:\temp\server>dir
 Volume in drive C is BiggusDiskus
 Volume Serial Number is B092-1A43

 Directory of C:\temp\server

03/26/2012  11:06 PM    <DIR>          .
03/26/2012  11:06 PM    <DIR>          ..
02/19/2012  10:49 AM               408 .classpath
02/19/2012  10:45 AM               386 .project
03/26/2012  01:47 PM    <DIR>          imageflubber
03/26/2012  11:06 PM    <DIR>          META-INF
               2 File(s)            794 bytes
               4 Dir(s)  328,674,193,408 bytes free
MAINFLUB
same env as SERVER but
     java -classpath . imageflubber.MainFlub
	 
	 */

	
	static final String AltCopy = new String("AltCopy");
	public static String getAltcopy() {
		return AltCopy;
	}
	static final String AltMove = new String("AltMove");

	public static String getAltmove() {
		return AltMove;
	}
	String arg0 = null;
	
	private String PositionFile = "C:\\temp\\FlubWindowPosition.txt";
	public String getPositionFile() {
		return PositionFile;
	}
	public void setPositionFile(String positionFile) {
		PositionFile = positionFile;
	}
	WindowPositioner windowPositioner = null;
	public WindowPositioner getWindowPositioner() {
		if (windowPositioner==null) {
			windowPositioner = new WindowPositioner(PositionFile,this);
		}
		return windowPositioner;
	}
	public void setWindowPositioner(WindowPositioner windowPositioner) {
		this.windowPositioner = windowPositioner;
	}
	
	String screenID = null;
	public String getScreenID() {
		return screenID;
	}
	public void setScreenID(String screenID) {
		this.screenID = screenID;
	}
	
	
	private JFrame yornFrame = null;
	public JFrame  getYornFrame() {return yornFrame;	}
	public void    setYornFrame(JFrame yornFrame) {this.yornFrame = yornFrame;	}
	
	private String cmdFilePath = new String("\\temp\\delum.bat");
	public String getCmdFilePath() {return cmdFilePath;}
	public void setCmdFilePath(String cmdFilePath) {this.cmdFilePath = cmdFilePath;	}

	private int imBufSize = 10;	
	public int  getImBufSize() {	return imBufSize;}
	public void setImBufSize(int imBufSize) {this.imBufSize = imBufSize;	}

	LoadImageApp   ima = null;
	public LoadImageApp getIma()         {	return ima;	}
	public void setIma(LoadImageApp ima) {	this.ima = ima;	}

	private LinkedBlockingQueue<LoadImageApp> myLbq = null;
	public           LinkedBlockingQueue<LoadImageApp> getMyLbq() {		return myLbq;	}
	public void setMyLbq(LinkedBlockingQueue<LoadImageApp> myLbq) {		this.myLbq = myLbq;	}
	
	private     ImgQFiller myImgQF = null;
	public      ImgQFiller getMyImgQF() {		return myImgQF;	}
	public void setMyImgQF(ImgQFiller myImgQF) {		this.myImgQF = myImgQF;	}
	
	private     boolean qFillerDone = false;
	public boolean      isQFillerDone() {	return qFillerDone;	}
	public void         setQFillerDone(boolean qFillerDone) {	this.qFillerDone = qFillerDone;	}
	
	JCheckBox jcb_ = null;
	public JCheckBox getNefCheck() {return jcb_;}
	public void      setNefCheck (JCheckBox jcbx) {this.jcb_ = jcbx;	}

	int    listPointer = 0;
	
    //public static boolean staticDebug = false;
    public static boolean staticDebug = true;
    public static boolean isStaticDebug() {	return staticDebug;	}
	public static void    setStaticDebug(boolean staticDebug) {MainFlub.staticDebug = staticDebug;	}

	//boolean        debug = false;
	boolean        debug = true;
	public boolean isDebug() {		return debug;	}
	public void    setDebug(boolean debug) {		this.debug = debug;	}

	private boolean allDone = false;
	public boolean  isAllDone() {return allDone;	}
	public void     setAllDone(boolean allDone) {this.allDone = allDone;}
	
	private String moveListString_ = null;
	private String copyListString_ = null;
	public  String getMoveListString() {return moveListString_;	}
	public  void   setMoveListString(String moveList_) {this.moveListString_ = moveList_;	}
	public  String getCopyListString() {return copyListString_;}
	public  void   setCopyListString(String copyList_) {this.copyListString_ = copyList_;	}
	private JList  moveList_ = null;
	private JList  copyList_ = null;
	public  JList  getMoveList() {return moveList_;}
	public  void   setMoveList(JList moveList) {this.moveList_ = moveList;}
	public  JList  getCopyList() {return copyList_;}
	public  void   setCopyList(JList copyList) {this.copyList_ = copyList;}
	
	
	
	private JFrame frayme = null;
	public  JFrame getFrayme() {return frayme;}
	public void setFrayme(JFrame frayme) {this.frayme = frayme;}

	private JPanel reviewPanel = null;
	public  JPanel getReviewPanel() {return reviewPanel;	}
	public   void  setReviewPanel(JPanel reviewPanel) {this.reviewPanel = reviewPanel;	}

	private JPanel buttPanel = null;
	public  JPanel getButtPanel() {return buttPanel;	}
	public  void   setButtPanel(JPanel buttPanel) {this.buttPanel = buttPanel;}

	private File dir_ = null;
	public  File getDir()       { 
		if (dir_ == null) {
			out("no dir set currently");
		} else {
		out("provding current dir: " + dir_.getPath());
		}
		return dir_;}
	public  void setDir(File f) { 
		out("setting current dir: " + f.getPath());
		dir_= f;}
	
	private GetDir dirObj = null;
	public  GetDir getDirObj() {					return dirObj;	}
	public  void   setDirObj(GetDir dirObj) {		this.dirObj = dirObj;	}

	private DirReader dr_ = null;
	public  DirReader getDirReader()            { return dr_;}
	public  void      setDirReader(DirReader f) { dr_= f;}
	
	private File4Img imgFile_ = null;
	public  void setFile(File4Img f) { imgFile_ = f;}
	public  File4Img getFile()       { return imgFile_;}
	
	private LinkedList<File4Img> delList = new LinkedList<File4Img>();
	public  LinkedList<File4Img> getDelList() {return delList;}
	public  void             addToDelList(File4Img item) {delList.add(item);}
	
	
	private LinkedList<File4Img> bigList = new LinkedList<File4Img>();
	public  LinkedList<File4Img> getBigList() {return bigList;}
	public  void             setBigList(LinkedList<File4Img> ll) {bigList = ll;	}
	public  void             addToBigList(File4Img item) {bigList.add(item);}
	
	private ReentrantLock             fmLock = null; 
	public  void                      lock() { fmLock.lock();}
	public  void                      unlock() { fmLock.unlock();}
	
	boolean firstTime = true;   
	public boolean isFirstTime() {		return firstTime;}
	public void setFirstTime(boolean firstTime) {this.firstTime = firstTime;}
	//--------------------Target PATHS------------------------------------------------
	private String moveToPath = null;
	public String getMoveToPath()                  {return moveToPath;}
	public void   setMoveToPath(String moveToPath) {
		out("setting move-to path to " + moveToPath);
		this.moveToPath = moveToPath;}
	
	private String copyToPath = null;
	public String getCopyToPath()                  {return copyToPath;}
	public void   setCopyToPath(String copyToPath) {
		out("Setting copy-to path to " + copyToPath);
		this.copyToPath = copyToPath;}
	
	private String altCopyToPath = null;
	private String altMoveToPath = null;
	public String getAltCopyToPath() {
		return altCopyToPath;
	}
	public void setAltCopyToPath(String altCopyToPath) {
		this.altCopyToPath = altCopyToPath;
	}
	public String getAltMoveToPath() {
		return altMoveToPath;
	}
	public void setAltMoveToPath(String altMoveToPath) {
		this.altMoveToPath = altMoveToPath;
	}
	public boolean isWindows = false;
	public boolean isWindows() {
			 WorLinux w = new WorLinux();
			 isWindows = w.izzit();
		return isWindows;
	}
	
	
	
	
	//--------------------------------------BUILDYORNFRAME----------------------------------
	
	
	public void buildYornFrame() {   
		    JFrame f = null;
		   
		    // Create a New Frame, add a window listener
		    String fTitle = new String( "Keep, Discard or Move File Controls from " + this.getDir().getPath());
		    out("buidYornFrame: new frame title is "+fTitle);
	    	f = new JFrame(fTitle);
	    	f.addWindowListener(new WindowAdapter(){	        	
	             public void windowClosing(WindowEvent e) {
	            	 popDeleteRunner();
	            	 System.exit(0);
	            }
	         });

  	        out("buildYornFrame: calling MakeFileYorNPanel constructor");
	        MakeFileYorNPanel yorn = new MakeFileYorNPanel(this);
	        out("buildYornFrame: now calling MakeFileYorNPanel getButtons");
	        JPanel bpan = yorn.getButtons();
	        out("buildYornFrame:returned from yorn.getButtons");
	        setButtPanel(bpan);
	      
	        f.add(getButtPanel());
	        
	        JCheckBox jcb = new JCheckBox("delete NEF?");
	        setNefCheck(jcb);
	        bpan.add(jcb);
	        
	        JButton undo = new JButton("undo");
	        undo.addActionListener(new ActionListener() { 
	            public void actionPerformed(ActionEvent e) { 
	                //popDeleteRunner();
	                //System.exit(0);
	            	undoit();
	            	out("&&&&&&&&&&&&&&&&&&&want to undo last move or delete&&&&&&&&&&&&&&&&&&&&");
	            } 
	        });
	        bpan.add(undo);
	        
	        JButton runDelete = new JButton("run delete?");
	        runDelete.addActionListener(new ActionListener() { 
	            public void actionPerformed(ActionEvent e) { 
	                popDeleteRunner();
	                System.exit(0);
	            } 
	        });
	        bpan.add(runDelete);
	        
	        //out("packing");
	        f.pack();
	        out("buildYornFrame:Setting 'yorn' frame visible");
	        f.setLocation(600, 900);
	        //f.setLocation(60,100);
	        //Dimension d = new Dimension(500,200);
	        f.setSize(1300,100);
	        f.setVisible(true);
	        setYornFrame(f);
	
	}
	//---------------------------------DOIT---------------------------
	public boolean doit() {
	       doit_local();	
	       return true;
	}
    public boolean doit_local() {
    	
    	    WindowPositioner wp = null;
		    
		    JFrame f = null;   
		    if (getFrayme() != null) {
		    	JFrame z = getFrayme();
		    	out("DOIT LOCAL - calling window location save function");
		    	// call frame position retrieval function
		    	//wp = getWindowPositioner();
		    	//wp.save(z);
		    	out("DOIT LOCAL - Just saved window position");
		    	z.setVisible(false);
		    	z.dispose();
		    	out ("Just Fired the old frame, and 'disposed' it");
		    }
		 
		    // Create a New Frame, add a window listener
	    	f = new JFrame("Load Image Sample");
	    	// get position of last window displayed...
	    	int x = 0;
	    	int y = 0;
	    	if (wp != null) {
	    		wp = this.getWindowPositioner();
	    		// force frame to that position
		    	//out("MainFlub: repositioning window");
		    	//wp.restore( f );
	    		x = wp.getxVal();
	    		y = wp.getyVal();
		    }
	    	
	    	setFrayme(f);
	    	f.setLocation(x, y);
		    f.addWindowListener(new WindowAdapter(){	        	
	             public void windowClosing(WindowEvent e) {
	             System.exit(0);
	            }
	         });
		
		    Box  box = Box.createVerticalBox(); 
		   
		    if ( firstTime) {
		    	out("Doit:First Time calling nextfile");
		    	firstTime = false;
		    	this.getDirReader().nextFile();	
		    	buildYornFrame();
		    }
		    out("DOIT:contents:         "+getIma().toString());
 	        box.add(getIma());
 	        
	        out("DOIT:adding box to frame");
	        getFrayme().add(box, BorderLayout.CENTER);
	        //out("packing");
	        getFrayme().pack();
	        out("DOIT:Setting visibleSetting visibleSetting visibleSetting visibleSetting visibleSetting visibleSetting visible");
	        //f.setLocation(600,10);
	        getFrayme().setVisible(true);
	
		return true;
	}
    void popDeleteRunner() {
    	System.out.println("THIS IS THE DELETE RUNNER");
    	/*
    	JOptionPane.showInternalConfirmDialog(getFrayme(),
                "Run the Delete-Photo Script NOW?", "information",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                */
    	JOptionPane pane = new JOptionPane("ScriptRunner");
        pane.setMessage("Want to run the delete NOW?"); // Configure
        Object[] myOpts = { "yes, run", "no, don't run", "cancel"};
       
        
        //JDialog dialog = pane.createDialog(pane, "delete dialog");
        //dialog.show();
        int val = pane.showOptionDialog(getFrayme(), "want to run delete now?", "deleter", JOptionPane.YES_NO_CANCEL_OPTION, 
        		JOptionPane.QUESTION_MESSAGE, null, myOpts, null);
        Object selectedValue = pane.getValue();
        System.out.println("the selected value is a " + selectedValue.getClass().getName());
        Object[] options = pane.getOptions();
        if(selectedValue == null)
        {
        	System.out.println("uh oh, null value in option pane");
          return;
        } else {
        	System.out.println("selected values is " + selectedValue);
        	System.out.println("and returned value from show option dialog is " + val);
        }
        if (val == 0 ) {
        	System.out.println("DELETE selected"); 
        	TryProcessBuilder tp = new TryProcessBuilder();
        	tp.doit();
        	} else { 
        		if (val == 1) {
        			System.out.println("DO NOT delete selected");
        		} else {
        			System.out.println("Must be cancel");
        		}
        }
    }    
        
    //************************undoit***************************
    void undoit() {
        	if (isWindows()) {     	
        	// in a DOS shell, this works:
        	//  "c:\\Program Files\\Git\\usr\\bin\\bash" /q/bin/chopIt.bash /q/temp/delum.bat
        	//
        	    String       cmdString2 = new String("q:\\bin\\chopIt.bash");
        	    String       cmdString3 = new String("/q/temp/delum.bat");
        	    String       cmdString1 = new String("c:\\Progra~1\\Git\\usr\\bin\\bash ");
        	    ArrayList<String> cmdList = new ArrayList<String>();
        	    cmdList.add(cmdString1);
        	    cmdList.add(cmdString2);
        	    cmdList.add(cmdString3);
        	    
        	    //cmdStringB.append(" /q/bin/chopIt.bash /q/temp/delum.bat");
        	    System.out.println("THIS IS THE UNDOIT");
            	
            	TryProcessBuilder tp = new TryProcessBuilder();
            	tp.doit(cmdList);
        }  else {
        	  //setCmdString3("/tmp/delum.bash"); // cmdString3 not used except in block just above here
        	  JOptionPane.showMessageDialog(null, "NO undoit in Linux (yet...)", "TRIED UNDOIT",JOptionPane.PLAIN_MESSAGE );            
        }   	
        
    }
	//---------------------------SETUP-----------------------------------------
	// find the place to start looking at files- either from the first
	// argument on the command line, or from a file chooser.
	public void setup(String arg0) {
		String aarg = arg0;
		if (aarg == null) {
			aarg = new String("nullllll");
		}
		boolean isW = isWindows();
		if (! isW ) {
			setCmdFilePath("/tmp/delum.bash");
			//setCmdString3("/tmp/delum.bash"); cmdString3 not used anywhere where it conflicts...
		}
		out("getting directory "+aarg);
		File fyle = null;
        // if specified on comamnd line, make sure it is a real directory or file that exists
        if (arg0 != null) {
        	out("arg0 is "+arg0);
        	fyle = new File ( arg0);
        	out("fyle path is " + fyle.getParent() + ", file name is " + fyle.getName());
        	out("file exists for fyle is " + fyle.exists());
        	if (  fyle.exists()) {
        		out("found file ok - "+arg0); 
        		if (fyle.isDirectory()) {
        			setDirReader(new DirReader(fyle,this));
        			return;
        		}
        	} else {
        		out("file "+ arg0 + " could not be found. will use file chooser");
        		fyle = null;
        	}
        } else {
        	// ok, no file existed
            out("Contructing GetDir object");
    		GetDir gd = new GetDir(this);
    		setDirObj(gd);
    		
    		out("filename chosen from file chooser is  "+getFile().getName());
    		out("from chooser, path is                 "+getFile().getParent());
    		setDir(new File(getFile().getParent()));
    		setDirReader(new DirReader(getFile(), this));
        }
        // OK got the big list of files, time to start processing some images
        setMyImgQF(new ImgQFiller(this));
		this.getMyImgQF().start();
		out("SETUP COMPLETED");
	}
	//----------------------------CONSTRUCTOR------------------------------------------------
	 public MainFlub() {
		 constructMe(null);
	 }
	 public MainFlub(String[] args) {
		 constructMe(args);
	 }
	 private void constructMe(String[] args){
		 out("constructor001");
		 if (args != null) {
			 int isx=0;
			 while (isx < args.length) {
				 out("arg "+isx+" = "+args[isx]);
				 isx +=1 ;
			 }
		 }
		 
		 boolean isWin = this.isWindows();
		 if (! isWin ) {
		     setCmdFilePath("/tmp/delum.bash");
		 }
		 
		 
		 
		 
		 Properties p = System.getProperties();
		  String d = p.getProperty("debug");
		  if ( d != null ) {
			  setDebug(true);
			  MainFlub.setStaticDebug(true);
		  } 
		  String i = p.getProperty("imbuf");
		  if ( i != null ) {
			  Integer ib = new Integer(i);
			  setImBufSize(ib.intValue());
		  }
		  String pr = p.getProperty("props");
		  out("props file is "+pr);
		  if (pr != null) {
			    Properties prop = new Properties(); 
		    	try {
		               //load a properties file
		    		prop.load(new FileInputStream(pr));		 
		               //get the property value and print it out
		            out(prop.getProperty("debug"));
		            d = prop.getProperty("debug");
		  		    if ( d != null ) {
		  		      if (d.compareToIgnoreCase("true")==0) {
		  		    	setDebug(true);
			  			MainFlub.setStaticDebug(true);      
		  		      }
		  		    } 
		  		    //
		  		    // Set up 'alternate' copy-to and move-to lists that pop up if 
		  		    // you want different targets than the main ones; this is 
		  		    // also defined in the props file
		  		    String altDelim = prop.getProperty("altDelim");
		  		    if (altDelim != null) {
		  		    	out ("found delimiter for alt-move and alt-copy, it is '"+altDelim+"'");
		  		    }
		  		    
		  		    setMoveListString( prop.getProperty("moveList"));
		  		    if (this.getMoveListString() != null) {
		  		    	out("list of move-to directories is "+this.getMoveListString());
		  		    	/*
		  		    	String[] mvto = this.getMoveListString().split(altDelim,0);
		  		    	out("list contains "+mvto.length+" strings");
		  		    	JList<String> list = new JList<String>(mvto);
		  		    	*/
		  		    	setMoveList(GetMoveCopyTarget.getListFromProps(this.getMoveListString(), altDelim));
		  		    }
		  		    setCopyListString( prop.getProperty("copyList"));
		  		    if (this.getCopyListString() != null) {
		  		    	out("list of copy-to directories is "+this.getCopyListString());
		  		    	setCopyList(GetMoveCopyTarget.getListFromProps(this.getCopyListString(), altDelim));
		  		    }
		    		out(prop.getProperty("imbuf"));
		    		i = prop.getProperty("imbuf");
		  		    if ( i != null ) {
		  			  Integer ib = new Integer(i);
		  			  setImBufSize(ib.intValue());
		  			  out("just set imbuf size to "+ib.intValue());
		  		    }
		    		//System.out.println(prop.getProperty("moveToPath"));
		    		String moveToPath = prop.getProperty("moveToPath");
		    		if (moveToPath != null) {
		    			this.setMoveToPath(moveToPath);
		    		} else {
		    			this.setMoveToPath("c:\\temp\\tempix");
		    		}
		    		//System.out.println(prop.getProperty("copyToPath"));
		    		String copyToPath = prop.getProperty("copyToPath");
		    		if (copyToPath != null) {
		    			this.setCopyToPath(copyToPath);
		    		} else {
		    			this.setCopyToPath("c:\\temp\\tempix");
		    		}
		    	} catch (IOException ex) {
		    		ex.printStackTrace();
		        }
		  }
			  
		 setMyLbq(new LinkedBlockingQueue<LoadImageApp>(getImBufSize()));
		 fmLock = new ReentrantLock();
		 
		 
	 }
	 //-----------------------------------------------------------------------
	 public void out (String s) {
		 if (this.isDebug()) {
			 System.out.println("MainFlub: " + s);	 
		 }
	 }
	
	 
	  public static void main (String[] args){
		 
		  MainFlub fm = new MainFlub(args);
		  
		 
			 
		  
		  String arg = null;
		  if (args.length > 0) {
			  arg = args[0];
		  }
		  fm.setup(arg);
		  fm.doit();
	  }
	public void setWindows(boolean isWindows) {
		this.isWindows = isWindows;
	}
	
	
	
	
	
}
