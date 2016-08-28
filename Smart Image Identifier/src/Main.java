import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.opencv.core.Core;

import com.codeferm.opencv.*;
import com.codeferm.opencv.DefualtImpl.*;;

public class Main {

	public static void main(final String... args) throws IOException {
	
		PeopleDetect p = new PeopleDetectImplementation();
	
		File folder = new File("./resources");
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0; i< listOfFiles.length; i++)
		{
			System.out.println(listOfFiles[i].getPath());
			if(listOfFiles[i].getPath().contains(".jpg"))
				p.runTests(listOfFiles[i].getPath());
		}
	}
    
}
