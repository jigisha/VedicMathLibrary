package com.vmath.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * to dos:
 * left-to-right calculation
 * store upto 20! for factorial calculations.
 * +ve and -ve operands.
 * removal of grid in mul.
 * time, space and energy optimization.
 * 
 * Product catalog methods:
 * multiply, square, cube, power(exponential), factorial, min, max (for 2 or more operands).
 *  
 * Division catalaog:
 * div, inverse, square root, cube root, nth root, log
 * 
 * Trigonometric functions:
 * sine, cosine, tangent, sine inv, cosine inv, tangent inv.
 * 
 * Transcendental equations.
 * 
 * Calculus:
 * Differentiation, Integration, Integro-Differential equations, Patrial differential equations.
 * 
 * Functions of polynomials and bipolynomials:
 * 
 * Curve fitting. 
 * 
 * Matrices inversion and multiplication. Determinants.
 * 
 * Random number generator.
 * 
 * Statistical catalog.
 * 
 * @author jiaryya
 *
 */
public class VMathLib {

	private static int[][] singlesProdLookup = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
								 { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
								 { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18 }, 
								 { 0, 3, 6, 9, 12, 15, 18, 21, 24, 27 }, 
								 { 0, 4, 8, 12, 16, 20, 24, 28, 32, 36 },
								 { 0, 5, 10, 15, 20, 25, 30, 35, 40, 45 }, 
								 { 0, 6, 12, 18, 24, 30, 36, 42, 48, 54 }, 
								 { 0, 7, 14, 21, 28, 35, 42, 49, 56, 63 },
								 { 0, 8, 16, 24, 32, 40, 48, 56, 64, 72 }, 
								 { 0, 9, 18, 27, 36, 45, 54, 63, 72, 81 } };

	// To calculate "x to the power y"
	public static String pow(String x, int y) throws InvalidNumberException, InvalidMultiplicationArgException {
		int dec_place = x.indexOf(VMConstants.DECIMAL);
		if (dec_place > -1) {
			StringBuilder temp_num = new StringBuilder(x);
			temp_num = temp_num.deleteCharAt(dec_place);
			StringBuilder res = new StringBuilder(power(temp_num.toString(), y));
			return res.insert((dec_place * y), VMConstants.DECIMAL).toString();
		}
		else
			return power(x, y);
	}

	// This method for "pow" reuses the results of multiplications to half the magnitude of "y". Complexity = O(log n)
	private static String power(String x, int y) throws InvalidNumberException, InvalidMultiplicationArgException {
		if (y == 0)
			return VMConstants.ONE;
		if (y == 1)
			return x;
		if (y == 2)
			return square(x);
		if (y == 3)
			return cube(x);

		String original_x = x;
		x = square(power(original_x, y >> 1));

		if (((y >> 0) & 1) == 0) {
			return x;
		}
		else {
			return multiply(original_x, x);
		}
	}

	// To calculate square of a number
	public static String sqr(String x) throws InvalidNumberException, InvalidMultiplicationArgException {
		StringBuilder temp_x = new StringBuilder(x);
		int dec_place = x.indexOf(VMConstants.DECIMAL);
		if (dec_place > -1) {
			temp_x = temp_x.deleteCharAt(dec_place);
			StringBuilder res = new StringBuilder(square(temp_x.toString()));
			return res.insert((dec_place << 1), VMConstants.DECIMAL).toString();
		}
		else
			return square(x);
	}

	private static String square(String x) throws InvalidNumberException, InvalidMultiplicationArgException {
		return multiply(x, x);
	}

	// To calculate cube of a number
	public static String cub(String x) throws InvalidNumberException, InvalidMultiplicationArgException {
		StringBuilder temp_x = new StringBuilder(x);
		int dec_place = x.indexOf(VMConstants.DECIMAL);
		if (dec_place > -1) {
			temp_x = temp_x.deleteCharAt(dec_place);
			StringBuilder res = new StringBuilder(cube(temp_x.toString()));
			return res.insert((dec_place * 3), VMConstants.DECIMAL).toString();
		}
		else
			return cube(x);
	}

	private static String cube(String x) throws InvalidNumberException, InvalidMultiplicationArgException {
		return multiply(square(x), x);
	}

	// This method is for multiplying x1 by x2
	public static String mul(String x1, String x2) throws InvalidNumberException, InvalidMultiplicationArgException {
		int total_decimal_places = -1;
		StringBuilder temp_x1 = new StringBuilder(x1);
		StringBuilder temp_x2 = new StringBuilder(x2);

		if (x1.indexOf(VMConstants.DECIMAL) > -1 || x2.indexOf(VMConstants.DECIMAL) > -1) {
			int decimal_index_x1 = x1.indexOf(VMConstants.DECIMAL);
			int decimal_index_x2 = x2.indexOf(VMConstants.DECIMAL);

			if (decimal_index_x1 > -1) {
				temp_x1 = temp_x1.deleteCharAt(decimal_index_x1);
				total_decimal_places += decimal_index_x1;
			}
			if (decimal_index_x2 > -1) {
				temp_x2 = temp_x2.deleteCharAt(decimal_index_x2);
				total_decimal_places += decimal_index_x2;
			}
		}
		if (total_decimal_places > -1) {
			StringBuilder res = new StringBuilder(multiply(temp_x1.toString(), temp_x2.toString()));
			return res.insert(total_decimal_places + 1, VMConstants.DECIMAL).toString();
		}
		else {
			return multiply(x1, x2);
		}
	}

	// The result of multiplication is of length "x1.length() + x2.length()"
	// Used byte[] to save extra bytes for storing integers.
	private static String multiply(String x1, String x2) throws InvalidNumberException, InvalidMultiplicationArgException {
		int x1_len = x1.length(), x2_len = x2.length();
		byte[] x1_array, x2_array;
		int diff_in_len = Math.abs(x1_len - x2_len);
		List<String> cross_products = new ArrayList<String>();

		if (x1_len > x2_len) {
			x1_array = VMathUtils.convertStringToByteArray(x1, x1_len);
			x2_array = VMathUtils.convertStringToByteArray(x2, x1_len);
			cross_products = getCrossProducts(x1_array, x2_array, diff_in_len);
		}
		else {
			x1_array = VMathUtils.convertStringToByteArray(x1, x2_len);
			x2_array = VMathUtils.convertStringToByteArray(x2, x2_len);
			cross_products = getCrossProducts(x2_array, x1_array, diff_in_len);
		}
		int res_length = x1_len + x2_len;
		return generateResult(createCrossProdGrid(cross_products, res_length), res_length);

	}

	// This the core idea of the Vedic math technique - "Calculation of Cross-Products" where actual multiplication can be avoided with look-ups
	// The time complexity is theta(max(x1,x2).length()^2).
	private static List<String> getCrossProducts(byte[] x1, byte[] x2, int diff_in_len) {
		List<String> crs_prods = new ArrayList<String>();
		int x1_len = x1.length;
		for (int round1_ctr = x1_len - 1; round1_ctr >= 0; round1_ctr--) {
			int crs_sum1 = 0;
			for (int x1_ctr = x1_len - 1, x2_ctr = round1_ctr; x1_ctr >= round1_ctr; x1_ctr--, x2_ctr++) {
				crs_sum1 += singlesProdLookup[x1[x1_ctr]][x2[x2_ctr]];
			}
			crs_prods.add(String.valueOf(crs_sum1));
		}
		for (int round2_ctr = x1_len - 2; round2_ctr > diff_in_len - 1; round2_ctr--) {
			int crs_sum2 = 0;
			for (int x2_ctr = round2_ctr, x1_ctr = 0; x2_ctr >= 0; x2_ctr--, x1_ctr++) {
				crs_sum2 += singlesProdLookup[x2[x2_ctr]][x1[x1_ctr]];
			}
			crs_prods.add(String.valueOf(crs_sum2));
		}
		return crs_prods;
	}

	// Grid makes it easy to fetch the position-wise digits of the result.
	private static byte[][] createCrossProdGrid(List<String> crs_prods, int res_length) {
		byte[][] crs_prod_grid = new byte[crs_prods.size()][res_length];
		for (int row_ctr = 0; row_ctr < crs_prods.size(); row_ctr++) {
			String crs_prod = crs_prods.get(row_ctr);
			int single_int_ctr = 0;
			while (single_int_ctr < crs_prod.length()) {
				crs_prod_grid[row_ctr][res_length - 1 - row_ctr - single_int_ctr] = 
						(byte) Integer.parseInt(String.valueOf(crs_prod.charAt(crs_prod.length() - 1 - single_int_ctr)));
				single_int_ctr++;
			}
		}
		return crs_prod_grid;
	}

	// Calculates the final result from the grid.
	private static String generateResult(byte[][] crs_prod_grid, int res_length) {
		byte[] res = new byte[res_length];
		int num_cross_products = crs_prod_grid.length;
		int carry = 0;
		for (int grid_col_ctr = res_length - 1; grid_col_ctr >= 0; grid_col_ctr--) {
			int sum = 0;
			for (int grid_row_ctr = 0; grid_row_ctr < num_cross_products; grid_row_ctr++) {
				sum += crs_prod_grid[grid_row_ctr][grid_col_ctr];
			}
			sum += carry;
			byte lsb = (byte) (sum & 0x9);
			carry = sum / 10;
			res[grid_col_ctr] = lsb;
		}
		StringBuilder res_mul = new StringBuilder();
		for (int i = 0; i < res_length; i++) {
			res_mul.append(res[i]);
		}
		return res_mul.toString();
	}
	
	public static String fact(int n) throws InvalidNumberException, InvalidMultiplicationArgException {
		
		if (((n >> 0) & 1) == 0)
			return factorial_internal(n, 2); // we need a midterm to be used for getting deviations 		
		else 
			return factorial_internal(n, 1);
	}
	
	// using the x2-y2 = (x+y)*(x-y) formula to reduce the multiplcations to half of n
	private static String factorial_internal(int n, int l) throws InvalidNumberException, InvalidMultiplicationArgException {
		int mid_term = n >> 1 + 1;
		int max_deviation = mid_term - l;
		ArrayList<String> products = new ArrayList<String>();
		
		for (int diff = max_deviation; diff > 0; diff = diff - 2) {
			int first_term = mid_term * mid_term - diff * diff;
			products.add(String.valueOf(first_term * (first_term + diff << 1 - 1)));
		}		
		//if(if l== 2 ) result = l*mid_term;
		String result = String.valueOf(mid_term);
		for (int prod_ctr = 0; prod_ctr < products.size(); prod_ctr++) {
			result = multiply(result, products.get(prod_ctr));
		}
		return result;
	}
	
	
	public static void main(String[] args) throws InvalidNumberException, InvalidMultiplicationArgException {
		VMathUtils.start();
		String n = "9999646754867974634647567976";
		double l = 2432902008176640000L;//20! is max that can be stored
		VMathUtils.numbersPreprocessing(new ArrayList<String>(Arrays.asList(n)));
		System.out.println(pow(n, 3));
		 VMathUtils.stop();
		 System.out.println("elapsed time in nanoseconds: " + VMathUtils.getElapsedTime());
	}
}
