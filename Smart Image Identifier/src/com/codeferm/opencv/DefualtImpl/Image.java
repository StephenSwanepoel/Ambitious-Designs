package com.codeferm.opencv.DefualtImpl;

import java.awt.image.BufferedImage;

public class Image {
		private BufferedImage image;
		
		public Image(BufferedImage image)
		{
			this.image = image;
		}
		
		public BufferedImage getBufferedImage()
		{
			return image;
		}
}
