package com.codeferm.opencv;

import com.codeferm.opencv.DefualtImpl.Image;

public class PeopleDetectionRequest {
	private Image image;
	
	public PeopleDetectionRequest()
	{
		this.image = null;
	}
	
	public PeopleDetectionRequest(Image image)
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
