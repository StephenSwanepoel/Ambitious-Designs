package com.codeferm.opencv;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import java.awt.image.BufferedImage;

public class Database {
	
	private static MongoClient mongoClient;
	private static DB db;
	
	 /**
     * Method which connects to the database and pulls records 
     */
	public static Boolean main(String[] args) {
		try {
        //=================== CONNECT TO DB ========================
		mongoClient = new MongoClient("localhost", 27017);
		db =mongoClient.getDB("COS301");
		
		System.out.println("Connected to database successfully.");
		readDatabase();
		
		return true;
		
		} catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
		
		return false;
	}
	
	 /**
     * Method which decodes a byte array
     * @param encodedBytes is a byte array which contains the data required to produce an image
     * @return returns decoded base64 string
     */
	public static byte[] getDecoded(byte[] encodedBytes){
		return Base64.getDecoder().decode(encodedBytes);
	}
	
	 /**
     * Method which encodes a byte array
     * @param decodedBytes is a byte array which contains the data required to produce an image
     * @return returns encoded base64 string
     */
	public static byte[] getEncoded(byte[] decodedBytes){
		return Base64.getEncoder().encode(decodedBytes);
	}
		
	 /**
     * Method which reads .bson objects from the database, storing the necessary details 
     * and writing the image to the folder for processing
     */
	public static void readDatabase() throws IOException
    {
        BasicDBObject query = new BasicDBObject();
    	BasicDBObject field = new BasicDBObject();
        	field.put("attachments", 0);
    	BasicDBObject attachments = new BasicDBObject();
    	attachments.put("attachments.fields",java.util.regex.Pattern.compile("")); 
        
        DBCursor items = db.getCollection("Willburg").find(query,field);
        DBCursor items2 = db.getCollection("Willburg").find(query,attachments);    
                
        for(int i=0; i<50; i++)
        {
        	BasicDBObject obj = (BasicDBObject) items.next();
        	BasicDBObject obj2 = (BasicDBObject) items2.next();
            String cameraID = obj.get("camera").toString();
            String date = obj.get("date").toString();
            String from = obj.get("from").toString();
            String ID = obj.get("_id").toString();
            String encoding = obj2.get("attachments").toString().substring(18, obj2.get("attachments").toString().length()-4);
            
            BufferedImage img = getImage(encoding.getBytes());
            //Write the image to a file
            File outputfile = new File("./resources/database/"+ ID +".jpg");
            ImageIO.write(img, "jpg", outputfile);
        }
    }	
	
	 /**
     * Method which generates an image from a base64 string
     * @param base64String is a byte array which contains the data required to produce an image
     * @return returns buffered image
     */
	public static BufferedImage getImage(byte[] base64String) throws IOException{		
		try{
			BufferedImage image = null;
			byte[] imageByte = getDecoded(base64String);			
			
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
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
