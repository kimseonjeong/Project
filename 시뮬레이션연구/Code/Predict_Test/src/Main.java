import java.io.*;

public class Main {

	public static void main(String[] args) {
		try {
			
			//file
			File f = new File("C:/Coding/Simulation_DataSearch/Cl.rds");
			
			//dirpath string
			String dirPath;
			
			//file exist
			//interaction3.r execute
			//file no exist
			//interaction1.r execute
			
			if(f.exists())
				dirPath = "./interaction3.R";
			else
				dirPath = "./interaction1.R";
			
			//stringbuffer cmd execute
			StringBuffer cmd = new StringBuffer();

			//Rscript execute
			cmd.append("Rscript");
			cmd.append(" ");
			
			//file path
			cmd.append(dirPath);
			
			//cmd execute
			Process proc = Runtime.getRuntime().exec(cmd.toString());
			proc.waitFor();
			
			//rscript input
			if (proc.exitValue() != 0) {
				
				//error exist
				BufferedReader err = new BufferedReader(new InputStreamReader(
						proc.getErrorStream()));
				
				while (err.ready())
					System.out.println(err.readLine());
				
				//error close
				err.close();
				
			} else {
				
				//output exist
				BufferedReader out = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				
				
				while (out.ready())
					System.out.println(out.readLine());
				
				//close
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
			
			proc.destroy();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
