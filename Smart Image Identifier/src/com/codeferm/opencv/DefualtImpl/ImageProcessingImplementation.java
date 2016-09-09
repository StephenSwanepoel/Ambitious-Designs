package com.codeferm.opencv.DefualtImpl;

import java.io.IOException;

import com.codeferm.opencv.PeopleDetection;
import com.codeferm.opencv.PeopleDetect;
import com.codeferm.opencv.ProcessImageRequest;

public class ImageProcessingImplementation {

	public ProcessImageResponse ProcessImage(ProcessImageRequest request) {
		
		/*PeopleDetect detect = new PeopleDetectImplementation();
		
		try {
			detect.runTests(""); //detect.runTest(request.getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		*/ProcessImageResponse response = new ProcessImageResponse();
		return response;
	}

}
