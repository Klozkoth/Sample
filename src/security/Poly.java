/**
 * 
 */
package security;

import java.io.*;
import java.util.*;

/**
 * @author Adam Leedy
 *
 */
public class Poly {
	
	public static int degFx = 0;
	public static int degGx = 0;	
	public static ArrayList<Integer> fx = new ArrayList<Integer>();
	public static ArrayList<Integer> gx = new ArrayList<Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loadFile();
		File output = new File("output_poly.txt");
		output.delete();
		String temp = leadZero(addPoly());
		writeFile(output, temp);
		temp = leadZero(subPoly());
		writeFile(output, temp);
		temp = leadZero(multPoly());
		writeFile(output, temp);
	}
	
	/**
	 * Reads in input.txt (until manual submission is implemented) and stores the values for
	 * polynomial arithmetic. Values are stored to the class variables, hence nothing being returned.
	 */
	protected static void loadFile() {
		File file = new File("input_poly.txt");
		String space = " ";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			//First polynomial
			degFx = Integer.valueOf(reader.readLine());
			//Possibly find way to clean this up.
			String[] temp = reader.readLine().split(space);
			for(String str : temp) {
				fx.add(Integer.valueOf(str));
			}
			
			//Second polynomial
			degGx = Integer.valueOf(reader.readLine());
			//Possibly find way to clean this up.
			temp = reader.readLine().split(space);
			for(String str : temp) {
				gx.add(Integer.valueOf(str));
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("General IO Error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the param poly to the specified output file.
	 * @param output - The file to write to.
	 * @param poly - The String Polynomial.
	 */
	protected static void writeFile(File output, String poly) {
		try(PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(output, true)))) {
			write.println(poly);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Removes and leading 0's and spaces in the polynomial. If the entire polynomial is 0,
	 * then the last 0 will be returned.
	 * @param poly - The String polynomial from addition, subtraction, or multiplication.
	 * @return either an unaltered String poly, or a String poly with the leading 0's removed.
	 */
	protected static String leadZero(String poly) {
		char[] temp = poly.toCharArray();
		for(int i = 0; i < temp.length-2; i++) {
			if(temp[i] != '0' && temp[i] != ' ') {
				break;
			} else {
				poly = poly.substring(1);
			}
		}
		return poly;
	}
	
	/**
	 * Performs polynomial addition on the two polynomial ArrayLists stored from loadFile().
	 * @return String coef, which represents the sum of the coefficients. 
	 * 		Each coefficient is separated by a space.
	 */
	protected static String addPoly() {
		String coef = "";
		if(degFx == degGx) {
			for(int i = 0; i < fx.size(); i++) {
				coef += (fx.get(i) + gx.get(i));
				coef += " ";
			}
			return coef;
		} else {
			int diff = (degFx - degGx);
			int cnt = 0;
			if(diff < 0) {
				for(int i = 0; i < Math.abs(diff); i++) {
					coef += gx.get(i);
					coef += " ";
				}
				for(int i = Math.abs(diff); i < gx.size(); i++) {
					coef += (fx.get(cnt) + gx.get(i));
					coef += " ";
					cnt++;
				}
				return coef;
			} else {
				for(int i = 0; i < diff; i++) {
					coef += fx.get(i);
					coef += " ";
				}
				for(int i = diff; i < fx.size(); i++) {
					coef += (fx.get(i) + gx.get(cnt));
					coef += " ";
					cnt++;
				}
				return coef;
			}
		}
	}
	
	/**
	 * Performs polynomial subtraction on the two polynomial ArrayLists stored from loadFile().
	 * @return String coef, which represents the sum of the coefficients. 
	 * 		Each coefficient is separated by a space.
	 */
	protected static String subPoly() {
		String coef = "";
		if(degFx == degGx) {
			for(int i = 0; i < fx.size(); i++) {
				coef += (fx.get(i) - gx.get(i));
				coef += " ";
			}
			return coef;
		} else {
			int diff = (degFx - degGx);
			int cnt = 0;
			if(diff < 0) {
				for(int i = 0; i < Math.abs(diff); i++) {
					coef += gx.get(i);
					coef += " ";
				}
				for(int i = Math.abs(diff); i < gx.size(); i++) {
					coef += (fx.get(cnt) - gx.get(i));
					coef += " ";
					cnt++;
				}
				return coef;
			} else {
				for(int i = 0; i < diff; i++) {
					coef += fx.get(i);
					coef += " ";
				}
				for(int i = diff; i < fx.size(); i++) {
					coef += (fx.get(i) - gx.get(cnt));
					coef += " ";
					cnt++;
				}
				return coef;
			}
		}
	}
	
	/**
	 * Performs polynomial multiplication on the two polynomial ArrayLists stored from loadFile().
	 * @return String coef, which represents the product of the coefficients. 
	 * 		Each coefficient is separated by a space.
	 */
	protected static String multPoly() {
		String coef = "";
		int[] co = new int[degFx + degGx + 1];
		for(int i = 0; i < (degFx + degGx); i++) {
			co[i] = 0;
		}
		for(int i = 0; i < fx.size(); i++) {
			for(int g = 0; g < gx.size(); g++) {
				co[i + g] += (fx.get(i) * gx.get(g));
			}
		}
		for(int num : co) {
			coef += num;
			coef += " ";
		}
		return coef;
	}
}