package com.patternGrid.randomization;

import java.security.SecureRandom;

public class RandomGridGenerator {

	private static char[] alphaNumericCharArray = {

			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	private static boolean checkIfString(String[] randomString, String value, int currentSize) {
		for (int i = 0; i < currentSize; i++) {
			if (randomString[i].equals(value))
				return true;
		}
		return false;
	}

	public static String[] randomizor(int size, int maxCharPerString) {

		String[] randomString = new String[size];
		int counter = 0;
		SecureRandom rand = new SecureRandom();
		while (counter < size) {
			String genString = "";
			int currStringSize = rand.nextInt(maxCharPerString);
			currStringSize++;
			// System.out.println(currStringSize);
			for (int j = 0; j < currStringSize; j++) {
				int index = rand.nextInt(alphaNumericCharArray.length);
				genString += ((Character) alphaNumericCharArray[index]).toString();
			}
			if (!checkIfString(randomString, genString, counter)) {
				randomString[counter] = genString;
				counter++;
			}
		}
		return randomString;
	}

}
