package com.codeferm.opencv;

import java.io.IOException;

import com.codeferm.opencv.PeopleDetection;
import com.codeferm.opencv.ProcessImageRequest;
import com.codeferm.opencv.DefualtImpl.PeopleDetectImplementation;
import com.codeferm.opencv.DefualtImpl.ProcessImageResponse;

public class PeopleDetection {

	public ProcessImageResponse ProcessImage(ProcessImageRequest request) {	
		
		try {
			if(new PeopleDetectImplementation().runTests(request.getImage().getURL()))
			{
				return new ProcessImageResponse();
			}
			else
				return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
