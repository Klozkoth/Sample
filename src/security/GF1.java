package security;

import java.io.*;

public class GF1 extends Poly{

	public static int prime = 0;
	public static int[][] addField;
	public static int[][] multField;
	public static int x = 0;
	public static int y = 0;
	
	public static void main(String[] args) {
		loadFile();
		if(isPrime(prime) == false) {
			System.out.println("Value is not prime. Closing program.");
			return;
		}
		createField();
		if(isContained(x, y) == false) {
			System.out.println("Elements not in finite field. Closing program");
			return;
		}
		File output = new File("output_gf1.txt");
		output.delete();
		writeFile(output, addElements());
		writeFile(output, subElements());
		writeFile(output, multElements());
		writeFile(output, divElements());
	}
	
	/**
	 * Returns the sum of the finite field addition. The addition is performed by adding the two elements together,
	 * and then performing mod by the prime number on the sum.
	 * @return sum - The result of the finite field addition.
	 */
	protected static int addElements() {
		int sum = addField[x+1][y+1];
		return sum;
	}
	
	/**
	 * Returns the product of the finite field multiplication. The multiplication is performed by 
	 * multiplying the two elements together, and then performing mod by the prime number on the sum.
	 * @return product - The result of the finite field multiplication.
	 */
	protected static int multElements() {
		int product = multField[x+1][y+1];
		return product;
	}
	
	/**
	 * Returns the sum of the finite field subtraction. The subtraction is performed by
	 * adding the additive inverse of x to y.
	 * @return sum - The result of y + the additive inverse of x.
	 */
	protected static int subElements() {
		int sum = (y + (prime - x));
		return sum;
	}
	
	/**
	 * Returns the product of x times the multiplicative inverse of y modded by the prime number.
	 * @return product - Product is equal to (x * y^-1) % prime.
	 */
	protected static int divElements() {
		int product = 0;
		for(int i = 1; i <= prime; i++) {
			if(multField[i][y+1] == 1) {
				product = ((x * (i-1)) % prime);
			}
		}
		return product;
	}
	
	/**
	 * Uses the ternary operator to quickly perform a recursive GCD search. 
	 * For reference, the ternary operator goes (expression) ? (if true) : (if false);
	 * @param a - The numerator in the equation.
	 * @param b - The denominator.
	 * @return a or GCD(b, a%b) depending on if b is equal to 0.
	 */
	protected static int GCD(int a, int b) {
		return b == 0 ? a : GCD(b, a % b);
	}
	
	/**
	 * Reads in 3 lines from an input file and stores the first value (a prime number) into prime. 
	 * The second are third values are used for the finite field arithmetic.
	 */
	protected static void loadFile() {
		File file = new File("input_gf1.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			prime = Integer.valueOf(reader.readLine());
			x = Integer.valueOf(reader.readLine());
			y = Integer.valueOf(reader.readLine());
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
	 * Creates the addition and multiplication finite fields from the prime number provided.
	 */
	protected static void createField() {
		addField = new int[prime+1][prime+1];
		multField = new int[prime+1][prime+1];
		for(int i = 1; i <= prime; i++) {
			addField[i][0] = i-1;
			addField[0][i] = i-1;
			multField[i][0] = i-1;
			multField[0][i] = i-1;
		}
		for(int i = 1; i <= prime; i++) {
			for(int j = 1; j <= prime; j++) {
				addField[i][j] = ((i-1 + j-1) % prime);
				multField[i][j] = (((i-1) * (j-1)) % prime);
			}
		}
	}
	
	/**
	 * Writes the result of the finite field arithmetic to the specified file.
	 * @param output - The file to be written to.
	 * @param num - The result of the finite field arithmetic
	 */
	protected static void writeFile(File output, int num) {
		try(PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(output, true)))) {
			write.println(num);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Checks to make sure that the specified prime number is in fact prime.
	 * @param num - the number being checked.
	 * @return true or false depending on if the number is prime or not.
	 */
	protected static boolean isPrime(int num) {
		for(int i = 2; i <= num / 2; i++) {
			if(num % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks to ensure that the elements belong to Z sub prime.
	 * @param n - The first element.
	 * @param m - The second element.
	 * @return true or false depending on if both elements belong.
	 */
	protected static boolean isContained(int n, int m) {
		if(n < prime && m < prime) {
			return true;
		}
		return false;
	}
}
