package com.codeferm.opencv;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.opencv.core.Mat;

public interface PeopleDetect {
	Mat greyScale(BufferedImage image, Mat mat);
	Mat equalization(BufferedImage image, Mat mat);
	Mat enlargeImage(BufferedImage image, Mat mat);
	Mat generateMat(BufferedImage image);
	void generateImage(BufferedImage image, Mat mat, byte[] data);
	void processImage(String url) throws IOException;
	void processVideo(String url);
}
