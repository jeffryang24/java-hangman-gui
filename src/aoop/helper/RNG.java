package aoop.helper;

import java.util.Random;

/*
 * @author		: Jeffry Angtoni
 * @desc		: Random Number Generator (RNG)
 * @date		: January 14, 2017
 * 
 */

public class RNG {
	private static Random rnd = new Random();
	public static int randomNumber(int min, int max){
		return rnd.nextInt((max - min) + 1) + min;
	}
}
