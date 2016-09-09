package com.codeferm.opencv.DefualtImpl;

import java.io.IOException;

import com.codeferm.opencv.PeopleDetection;
import com.codeferm.opencv.PeopleDetect;
import com.codeferm.opencv.PeopleDetectionRequest;

public class ImageProcessingImplementation {

	public PeopleDetectionResponse ProcessImage(PeopleDetectionRequest request) {
		
		/*PeopleDetect detect = new PeopleDetectImplementation();
		
		try {
			detect.runTests(""); //detect.runTest(request.getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		*/PeopleDetectionResponse response = new PeopleDetectionResponse();
		return response;
	}

}
