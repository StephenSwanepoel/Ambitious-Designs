package com.codeferm.opencv;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import com.codeferm.opencv.PeopleDetection;
import com.codeferm.opencv.PeopleDetectionRequest;
import com.codeferm.opencv.DefualtImpl.PeopleDetectImplementation;
import com.codeferm.opencv.DefualtImpl.PeopleDetectionResponse;

public class PeopleDetection {

	public PeopleDetectionResponse ProcessImage(PeopleDetectionRequest request) throws SecurityException, IOException {		
        new File("./output/audit-logs").mkdirs();
		final FileHandler fh = new FileHandler("./output/audit-logs/audit.log",true);    		
	 		
		try {
			
			if(new PeopleDetectImplementation(fh).runTests(request.getImage()))
				return new PeopleDetectionResponse();
			else
				return null;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			fh.close();
		}
		return null;
	}

}
