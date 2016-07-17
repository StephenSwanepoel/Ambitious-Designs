/**
 * Ambitious Designs - Smart Image Identifier
 * 
 * Group members
 * 
 * Stephen Swanepoel
 * Dian Veldsman
 * Killian Kieck
 */
package com.codeferm.opencv;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.nio.*;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc; //Library to draw shapes etc.
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

/**
 * Histogram of Oriented Gradients object detector.
 */

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.LawOfDemeter", "PMD.AvoidLiteralsInIfCondition",
        "PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidUsingNativeCode", "PMD.AvoidFinalLocalVariable",
        "PMD.CommentSize", "PMD.AvoidPrintStackTrace", "PMD.UseProperClassLoader", "PMD.AvoidPrefixingMethodParameters",
        "PMD.DataflowAnomalyAnalysis" })
final class PeopleDetect {
    /**
     * Logger.
     */
    // Logger is not a constant
    @SuppressWarnings({ "checkstyle:constantname", "PMD.VariableNamingConventions" })
    private static final Logger logger = Logger.getLogger(PeopleDetect.class.getName());
    /* Load the OpenCV system library */
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.load("E:\\Software\\OpenCV\\openCV3.1\\opencv\\build\\java\\x64\\openh264-1.4.0-win64msvc.dll");
    }
    
    public static void processImage(String url) throws IOException{
    	
    	try{    		
	    	File input = new File(url);
	        BufferedImage image = ImageIO.read(input);    
	        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	        mat.put(0, 0, data);
	        
	        //Grey scale image
	        Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
	        Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);
	
	        byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
	        mat1.get(0, 0, data1);
	        BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
	        image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);
	  
	        final HOGDescriptor hog = new HOGDescriptor();
	    	hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
	    
	    	MatOfRect found = new MatOfRect();
	    	MatOfDouble weight = new MatOfDouble();
	    	final Scalar rectColor = new Scalar(0, 255, 0);
	        final Scalar fontColor = new Scalar(255, 255, 255);
	        final Point rectPoint1 = new Point();
	        final Point rectPoint2 = new Point();
	        final Point fontPoint = new Point();
	        int framesWithPeople = 0;
	        final long startTime = System.currentTimeMillis();
	        
	    	hog.detectMultiScale(mat1, found, weight, 0, new Size(8, 8), new Size(32, 32), 1.01, 2, false);
	    	if (found.rows() > 0) {
                framesWithPeople++;
                final List<Double> weightList = weight.toList();
                final List<Rect> rectList = found.toList();
                int index = 0;
                for (final Rect rect : rectList) {
                    rectPoint1.x = rect.x;
                    rectPoint1.y = rect.y;
                    rectPoint2.x = rect.x + rect.width;
                    rectPoint2.y = rect.y + rect.height;
                    //Draw rectangle around fond object
                    Imgproc.rectangle(mat1, rectPoint1, rectPoint2, rectColor, 2);
                    fontPoint.x = rect.x;
                    //Illustration
                    fontPoint.y = rect.y - 4;
                    //Print weight
                    //Illustration
                    Imgproc.putText(mat1, String.format("%1.2f", weightList.get(index)), fontPoint,
                            Core.FONT_HERSHEY_PLAIN, 1.5, fontColor, 2, Core.LINE_AA, false);
                    index++;
                }
            }	 
	    	
	    	BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	    	output.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);
	    	  	
	    	Imgcodecs.imwrite("./output/test.jpg", mat1);
	    	
	    	final long estimatedTime = System.currentTimeMillis() - startTime;
	        final double seconds = (double) estimatedTime / 1000;
	        logger.log(Level.INFO, String.format("%d frames with people", framesWithPeople));
	        logger.log(Level.INFO, String.format("elapsed time: %4.2f seconds", seconds, seconds));
	        
	        found.release();
	        weight.release();
	        mat.release();
	        mat1.release();	        
	     
    	} catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	     } 	
    }
    
    /**
     * process video
     */
    public static void processVideo(/*String url*/){
    	
    	String url = "./resources/walking.mp4";
    	final String outputFile = "./output/test.avi";    	    	
    	logger.log(Level.INFO, String.format("Output file: %s", outputFile));
    	
    	final VideoCapture videoCapture = new VideoCapture(url);
        final Size frameSize = new Size((int) videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH),
                (int) videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        
        logger.log(Level.INFO, String.format("Resolution: %s", frameSize));
        
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
        final Point rectPoint1 = new Point();
        final Point rectPoint2 = new Point();
        final Point fontPoint = new Point();
        int frames = 0;
        int framesWithPeople = 0;
        final Scalar rectColor = new Scalar(0, 255, 0);
        final Scalar fontColor = new Scalar(255, 255, 255);
        final long startTime = System.currentTimeMillis();
                
        while (videoCapture.read(mat)) {
            hog.detectMultiScale(mat, foundLocations, foundWeights, 0.0, winStride, padding, 1.05, 2.0, false);
            if (foundLocations.rows() > 0) {
                framesWithPeople++;
                final List<Double> weightList = foundWeights.toList();
                final List<Rect> rectList = foundLocations.toList();
                int index = 0;
                for (final Rect rect : rectList) {
                    rectPoint1.x = rect.x;
                    rectPoint1.y = rect.y;
                    rectPoint2.x = rect.x + rect.width;
                    rectPoint2.y = rect.y + rect.height;
                    //Draw rectangle around fond object
                    Imgproc.rectangle(mat, rectPoint1, rectPoint2, rectColor, 2);
                    fontPoint.x = rect.x;
                    //Illustration
                    fontPoint.y = rect.y - 4;
                    //Print weight
                    //Illustration
                    Imgproc.putText(mat, String.format("%1.2f", weightList.get(index)), fontPoint,
                            Core.FONT_HERSHEY_PLAIN, 1.5, fontColor, 2, Core.LINE_AA, false);
                    index++;
                }
            }

            videoWriter.write(mat);
            frames++;
        }
        final long estimatedTime = System.currentTimeMillis() - startTime;
        final double seconds = (double) estimatedTime / 1000;
        logger.log(Level.INFO, String.format("%d frames, %d frames with people", frames, framesWithPeople));
        logger.log(Level.INFO, String.format("%4.1f FPS, elapsed time: %4.2f seconds", frames / seconds, seconds));
        
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
    private PeopleDetect() {
        throw new AssertionError();
    }
    
    public static void main(final String... args) throws IOException {
    	
        String url = null;        
        // Check how many arguments were passed in
        if (args.length == 0) {
            // If no arguments were passed then default to local file
        	url = "./resources/trail2.jpg";
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
        	processVideo();
    }
}
