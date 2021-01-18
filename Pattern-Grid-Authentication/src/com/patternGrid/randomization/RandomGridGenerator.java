package com.patternGrid.randomization;

import java.security.SecureRandom;
import java.util.Random;

public class RandomGridGenerator {

	private static char[] alphaNumericCharArray = {

			'X', 'e', 'T', 'U', 'K', 'Y', 'f', 'g', 's', 't', 'u', 'A', 'B', 'i', '7', '8', '9', '0', 'j', 'k', 'C',
			'D', 'V', 'c', 'Q', 'R', 'S', 'h', 'v', 'w', 'x', 'y', 'z', 'm', 'n', 'o', 'p', 'q', 'r', 'W', '4', '5',
			'H', 'J', 'E', 'F', 'G', 'd', 'L', 'M', 'N', 'O', 'P', '1', '2', '3', 'a', 'b', 'Z', '6' };

	private static boolean checkIfString(String[] randomString, String value, int currentSize) {
		for (int i = 0; i < currentSize; i++) {
			if (randomString[i].equals(value))
				return true;
		}
		return false;
	}

	public static String[][] randomizor(int row, int col, int maxCharPerString) {
		int size = row * col;
		String[] randomString = new String[size];
		int counter = 0;
		SecureRandom rand = new SecureRandom();
		while (counter < size) {
			String genString = "";
			// int currStringSize = rand.nextInt(maxCharPerString);
			// currStringSize++;
			// System.out.println(currStringSize);
			for (int j = 0; j < maxCharPerString; j++) {
				int index = rand.nextInt(alphaNumericCharArray.length);
				genString += ((Character) alphaNumericCharArray[index]).toString();
			}
			if (!checkIfString(randomString, genString, counter)) {
				randomString[counter] = genString;
				counter++;
			}
		}

		String[][] randomStringJagged = new String[row][col];
		int dummyCounter = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				randomStringJagged[i][j] = randomString[dummyCounter];
				dummyCounter++;
			}
		}

		return randomStringJagged;
	}

	public static String getRandomOTP() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

}
