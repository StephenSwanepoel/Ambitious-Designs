import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
//import com.mongodb.Block;
import com.mongodb.client.FindIterable;

//import static com.mongodb.client.model.Filters.*;
//import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

import java.awt.image.BufferedImage;

public class Database {
	
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		FindIterable<Document> iterable = db.getCollection("restaurants").find();
		System.out.println("ASD");
	}	
	
	public byte[] getDecoded(byte[] encodedBytes){
		return Base64.getDecoder().decode(encodedBytes);
	}
	
	public byte[] getEncoded(byte[] decodedBytes){
		return Base64.getEncoder().encode(decodedBytes);
	}
		
	public void readDatabase(MongoDatabase db)
    {
        /*try
        {
            byte[] byteArray = new byte[102400];
            FileInputStream fis = new FileInputStream(file);
            byte[] base64String;
            int bytesRead = 0;
            while ((bytesRead = fis.read(byteArray)) != -1)
            {
            	byte[] encodedBytes = Base64.getEncoder().encode(byteArray);
                base64String = Base64.getDecoder().decode(encodedBytes);
                buildImage(file, base64String, directory);
            }
            fis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }	
	
	public BufferedImage getImage(File file, byte[] base64String) throws IOException{
		
		try{
			BufferedImage image = null;
			ByteArrayInputStream bis = new ByteArrayInputStream(base64String);
			image = ImageIO.read(bis);
			bis.close();
			
			return image;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;		
	}
}
