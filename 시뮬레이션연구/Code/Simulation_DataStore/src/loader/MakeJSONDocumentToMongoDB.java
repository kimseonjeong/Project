package loader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mongodb.BasicDBList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class MakeJSONDocumentToMongoDB {
	public MakeJSONDocumentToMongoDB(String[] args, String ip, int port) {
		// MongoDB insert
		MongoClient mongoClient = null;
		String[] d_input = {"Shapename", "thickness","Umach", "AOA", "RE", "IVISC", "rho_inf", "t_inf", "p_inf", "t_wall", "intensity", "f_func", "f_order", "liminter" };
		String[] d_output = {"Cl", "Cdt", "Cdp", "Cdf", "Cm"};
		
		try {
			//mongoClient = new MongoClient("localhost", 27017);
			mongoClient = new MongoClient(ip, port);
			System.out.println("success");
			
			// write permission
			WriteConcern w = new WriteConcern(1, 2000);
			mongoClient.setWriteConcern(w);

			// db connect
			DB db = mongoClient.getDB("simulation");

			// collection bring
			DBCollection coll = db.getCollection("kflow");

			// data insert in testDB1 table
			DBObject doc_all = new BasicDBObject();
			
			//input
			DBObject doc_input = new BasicDBObject();
			for(int i=0; i<14; i++)
				doc_input.put(d_input[i], args[i]);

			//output
			DBObject doc_output = new BasicDBObject();
			
			//aerodynamic
			DBObject doc_aerodynamic = new BasicDBObject();
			for(int i=0; i<5; i++)
				doc_aerodynamic.put(d_output[i], args[i+14]);
			doc_output.put("aerodynamic", doc_aerodynamic);
			
			//field
			DBObject doc_field = new BasicDBObject();
			for(int i=1; i<17; i++){
				String s = String.format("%03d", i);
				doc_field.put("flo" + s , args[i+18]);
			}
			doc_output.put("field", doc_field);
			
			//surface
			DBObject doc_surface = new BasicDBObject();
			for(int i=2; i<8; i++){
				String s = String.format("%03d", i);
				doc_surface.put("sur" + s , args[i+33]);
			}
			doc_output.put("surface", doc_surface);
			
			
			DBObject doc_predict = new BasicDBObject();
			doc_predict.put("predicted", "n");
			
			doc_all.put("simulator", "KFLOW");
			doc_all.put("input", doc_input);
			doc_all.put("output", doc_output);
			doc_all.put("predicted", "n");

			coll.insert(doc_all, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
