/**
 * Ambitious Designs - Smart Image Identifier
 * 
 * Group members
 * 
 * Stephen Swanepoel
 * Dian Veldsman
 */
//Include OpenCV framework
package com.codeferm.opencv.DefualtImpl;
//Libraries (general)
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
//Libraries required for logging system events
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//Library required for read/write operations for images
import javax.imageio.ImageIO;

//Libraries required for creating BufferedImage instances
import java.awt.image.*;

//Libraries required for defining mat objects as well as
//performing various operations on them
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;	//Testing
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier; //Testing
//Library required for drawing onto image
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
//Libraries required for video handling
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import com.codeferm.opencv.ImagePopup;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.LawOfDemeter", "PMD.AvoidLiteralsInIfCondition",
        "PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidUsingNativeCode", "PMD.AvoidFinalLocalVariable",
        "PMD.CommentSize", "PMD.AvoidPrintStackTrace", "PMD.UseProperClassLoader", "PMD.AvoidPrefixingMethodParameters",
        "PMD.DataflowAnomalyAnalysis" })
public class PeopleDetectImplementation {
    /**
     * Logger
     */
    // Logger is not a constant
    @SuppressWarnings({ "checkstyle:constantname", "PMD.VariableNamingConventions" })
    private static final Logger logger = Logger.getLogger(PeopleDetectImplementation.class.getName());
    /** 
     * Load the OpenCV system library 
     * Load dynamic library     * 
     */
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private boolean[] successfulTests = new boolean[3];
    private ImagePopup pop;
    
    /**
     * Method which takes two arguments, improving luminance, which is by far more important 
     * in distinguishing visual features
     * @param image a BufferedImage object used to create a new mat with the same dimensions
     * @param mat a Mat object which is used to duplicate an image
     * @return Returns a grey scaled mat object
     */
    public Mat greyScale(BufferedImage image, Mat mat){
    	
        Mat mat2 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
        Imgproc.cvtColor(mat, mat2, Imgproc.COLOR_RGB2GRAY);

        return mat2;
    }
    
    /**
     * Method which performs a Histogram Equalization, improving contrast in the image
     * @param image a BufferedImage object used to create a new mat with the same dimensions
     * @param mat a Mat object which is used to duplicate an image
     * @return Returns a Mat object of the image with an increase in contrast
     */
    public Mat equalization(BufferedImage image, Mat mat){
    	
    	Mat mat2 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
        Imgproc.equalizeHist(mat, mat2);
        
    	return mat2;
    }
   
    /**
     * Method which enlarges a provided image to a Mat object
     * @param image a BufferedImage object gets enlarged
     * @param mat a Mat object which is used to store the enlarged image and returned to the user
     * @param Returns the modified Mat object
     */
    public Mat enlargeImage(BufferedImage image, Mat mat){
    	
    	Mat mat2 = new Mat();
    	Imgproc.resize(mat, mat2, new Size(640,640));
    	
    	return mat2;
    }
    
    /**
     * Method which generates a Mat object used for various operations
     * @param image a BufferedImage object used to create a new mat with the same dimensions
     * as well provide pixel writing capabilities - image.getRaster().
     * @return Returns a standard Mat object of the image 
     */
    public Mat generateMat(BufferedImage image){
    	   		  
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
	        
        return mat;
    }
    
    /**
     * Method which generates an image to store the Mat object into a .jpg file
     * @param image image a BufferedImage object used to create a new mat with the same dimensions
     * @param mat a Mat object which is used to duplicate an image
     * @param data provides pixel writing capabilities - image.getRaster()
     */
    public void generateImage(BufferedImage image, Mat mat, byte[] data, String url, char type){
    	
    	//Create an image file to store the mat object into (Same dimensions as original image)
    	BufferedImage output = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_BYTE_GRAY);
    	output.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
    	//Write the contents of the mat object to the output variable
    	String outputFile = ".\\output\\database\\";
        new File("./output/database").mkdirs();
        new File("./output/database/normal").mkdirs();
        new File("./output/database/greyScale").mkdirs();
        new File("./output/database/EQ").mkdirs();
    	String img;
    	
    	//Database images
    	if (url.contains("database"))
    		img = url.substring(21);
    	//Standard images in resource folder
    	else 
    		img = url.substring(12);
    		
    	switch(type)
    	{    	
	    	case 'N':
	    		outputFile += "normal\\" + img;
	    		Imgcodecs.imwrite(outputFile, mat);
	    		pop.popup(outputFile);
	    		break;
	    	case 'G':
	    		outputFile += "greyScale\\" + img;
	    		Imgcodecs.imwrite(outputFile, mat);
	    		pop.popup(outputFile);
	    		break;
	    	case 'E':
	    		outputFile += "EQ\\" + img;
	    		Imgcodecs.imwrite(outputFile, mat);
	    		pop.popup(outputFile);
	    		break;
    	}
    }
    
    /**
     * Method used to run multiple tests on an image for human detection, atleast 66% of tests must 
     * be passed in order for human detection to be successful
     * @param url a String containing the location of an image
     * @throws IOException
     */
    public Boolean runTests(Image image) throws IOException{
    	
    	pop = new ImagePopupImplementation(); 
    	
    	try{
    		final long startTime = System.currentTimeMillis();
    		
    		//File input = new File(url);
	        BufferedImage img = image.getBufferedImage();
    		
	        Mat mat = generateMat(img);
	        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
	        
	        //Resize image	    	
	        mat = enlargeImage(img,mat);	    	
	    	Mat mat2 = greyScale(img,mat);	
	    	Mat mat3 = equalization(img,mat2);
	    	
	    	logger.log(Level.INFO, "Initialising Tests");
	    	
	    	//Test 1 - Resize image + normal 
    		successfulTests[0] = processImage(mat, img, data, image.getURL(), 'N');
    		
    		//Test 2 - Resize image + greyscale  
    		successfulTests[1] = processImage(mat2, img, data, image.getURL(), 'G');
    		    		   		
    		//Test 3 - Resize image + equalisation   		  
    		successfulTests[2] = processImage(mat3, img, data, image.getURL(), 'E'); 
    		    	    		
    		int count = 0;
    		for (int i=0; i<3; i++){
    			if (successfulTests[i] == true)
    				count++;
    		}
    		
    		final long estimatedTime = System.currentTimeMillis() - startTime;
	        final double seconds = (double) estimatedTime / 1000;
    		logger.log(Level.INFO, String.format("elapsed time: %4.2f seconds", seconds));
    		
    		
    		if (count >= 2)
    		{
	    		try {
	    			TimeUnit.SECONDS.sleep(5);
	    		} catch (InterruptedException e) {
	    		}
    			logger.log(Level.INFO, "Human Detected!");
	    		pop.ClosePopup();
	    		logger.log(Level.INFO, String.format("--------------------------------------------------------"));
	    		return true;
    		}
    		else
    			logger.log(Level.INFO, "No Human Detected!");
    			
    		pop.ClosePopup();
    		logger.log(Level.INFO, String.format("--------------------------------------------------------"));
    		return false;
    	}
    	catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
		return null;
    }
    
    /**
     * Method which processes an image for human detection
     * @param image image a BufferedImage object used to create a new mat with the same dimensions
     * @param mat a Mat object which is used to duplicate an image
     * @param data provides pixel writing capabilities - image.getRaster()
     * @throws IOException
     */
     boolean processImage(Mat mat, BufferedImage image, byte[] data, String url, char type) throws IOException{    	 
    	try{    	        	        
	        //Initialise and set hog descriptor to "people detector"
	        final HOGDescriptor hog = new HOGDescriptor();
	    	hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
	    	//Initialise variables to identify humans in the image / video 
	    	//by locating box points to form a rectangle around the detected object
	    	MatOfRect found = new MatOfRect();	    	
	    	MatOfDouble weight = new MatOfDouble();
	    	//Colour of rectangle to be drawn onto image
	    	final Scalar rectColor = new Scalar(0, 255, 0); 

	        boolean detected = false;
    		//Detects objects of different sizes in the input image. The detected objects are returned as a list of rectangles
    		hog.detectMultiScale(mat, found, weight, 0, new Size(8, 8), new Size(32, 32), 1.025, 2, false);    		
    		if (found.rows() > 0) {
	    		detected = true;
	    		final List<Rect> rectList = found.toList();
                for (final Rect rect : rectList) {
                    //Draw rectangle around found object
                    Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), rectColor, 5);                      
                }
    	    	generateImage(image, mat, data, url, type);
            }	    	
	        
	        //Release memory
	        found.release();
	        weight.release();
	        mat.release();
	        
	        if (detected)
	        	return true;
	        	
	        else
		        return false;
	     
    	 } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	     }
    	return false;
    }
    
    /**
     * Suppress default constructor for noninstantiability.
     */
    public PeopleDetectImplementation(FileHandler fh) {    	
        logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter);
    }
    
    public PeopleDetectImplementation() {    	
        
    }

	public void processImage(String url) throws IOException {
		
	}
}
