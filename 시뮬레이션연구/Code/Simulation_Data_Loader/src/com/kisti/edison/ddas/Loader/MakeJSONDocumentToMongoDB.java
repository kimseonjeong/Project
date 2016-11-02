package com.kisti.edison.ddas.Loader;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

/** This class connects to MongoDB and save simulation data.
 *  
 *  Make a mongoClient and Connect to Simulation Database and get kflow collection.
 *  
 *  Also make all JSON documents for inserting to the collection.
 *  
 *  After then, Insert all JSON documents to the kflow collection.
 * 
 * @author DKELab in Sookmyung Women's University
 *
 */

public class MakeJSONDocumentToMongoDB {
	public MakeJSONDocumentToMongoDB(String[] args, String ip, int port) {
		// Variable initialization for MongoDB Connection
		MongoClient mongoClient = null;
		String[] d_input = {"Shapename", "thickness","Umach", "AOA", "RE", "IVISC", "rho_inf", "t_inf", "p_inf", "t_wall", "intensity", "f_func", "f_order", "liminter" };
		String[] d_output = {"Cl", "Cdt", "Cdp", "Cdf", "Cm"};
		
		try {
			//Connects to MongoDB whose ip and port number are from command line.
			//Default ip is localhost and Default port number is 27017
			mongoClient = new MongoClient(ip, port);
			System.out.println("success");
			
			//Write permission
			WriteConcern w = new WriteConcern(1, 2000);
			mongoClient.setWriteConcern(w);

			//Simulation DB connection
			DB db = mongoClient.getDB("simulation");

			//Get the kflow collection
			DBCollection coll = db.getCollection("kflow");

			//Make JSON document for attaching the all data
			DBObject doc_all = new BasicDBObject();
			
			//Make JSON document for attaching the input data
			DBObject doc_input = new BasicDBObject();
			for(int i=0; i<14; i++)
				doc_input.put(d_input[i], args[i]);

			//Make JSON document for attaching the output data
			DBObject doc_output = new BasicDBObject();
			
			//Make JSON document for attaching the Aerodynamic data
			DBObject doc_aerodynamic = new BasicDBObject();
			
			//Attach the Aerodynamic data to the JSON document
			for(int i=0; i<5; i++)
				doc_aerodynamic.put(d_output[i], args[i+14]);
			doc_output.put("aerodynamic", doc_aerodynamic);
			
			//Attach the flo*. file path to the JSON document
			DBObject doc_field = new BasicDBObject();
			for(int i=1; i<17; i++){
				String s = String.format("%03d", i);
				doc_field.put("flo" + s , args[i+18]);
			}
			doc_output.put("field", doc_field);
			
			//Attach the sur*. file path to the JSON document
			DBObject doc_surface = new BasicDBObject();
			for(int i=2; i<8; i++){
				String s = String.format("%03d", i);
				doc_surface.put("sur" + s , args[i+33]);
			}
			doc_output.put("surface", doc_surface);
			
			//Attach the all data to the JSON document
			doc_all.put("simulator", "KFLOW");
			doc_all.put("input", doc_input);
			doc_all.put("output", doc_output);
			doc_all.put("predicted", "N");
			
			//Insert JSON document to the collection
			coll.insert(doc_all, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
