package com.kisti.edison.ddas.Loader;

import java.io.*;
import java.util.StringTokenizer;

/** This class extracts necessary fields for saving simulation data.
 *  
 *  If user write the path in command line, then this class read kflow.inp 
 *  , Aerodynamic.dat, and NAC.sol file.
 *  
 *  Shapename, Umach, AOA, RE, and IVISC are from kflow.inp
 *  
 *  NAC.sol file is also from kflow.inp
 *  
 *  rho_inf, t_inf, p_inf, t_wall, intensity, f_func, f_order
 *  and limiter are from NAC.sol
 *  
 *  Cl, Cdt, Cdp, Cdf, and Cm are from Aerodynamic.dat
 *  
 * @author DKELab in Sookmyung Women's University
 *
 */

public class Extractor {
	
	//Necessary fields for saving the simulation data
	public String Shapename;
	public String thickness;
	public String Umach;
	public String AOA;
	public String RE;
	public String IVISC;
	public String rho_inf;
	public String t_inf;
	public String p_inf;
	public String t_wall;
	public String intensity;
	public String f_func;
	public String f_order;
	public String liminter;
	public String Cl;
	public String Cdt;
	public String Cdp;
	public String Cdf;
	public String Cm;
	public String solfile;
	
	public Extractor(String[] args) throws IOException {

		BufferedReader in = null;
		
		for (int i = 0; i < 2; i++) {
			
			//Read kflow.inp and Aerodynamic.dat from the path
			if(i == 0)
				in = new BufferedReader(new FileReader(args[0] + "/kflow.inp"));
			else if (i == 1)
				in = new BufferedReader(new FileReader(args[0] + "/Aerodynamic.dat"));
			String line = "";
			int rownum = 0;
			int colnum = 0;
			String line2 = "";
			int rownum2 = 0;
			int colnum2 = 0;

			while ((line = in.readLine()) != null) {
				rownum++;
				colnum = 0;
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					String str = st.nextToken();
					if (i == 0) {
						if (rownum == 1 && colnum == 0) {
							//Extract the shapename from kflow.inp
							Shapename = str;
							//Extract the thickness from the Shapename
							thickness = Shapename.substring(Shapename.length()-2, Shapename.length());
						}
						if (rownum == 4) {
							//Extract the Umach from kflow.inp
							if (colnum == 0) {
								if(str.charAt(0) == '-')
									Umach = str.substring(1);
								else
									Umach = str;
								
								Umach = Umach.replace("d", "E+");
							} else if (colnum == 1) {
								//Extract the AOA from kflow.inp
								AOA = str;
								AOA = AOA.replace("d", "E+");
							} else if (colnum == 3) {
								//Extract the RE from kflow.inp
								RE = str;
								RE = RE.replace("D", "E");
								RE = RE.replace("d", "E+");
							} else if (colnum == 4) {
								//Extract the IVISC from kflow.inp
								IVISC = str;
							}
						}
						if (rownum == 9 && colnum == 0) {
							//Read *.sol file from kflow.inp
							solfile = args[0]+ "/" + str;
							BufferedReader in2 = new BufferedReader(
									new FileReader(solfile));
							while ((line2 = in2.readLine()) != null) {
								rownum2++;
								colnum2 = 0;
								StringTokenizer st2 = new StringTokenizer(line2);
								while (st2.hasMoreTokens()) {
									String str2 = st2.nextToken();
									if (rownum2 == 6) {
										if (colnum2 == 0) {
											//Extract the rho_inf from NAC.sol
											rho_inf = str2;
										} else if (colnum2 == 1) {
											//Extract the t_inf from NAC.sol
											t_inf = str2;
										} else if (colnum2 == 2) {
											//Extract the p_inf from NAC.sol
											p_inf = str2;
										} else if (colnum2 == 3) {
											//Extract the t_wall from NAC.sol
											t_wall = str2;
										}
									}
									if (rownum2 == 8) {
										if (colnum2 == 1) {
											//Extract the intensity from NAC.sol
											intensity = str2;
										}
									}
									if (rownum2 == 13) {
										if (colnum2 == 0) {
											//Extract the f_func from NAC.sol
											f_func = str2;
										} else if (colnum2 == 1) {
											//Extract the f_order from NAC.sol
											f_order = str2;
										} else if (colnum2 == 3) {
											//Extract the limiter from NAC.sol
											liminter = str2;
										}
									}
									colnum2++;
								}
							}
						}
					} else if (i == 1) {
						if (rownum == 2 && colnum == 2) {
							//Extract the Cl from Aerodynamic.dat
							Cl = str;
						} else if (rownum == 3) {
							if (colnum == 0) {
								//Extract the Cdt from Aerodynamic.dat
								Cdt = str;
							} else if (colnum == 1) {
								//Extract the Cdp from Aerodynamic.dat
								Cdp = str;
							} else if (colnum == 2) {
								//Extract the Cdf from Aerodynamic.dat
								Cdf = str;
							}

						} else if (rownum == 4) {
							//Extract the Cm from Aerodynamic.dat
							Cm = str;
						}
					}
					colnum++;
				}

			}
			in.close();
		}
	}
}
