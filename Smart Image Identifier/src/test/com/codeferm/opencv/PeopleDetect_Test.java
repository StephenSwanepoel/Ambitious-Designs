package test.com.codeferm.opencv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;

import com.codeferm.opencv.PeopleDetection;
import com.codeferm.opencv.PeopleDetectionRequest;
import com.codeferm.opencv.DefualtImpl.Image;
import com.codeferm.opencv.DefualtImpl.PeopleDetectImplementation;
import com.codeferm.opencv.DefualtImpl.PeopleDetectionResponse;

public class PeopleDetect_Test {

	private PeopleDetectImplementation detect;
	
	@Before
	public void creatingPeopleDetect()
	{
		detect = new PeopleDetectImplementation();
	}
	
	@After
	public void Destructer_PeopleDetect()
	{
		detect = null;
	}
		
	@Test  
	public void testGreyScale() {
		try {
			File input = new File("./resources/human3.jpg");
	        BufferedImage image = ImageIO.read(input);
	        Mat mat = detect.generateMat(image);			
			mat = detect.greyScale(image, mat);			
			Raster ras = image.getRaster();			
			int elem = ras.getNumDataElements();
			
			assertEquals(1,elem);
		}
		catch (Exception e)
		{
			
		}		
	}

	@Test
	public void testEqualization() {
		try {
			File input = new File("./resources/human3.jpg");
			BufferedImage image;	
			image = ImageIO.read(input);
			Mat mat = detect.generateMat(image);
			mat = detect.greyScale(image, mat);
			mat = detect.equalization(image, mat);
			
			assertEquals(false ,mat.empty());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEnlargeImage() {		
		try {
			File input = new File("./resources/human3.jpg");
			BufferedImage image;		
			image = ImageIO.read(input);			
			Mat mat = detect.generateMat(image);			
			mat = detect.enlargeImage(image, mat);
			
			assertEquals(640, mat.height());
			assertEquals(640, mat.width());	
		} catch (IOException e) {
			
		}
        
	}

	@Test
	public void testGenerateMat() {		
		try{
			File input = new File("./resources/human3.jpg");
			BufferedImage image;		
			image = ImageIO.read(input);		
			Mat mat = detect.generateMat(image);			
			assertEquals(false ,mat.empty());
		}
		catch (Exception e){}
	}

	@Test
	public void testGenerateImage() {
		try{
			String url = "./resources/human3.jpg";	
			File input = new File(url);
	        BufferedImage img = ImageIO.read(input);
			Image image = new Image();
			image.setBufferedImage(img);			
			
			detect.runTests(image);
			Boolean generatedImage = false;
			
			File folder = new File(".\\output\\database\\normal\\");
			File[] listOfFiles = folder.listFiles();
			for(int i=0; i<listOfFiles.length; i++)
			{				
				if(listOfFiles[i].getName().equals("human3.jpg"))
					generatedImage = true;
			}
			assertEquals(generatedImage, true);
			
		}
		catch (Exception e){}
	}
	
	@Test
	public void testPeopleDetection() throws SecurityException, IOException
	{
		Image img = new Image();
		img.setURL("./resources/human3.jpg");
		img.setID("test2");
		
		PeopleDetectionResponse p = new PeopleDetection().ProcessImage(new PeopleDetectionRequest(img));
		assertNotNull(p);
		
		Boolean generatedImage = false;
		
		File folder = new File(".\\output\\database\\normal\\");
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<listOfFiles.length; i++)
		{
			if(listOfFiles[i].getName().equals("human3.jpg"))
				generatedImage = true;
		}
		assertEquals(generatedImage, true);
	}

	@Test
	public void testProcessImage() {
		/*try {		
			assertEquals(true ,detect.processImage("./Test/Test_Sources/test2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		fail("Not yet implemented");
	}
	
	@Test
	public void test_runTests()
	{
		fail("Not yet implemented");
	}
}
