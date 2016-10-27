package loader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.io.File;

public class Main {
		
	public static void main(String[] args) throws IOException, IllegalArgumentException, 
	IllegalAccessException{
		Extractor extractor = new Extractor(args);
		String[] input = new String[41];
		
		int i=0;
		
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
			
			//flo*.file
			for(File path:paths){
				String filename = path.getName();
				if (filename.startsWith("flo")){
					input[i] = path.toString();
					i++;
				}
			}
			
			//sur*.file
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
		
		
		String ip = "localhost";
		int port = 27017;
		
		if(args.length == 3){
			ip = args[1];
			port = Integer.valueOf(args[2]);
		}
		else if(args.length == 2){
			ip = args[1];
		}
		MakeJSONDocumentToMongoDB JSONdoc = new MakeJSONDocumentToMongoDB(input, ip, port);
	}

}
