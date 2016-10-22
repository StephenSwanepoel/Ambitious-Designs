package com.codeferm.opencv.DefualtImpl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.codeferm.opencv.ImagePopup;

public class ImagePopupImplementation implements ImagePopup {

	private int count=0;
	private JFrame f;
	private LinkedList<JFrame> ll = new LinkedList<>();
	
	
	@Override
	/**
     * Method which produces a popup for displaying purposes
     * @param bufferedimage contains an image which is being processed
     */
	public void popup(BufferedImage  bufferedimage) {
		f = new JFrame(); //creates jframe f
		ll.add(f);
	    ImageIcon image = new ImageIcon(bufferedimage);
	    JLabel lbl = new JLabel(image); //puts the image into a jlabel	    
	    f.getContentPane().add(lbl); //puts label inside the jframe
	    f.setSize(480, 640); //gets h and w of image and sets jframe to the size
	    f.setLocation(0, count); //sets the location of the jframe
	    f.setVisible(true); //makes the jframe visible
	    count += 100;
	}
	
	@Override
	/**
     * Method which produces a popup for displaying purposes
     * @param URL contains the location of an image which is being processed
     */
	public void popup(String URL) {
		JFrame f = new JFrame(); //creates jframe f
		ll.add(f);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size	    
	    ImageIcon image = new ImageIcon(URL);
	    JLabel lbl = new JLabel(image); //puts the image into a jlabel	    
	    f.getContentPane().add(lbl); //puts label inside the jframe
	    f.setSize(448, 640); //gets h and w of image and sets jframe to the size
	    f.setLocation((screenSize.width/3)*count++,0); //sets the location of the jframe
	    f.setVisible(true); //makes the jframe visible
	}
	
	@Override
	/**
     * Method which closes all current popups
     */
	public void ClosePopup() 
	{		
		for(int i =0; i<ll.size(); i++)
		{
			ll.get(i).dispose();
		}
	}

}
