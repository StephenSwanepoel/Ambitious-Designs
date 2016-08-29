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
//Libraries required for logging system events
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
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

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.LawOfDemeter", "PMD.AvoidLiteralsInIfCondition",
        "PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidUsingNativeCode", "PMD.AvoidFinalLocalVariable",
        "PMD.CommentSize", "PMD.AvoidPrintStackTrace", "PMD.UseProperClassLoader", "PMD.AvoidPrefixingMethodParameters",
        "PMD.DataflowAnomalyAnalysis" })
public class PeopleDetectImplementation implements com.codeferm.opencv.PeopleDetect {
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
        //System.loadLibrary("E:\\Software\\OpenCV\\openCV3.1\\opencv\\build\\java\\x64\\openh264-1.4.0-win64msvc.dll");
        System.load("C:\\Users\\Johan\\workspace\\Smart Image Identifier\\lib\\openh264-1.4.0-win64msvc.dll");
    }
    
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
    	//Imgcodecs.imwrite("./output/test3.jpg", mat1);
        
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
    	Imgproc.resize(mat, mat2, new Size(480,640));
    	//Imgproc.resize(resizeimage, size);
    	
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
    public void generateImage(BufferedImage image, Mat mat, byte[] data){
    	
    	//Create an image file to store the mat object into (Same dimensions as original image)
    	BufferedImage output = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_BYTE_GRAY);
    	output.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
    	//Write the contents of the mat object to the output variable
    	Imgcodecs.imwrite("./output/test.jpg", mat);
    }
    
    /**
     * Method which processes an image for human detection
     * @param url a String containing the location of an image
     * @throws IOException
     */
    public void processImage(String url) throws IOException{
    	
    	try{    
	    	File input = new File(url);
	        BufferedImage image = ImageIO.read(input);
	        Mat mat = generateMat(image);
	        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

	        //mat = greyScale(image, mat);
	        //mat = equalization(image,mat);
	        	        
	        //Initialise and set hog descriptor to "people detector"
	        final HOGDescriptor hog = new HOGDescriptor();
	        //hog.load("C:\\Users\\Stephen\\Desktop\\Smart Image Identifier\\src\\xml\\haarcascade_fullbody.xml");
	       // hog.setSVMDetector(_svmdetector);
	    	hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
	    	//Initialise variables to identify humans in the image / video 
	    	//by locating box points to form a rectangle around the detected object
	    	MatOfRect found = new MatOfRect();
	    	//Resize image	    	
	    	mat = enlargeImage(image,mat);
	    	MatOfDouble weight = new MatOfDouble();
	    	//Colour of rectangle to be drawn onto image
	    	final Scalar rectColor = new Scalar(0, 255, 0); 
	        final long startTime = System.currentTimeMillis();
	        Boolean detected = false;
    		//Detects objects of different sizes in the input image. The detected objects are returned as a list of rectangles
    		hog.detectMultiScale(mat, found, weight, 0, new Size(8, 8), new Size(32, 32), 1.025, 2, false);
	    	if (found.rows() > 0) {
	    		detected = true;
	    		final List<Rect> rectList = found.toList();
                for (final Rect rect : rectList) {
                    //Draw rectangle around found object
                    Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), rectColor, 5);  
                    
                }
    	    	generateImage(image, mat, data);
            }	    	
	    	
	    	final long estimatedTime = System.currentTimeMillis() - startTime;
	        final double seconds = (double) estimatedTime / 1000;
	        logger.log(Level.INFO, String.format("elapsed time: %4.2f seconds", seconds));
	        
	        //Release memory
	        found.release();
	        weight.release();
	        mat.release();
	        
	        if (detected)
	        	logger.log(Level.INFO, "Human Detected!");
	        else 
	        	logger.log(Level.INFO, "No Human Detected!");
	     
    	} catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	     } 	
    }
    
    /**
     * Method which processes a video for human detection
     * @param url a String containing the location of an image
     */
    public void processVideo(String url){
    	
    	final String outputFile = "./output/test.avi";    	    	    	
    	final VideoCapture videoCapture = new VideoCapture(url);
        final Size frameSize = new Size((int) videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH),
                (int) videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT));    
        final FourCC fourCC = new FourCC("X264");
        final VideoWriter videoWriter = new VideoWriter(outputFile, fourCC.toInt(),
                videoCapture.get(Videoio.CAP_PROP_FPS), frameSize, true);
        final Mat mat = new Mat();
        final HOGDescriptor hog = new HOGDescriptor();
        final MatOfFloat descriptors = HOGDescriptor.getDefaultPeopleDetector();
        hog.setSVMDetector(descriptors);
        final MatOfRect foundLocations = new MatOfRect();
        final MatOfDouble foundWeights = new MatOfDouble();
        final Size winStride = new Size(8, 8);
        final Size padding = new Size(32, 32);
        final Scalar rectColor = new Scalar(0, 255, 0);
        final long startTime = System.currentTimeMillis();
                
        while (videoCapture.read(mat)) {
            hog.detectMultiScale(mat, foundLocations, foundWeights, 0.0, winStride, padding, 1.025, 2.0, false);
            if (foundLocations.rows() > 0) {
                final List<Rect> rectList = foundLocations.toList();
                for (final Rect rect : rectList) {
                    //Draw rectangle around found object
                    Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), rectColor, 5);   
                }
            }

            videoWriter.write(mat);
        }
        final long estimatedTime = System.currentTimeMillis() - startTime;
        final double seconds = (double) estimatedTime / 1000;
        logger.log(Level.INFO, String.format("elapsed time: %4.2f seconds", seconds));
        
        //Release native memory
        videoCapture.release();
        videoWriter.release();
        descriptors.release();
        foundLocations.release();
        foundWeights.release();
        mat.release();
    }

    /**
     * Suppress default constructor for noninstantiability.
     */
    public PeopleDetectImplementation() {
   
    }
    
    /*public static void main(final String... args) throws IOException {
    	
        String url = null;        
        // Check how many arguments were passed in
        if (args.length == 0) {
            // If no arguments were passed then default to local file
        	url = "./resources/walking.avi";
        } else {
        	url = args[0];
        }
                       
        // Custom logging properties via class loader
        try {
            LogManager.getLogManager()
                    .readConfiguration(PeopleDetect.class.getClassLoader().getResourceAsStream("logging.properties"));
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, String.format("OpenCV %s", Core.VERSION));
        logger.log(Level.INFO, String.format("Input file: %s", url));
        
        if (url.contains(".jpg") == true)
            processImage(url);
        else
        	processVideo(url);
    }*/
}
