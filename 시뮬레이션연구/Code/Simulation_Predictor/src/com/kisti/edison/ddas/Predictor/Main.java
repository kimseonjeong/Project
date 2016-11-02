package com.kisti.edison.ddas.Predictor;

import java.io.*;

/** This class is for predicting the simulation results.
 * 
 *  If user has already Cl.rds file then, executes interaction3.R
 *  
 *  Otherwise, executes interaction1.R
 * 
 * @author DKELab in Sookmyung Women's University
 *
 */

public class Main {

	public static void main(String[] args) {
		try {
			
			//Find Cl modeling file
			File f = new File("C:/Coding/Simulation_DataSearch/Cl.rds");
			
			//Make a directory path variable
			String dirPath;
			
			//If Cl modeling file exists,
			//then executes interaction3.R
			//Otherwise,
			//executes interaction1.R
			
			if(f.exists())
				dirPath = "./interaction3.R";
			else
				dirPath = "./interaction1.R";
			
			//Make a variable for executing command line
			StringBuffer cmd = new StringBuffer();

			//Append to command line for executing R-script file
			cmd.append("Rscript");
			cmd.append(" ");
			
			//Append to command line for file path
			cmd.append(dirPath);
			
			//Make a process object to execute command line
			//And, execute command line
			
			Process proc = Runtime.getRuntime().exec(cmd.toString());
			proc.waitFor();
			
			//If r-script make an error, print the cause.
			
			if (proc.exitValue() != 0) {
				
				//Print the cause of an error
				BufferedReader err = new BufferedReader(new InputStreamReader(
						proc.getErrorStream()));
				
				while (err.ready())
					System.out.println(err.readLine());
				
				//Close the process printing the error
				err.close();
				
			} else {
				
				//If r-script make a result, print the result of r-script
				BufferedReader out = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				
				
				while (out.ready())
					System.out.println(out.readLine());
				
				//Close the process printing the result
				out.close();

				/*try {
					FileReader file = new FileReader("C://Coding//Simulation_DataSearch//testtesttest.txt");
					BufferedReader in = new BufferedReader(file);
					String s;

					while ((s = in.readLine()) != null) {
						System.out.println(s);
					}
					in.close();
				} catch (IOException e) {
					System.err.println(e);
					System.exit(1);
				}*/
				
			}
			
			//Destroy the process
			proc.destroy();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
