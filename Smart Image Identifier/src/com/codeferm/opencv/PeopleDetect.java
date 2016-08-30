package com.codeferm.opencv;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.opencv.core.Mat;

public interface PeopleDetect {
	Mat greyScale(BufferedImage image, Mat mat);
	Mat equalization(BufferedImage image, Mat mat);
	Mat enlargeImage(BufferedImage image, Mat mat);
	Mat generateMat(BufferedImage image);
	void processVideo(String url);
	void runTests(String url) throws IOException;
	
}
