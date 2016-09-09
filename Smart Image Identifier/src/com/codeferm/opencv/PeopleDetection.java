package com.codeferm.opencv;

import java.io.IOException;

import com.codeferm.opencv.PeopleDetection;
import com.codeferm.opencv.PeopleDetectionRequest;
import com.codeferm.opencv.DefualtImpl.PeopleDetectImplementation;
import com.codeferm.opencv.DefualtImpl.PeopleDetectionResponse;

public class PeopleDetection {

	public PeopleDetectionResponse ProcessImage(PeopleDetectionRequest request) {	
		
		try {
			if(new PeopleDetectImplementation().runTests(request.getImage().getURL()))
			{
				return new PeopleDetectionResponse();
			}
			else
				return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
