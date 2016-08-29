package com.codeferm.opencv;

import com.codeferm.opencv.DefualtImpl.ProcessImageResponse;

public interface ImageProcessing {
	ProcessImageResponse ProcessImage(ProcessImageRequest request);
}
