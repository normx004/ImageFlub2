package imageflubber;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class WindowPositioner {

    private final String filename;
    private Properties properties;
    private MainFlub flub = null;
    private int xVal = 0;
    private int yVal = 0; 
    
    private void out (String s) {
    	try {
    		out("WindowPositioner: "+s);
    	} catch (java.lang.StackOverflowError e) {
    		System.err.println("Uh Oh, stack overflow "+ e.toString());
    		e.printStackTrace();
    	} catch (java.lang.Exception ee) {
    		System.err.println("uncaugut exception!!!!!!");
    		ee.printStackTrace();
    	}
    }

    public WindowPositioner( String filename, MainFlub fptr )
    {
        this.filename = filename;
        out(" filename to use for position is "+filename);
        this.flub = fptr;
    }

    private void setBounds( String key, Component c )
    {
        //key = key + c.getName();
    	key = key + "frame0";
    	out("Key is "+key);

        String position = properties.getProperty( key );
        out("Position is \'" + position + "\'");
        //if (c.getName() == null) {
        	//out("Uh oh, in window positioner, component name is null");
       // } else {
        	out("ok, component name is "+c.getName());
        	if (position == null) {
        		out("JuhJoh, in window poisitioner, position is null");
        	}
        //}
        if ( /*c.getName() != null &&*/ ! ((position.trim().length() > 0))) 
        		//StringUtils.isBlank( position ) )
        {
            String[] nums = position.split( "," );
            c.setBounds( Integer.parseInt( nums[0] ), Integer.parseInt( nums[1] ),
                         Integer.parseInt( nums[2] ), Integer.parseInt( nums[3] ) );
        }

        if ( c instanceof Container )
        {
            key = key + "/";
            Container container = (Container) c;
            for ( Component child : container.getComponents() )
               setBounds( key, child );
        } else {
        	out("ok, c was NOT a container");
        }
    }

    /**
     * Loads the properties from the .xml file and sets all named windows with a matching
     * name.
     *
     * @param component Any component in the Swing app.  The top-most container will be
     * determined from this component.
     */
    public void restore( Component component )
    {
    	out("CALLED RESTORE, component name is "+component.getName());
        properties = new Properties();
        InputStream is = null;
        try
        {
            is = new FileInputStream( filename );
            properties.loadFromXML( is );
            out("CALLED RESTORE: read XML file OK, I guess");
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            return;
        }
        finally
        {
        	try {
        	is.close();
        	} catch (IOException ioex) {
        		ioex.printStackTrace();
        	}
            //IOUtils.closeQuietly( is );
        }

        Component top = component;
        while ( top.getParent() != null ) {
        	out("restore window position, top compnentn now "+top.getName());
            top = top.getParent();
    	}

        setBounds( "", top );
    }

    private void getBounds( String key, Component c )
    {
    	out("GETBOUNDS was called, component is "+c.getName());
        key = key + c.getName();
        
        String position = String.format( "%d,%d,%d,%d", c.getX(), c.getY(), c.getWidth(), c.getHeight() );
        out ("position calculated as "+ position);
        this.setxVal(c.getX());
        this.setyVal(c.getY());
        out("New Position is x "+c.getX()+", y "+c.getY());
        /*
        properties.setProperty( key, position );
        if ( c instanceof Container )
        {
            key = key + "/";
            Container container = (Container) c;
            for ( Component child : container.getComponents() )
                getBounds( key, child );
        }
        */
    }

    public void save( Component component )
    {
    	out("SAVE was called");
        Component top = component;
        while ( top.getParent() != null )
            top = top.getParent();

        properties = new Properties();
        getBounds( "", top );

        OutputStream os = null;
        try
        {
            os = new FileOutputStream( filename );
            properties.storeToXML( os, "Browser" );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
        	try {
        	os.close();
        	} catch (IOException ioex) 
        	{ ioex.printStackTrace();}
            //IOUtils.closeQuietly( os );
        }
    }

	public int getxVal() {
		return xVal;
	}

	public void setxVal(int xVal) {
		this.xVal = xVal;
	}

	public int getyVal() {
		return yVal;
	}

	public void setyVal(int yVal) {
		this.yVal = yVal;
	}
}



