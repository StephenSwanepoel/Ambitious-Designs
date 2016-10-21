import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.codeferm.opencv.PeopleDetection;


public class RequestThreadPool {
	
	public RequestThreadPool()
	{
		ExecutorService executor = Executors.newCachedThreadPool();
	}
	
	public RequestThreadPool(PeopleDetection peopleDetect)
	{
		
	}
}
