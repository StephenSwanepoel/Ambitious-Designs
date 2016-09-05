package test.com.codeferm.opencv;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.codeferm.opencv.*;
import com.codeferm.opencv.DefualtImpl.*;

public class PeopleDetect_Test {

	private PeopleDetect detect;
	private Database database;
	
	@Before
	public void creatingPeopleDetect()
	{
		detect = new PeopleDetectImplementation();
	}
	
	@Before
	public void creatingDatabase()
	{
		database = new Database();
	}
	
	@Test  
	public void testGreyScale() {
		try {
			File input = new File("./Test/Test_Sources/test.jpg");
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
		fail("Not yet implemented");
	}

	@Test
	public void testEnlargeImage() {
		
		try {
			File input = new File("./Test/Test_Sources/test2.jpg");
			BufferedImage image;		
			image = ImageIO.read(input);
			
			Mat mat = detect.generateMat(image);
			
			mat = detect.enlargeImage(image, mat);
			
			assertEquals(640, mat.height());
			assertEquals(480, mat.width());	
		} catch (IOException e) {
			
		}
        
	}

	@Test
	public void testGenerateMat() {
		
		try{
			File input = new File("./Test/Test_Sources/test2.jpg");
			BufferedImage image;		
			image = ImageIO.read(input);
		
			Mat mat = detect.generateMat(image);
			
			assertEquals(false ,mat.empty());
		}
		catch (Exception e)
		{
			
		}
	}

	@Test
	public void testGenerateImage() {
		try{
			File input = new File("./Test/Test_Sources/test2.jpg");
			BufferedImage image;		
			image = ImageIO.read(input);
			Mat mat = detect.generateMat(image);
			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			
			detect.generateImage(image, mat, data);
			File f = new File("./output/test.jpg");
			
			assertEquals(true, f.exists());
			
		}
		catch (Exception e)
		{
			
		}
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
	public void testDatabase() {
		assertEquals(true,database);
	}

	@Test
	public void testProcessVideo() {
		try {
			
			detect.processVideo("./resources/walking.avi");
			File f = new File("./output/test.avi");
			
			assertEquals(true, f.exists());
			
		}
		catch (Exception e)
		{
			
		}
	}

}
