package com.vmath.lib;

import java.util.List;
import java.util.regex.Pattern;

public class VMathUtils {

	private static final Pattern inputNumber = Pattern.compile("^[0-9](\\.?)[0-9]*$");

	public static boolean notEmpty(String number) {
		if (null == number || number.equals("") || number.equals(" ")) {
			return false;
		}
		return true;
	}

	public static boolean onlyNumbers(String number) {
		for (int i = 0; i < number.length(); i++) {
			if (!inputNumber.matcher(String.valueOf(number.charAt(i))).matches()) {
				return false;
			}
		}
		return true;
	}

	public static String removeLeadingZeros(StringBuilder number) {
		int j = 0;
		while (number.charAt(j) == '0') {
			j++;
		}
		return new String(number.delete(0, j));
	}

	private static long startTime = 0;
	private static long stopTime = 0;
	private static boolean running = false;

	public static void start() {
		startTime = System.nanoTime();
		running = true;
	}

	public static void stop() {
		stopTime = System.nanoTime();
		running = false;
	}

	public static long getElapsedTime() {
		long elapsed;
		if (running) {
			elapsed = (System.nanoTime() - startTime);
		}
		else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

	public static long getElapsedTimeSecs() {
		long elapsed;
		if (running) {
			elapsed = ((System.nanoTime() - startTime));
		}
		else {
			elapsed = ((stopTime - startTime));
		}
		return elapsed;
	}

	public static void numbersPreprocessing(List<String> numbers) throws InvalidNumberException {
		if (numbers.size() > 1) {
			for (String num : numbers) {
				if (!(notEmpty(num) && onlyNumbers(num))) {
					throw new InvalidNumberException(VMConstants.INEXP_MSG);
				}
				if (num.charAt(num.length() - 1) == '0') {
					num = removeLeadingZeros(new StringBuilder(num));
				}
			}
		}
	}

	public static byte[] convertStringToByteArray(String str, int array_len) {
		byte[] intArray = new byte[array_len];
		if (str.length() != array_len) {
			int str_index = str.length() - 1;
			int array_index = array_len - 1;
			while (str_index >= 0) {
				intArray[array_index--] = (byte) Integer.parseInt(String.valueOf(str.charAt(str_index--)));
			}
		}
		else {
			int index = 0;
			while (index < str.length()) {
				intArray[index] = (byte) Integer.parseInt(String.valueOf(str.charAt(index++)));
			}
		}
		return intArray;
	}

	public static void main(String[] args) {
		System.out.println(removeLeadingZeros(new StringBuilder("0000199007822")));
		System.out.println(onlyNumbers("9917371"));

	}
}
