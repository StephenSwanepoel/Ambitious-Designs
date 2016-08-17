import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.opencv.core.Core;

import com.codeferm.opencv.*;
import com.codeferm.opencv.DefualtImpl.*;;


public class Main {

	public static void main(final String... args) throws IOException {
	
		PeopleDetect p = new PeopleDetectImplementation();
		
		
    String url = null;        
    // Check how many arguments were passed in
    if (args.length == 0) {
        // If no arguments were passed then default to local file
    	url = "./resources/human2.jpg";
    } else {
    	url = args[0];
    }
    
    
    try {
        LogManager.getLogManager()
                .readConfiguration(PeopleDetect.class.getClassLoader().getResourceAsStream("logging.properties"));
    } catch (SecurityException | IOException e) {
        e.printStackTrace();
    }                   
    if (url.contains(".jpg") == true)
        p.runTests(url);
    else
    	p.processVideo(url);
	}
}
