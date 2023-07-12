package imageflubber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.lang.*;

public class ListDial {
    private JList<String> list;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;
    MainFlub fm_ = null;

    public ListDial(String message, JList<String> listToDisplay, MainFlub fm){
    	fm_ = fm;
        list = listToDisplay;
        label = new JLabel(message);
        createAndDisplayOptionPane();
    }


    public ListDial(String title, String message, JList<String> listToDisplay, MainFlub fm){
        this(message, listToDisplay, fm);
        dialog.setTitle(title);
    }
    private void out(String s){System.out.println("ListDial: "+s);}
    
    public int getHeight() {
    	int h = dialog.getHeight();
    	return h;
    }
    
    private void createAndDisplayOptionPane(){
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});
        dialog = optionPane.createDialog("Select option");
        out("Just created dialog");
    }

    private void setupButtons(){
        okButton = new JButton("Ok");
        okButton.addActionListener(e -> handleOkButtonClick(e));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> handleCancelButtonClick(e));
    }

    private JPanel layoutComponents(){
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.add(label, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements(){
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setOnOk(ActionListener event){
    	 out("Just did setOnOk");
    	 okEvent = event; }

    public void setOnClose(ActionListener event){
        cancelEvent  = event;
    }



    private void handleOkButtonClick(ActionEvent e){
    	  out("Just got into handleOkButtonClick, command is " + e.getActionCommand());
        out("Just got into handleOkButtonClick, param   is " + e.paramString());
        if(okEvent != null){ okEvent.actionPerformed(e); }
        hide();
    }

    private void handleCancelButtonClick(ActionEvent e){
        if(cancelEvent != null){ cancelEvent.actionPerformed(e);}
        hide();
    }

    public void show(){ dialog.setVisible(true); }
    
    public void show(int x, int y){
    	dialog.setLocation(x,y);
    	dialog.setVisible(true); }

    private void hide(){ dialog.setVisible(false); }

    public Object getSelectedItem(){ return list.getSelectedValue(); }
    
   public static void main (String args[]) {
	MainFlub fm = null;
   	JList<String> list = new JList<String>(new String[] {"foo", "bar", "foobar"});
        ListDial dialog = new ListDial("Copy or Move to : ", list, fm);
        dialog.setOnOk(e -> System.out.println("Chosen item: " + dialog.getSelectedItem()));
        dialog.show();
 
    }
    
}