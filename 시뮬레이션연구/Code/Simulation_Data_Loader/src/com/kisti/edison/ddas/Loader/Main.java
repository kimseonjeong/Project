package com.kisti.edison.ddas.Loader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.io.File;

/** This class makes Extractor object and MongoDB Connection object for saving simulation data.
 *
 *  Extractor object extracts necessary fields from the kflow.inp and Aerodynamic.dat
 *  and gives them to MongoDb Connection object for saving the simulation data.
 *
 *  MongoDB Connection object connects to mongoDB.
 *
 *  Default ip is localhost and port number is 27017
 *
 *  If user want to use another ip and port number,
 *  then write 'java -jar loader.jar /path ip port' in command line.
 *
 *  @author DKELab in Sookmyung Women's University
 *  
 */

public class Main {
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, 
	IllegalAccessException{
		//Make Extractor object
		Extractor extractor = new Extractor(args);
		String[] input = new String[41];
		
		int i=0;
		
		//Get the necessary fields for saving simulation data from Extractor.java
	    Class<?> objClass = extractor.getClass();
	    Field[] fields = objClass.getFields();
	    for(Field field : fields) {
	        String name = field.getName();
	        if(i<19){
	        	input[i] = (String) field.get(objClass.cast(extractor));
	        	i++;
	        }
	    }
		
		File f = null;
		File[] paths;
		
		try{
			f = new File(args[0]);
			paths = f.listFiles();
			
			//flo*. file path save
			for(File path:paths){
				String filename = path.getName();
				if (filename.startsWith("flo")){
					input[i] = path.toString();
					i++;
				}
			}
			
			//sur*. file path save
			for(File path:paths){
				String filename = path.getName();
				if (filename.startsWith("sur")){
					input[i] = path.toString();
					i++;
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Default ip is localhost
		String ip = "localhost";
		
		//Default port number is 27017
		int port = 27017;
		
		//If user use another ip and port number 
		if(args.length == 3){
			ip = args[1];
			port = Integer.valueOf(args[2]);
		}
		else if(args.length == 2){
			ip = args[1];
		}
		
		//Make mongoDB connection object
		MakeJSONDocumentToMongoDB JSONdoc = new MakeJSONDocumentToMongoDB(input, ip, port);
	}

}
