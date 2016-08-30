package com.codeferm.opencv.DefualtImpl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.codeferm.opencv.ImagePopup;

public class ImagePopupImplementation implements ImagePopup {

	private int count=0;
	private JFrame f;
	private LinkedList<JFrame> ll = new LinkedList<>();
	
	
	@Override
	public void popup(BufferedImage  bufferedimage) {
		f = new JFrame(); //creates jframe f
		ll.add(f);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size

	    ImageIcon image = new ImageIcon(bufferedimage);

	    JLabel lbl = new JLabel(image); //puts the image into a jlabel

	    f.getContentPane().add(lbl); //puts label inside the jframe

	    f.setSize(480, 640); //gets h and w of image and sets jframe to the size

	    int x = (screenSize.width - f.getSize().width)/2; //These two lines are the dimensions
	    int y = (screenSize.height - f.getSize().height)/2;//of the center of the screen

	    f.setLocation(0, count); //sets the location of the jframe
	    f.setVisible(true); //makes the jframe visible
	    count += 100;
	}
	
	@Override
	public void popup(String URL) {
		JFrame f = new JFrame(); //creates jframe f
		ll.add(f);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size

	    ImageIcon image = new ImageIcon(URL);

	    JLabel lbl = new JLabel(image); //puts the image into a jlabel

	    f.getContentPane().add(lbl); //puts label inside the jframe

	    f.setSize(480, 640); //gets h and w of image and sets jframe to the size

	    int x = (screenSize.width - f.getSize().width)/2; //These two lines are the dimensions
	    int y = (screenSize.height - f.getSize().height)/2;//of the center of the screen

	    f.setLocation((screenSize.width/3)*count++,0); //sets the location of the jframe
	    f.setVisible(true); //makes the jframe visible
	}
	
	@Override
	public void ClosePopup() 
	{		
		for(int i =0; i<ll.size(); i++)
		{
			ll.get(i).dispose();
		}
	}

}
