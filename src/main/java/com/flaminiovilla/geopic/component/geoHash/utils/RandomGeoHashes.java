package com.flaminiovilla.geopic.component.geoHash.utils;


import com.flaminiovilla.geopic.component.geoHash.model.GeoHash;
import com.flaminiovilla.geopic.component.geoHash.model.GeoPoint;

import java.util.Random;

public class RandomGeoHashes {

	/**
	 * Fixes seed to make things reproducible.
	 */
	private static final Random rand = new Random(9817298371L);

	/**
	 * @return a completely random {@link GeoHash} with a random number of bits.
	 *         precision will be between [5,64] bits.
	 */
	public static GeoHash create() {
		return GeoHash.withBitPrecision(randomLatitude(), randomLongitude(), randomPrecision());
	}
	public static GeoPoint randomGeoPoint() {
		return new GeoPoint(randomLatitude(), randomLongitude());
	}
	private static double randomLatitude() {
		return (rand.nextDouble() - 0.5) * 180;
	}

	private static double randomLongitude() {
		return (rand.nextDouble() - 0.5) * 360;
	}

	private static int randomPrecision() {
		return rand.nextInt(60) + 5;
	}


}
