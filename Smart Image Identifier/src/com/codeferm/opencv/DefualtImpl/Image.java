package com.codeferm.opencv.DefualtImpl;

import java.awt.image.BufferedImage;

public class Image {
		private BufferedImage image;
		private String id;
		private String url;
		
		public Image()
		{
			this.image = null;
			id = "";
			url = "";
		}
		
		public void setBufferedImage(BufferedImage image)
		{
			this.image = image;
		}
		
		public void setID(String id)
		{
			this.id = id;
		}
		
		public void setURL(String url)
		{
			this.url = url;
		}
		
		public BufferedImage getBufferedImage()
		{
			return this.image;
		}
		
		public String getID()
		{
			return this.id;
		}
		
		public String getURL()
		{
			return this.url;
		}
		
		
}
