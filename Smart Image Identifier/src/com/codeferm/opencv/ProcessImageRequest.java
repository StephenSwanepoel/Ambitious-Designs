package com.codeferm.opencv;

import com.codeferm.opencv.DefualtImpl.Image;

public class ProcessImageRequest {
	private Image image;
	
	public ProcessImageRequest()
	{
		this.image = null;
	}
	
	public ProcessImageRequest(Image image)
	{
		this.image = image;
	}
	
	protected void setImage(Image image)
	{
		this.image = image;
	}
	
	public Image getImage()
	{
		return this.image;
	}
	
}
