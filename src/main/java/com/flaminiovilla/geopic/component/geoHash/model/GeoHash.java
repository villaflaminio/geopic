
package com.flaminiovilla.geopic.component.geoHash.model;

import java.io.Serializable;
import java.util.Arrays;

public final class GeoHash implements Comparable<GeoHash>, Serializable {
	public static final int MAX_BIT_PRECISION = 64;

	private static final long serialVersionUID = -8553214249630252175L;
	public static final long FIRST_BIT_FLAGGED = 0x8000000000000000l;
	private static final char[] base32 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private static final int[] decodeArray = new int['z' + 1];

	static {
		Arrays.fill(decodeArray, -1);
		int sz = base32.length;
		for (int i = 0; i < sz; i++) {
			decodeArray[base32[i]] = i;
		}
	}

	protected long bits = 0;

	private BoundingBox boundingBox;

	protected byte significantBits = 0;

	public GeoHash() {
	}


	/**
	 * create a new {@link GeoHash} with the given number of bits accuracy. This
	 * at the same time defines this hash's bounding box.
	 */
	public static GeoHash withBitPrecision(double latitude, double longitude, int numberOfBits) {
		if (numberOfBits > MAX_BIT_PRECISION) {
			throw new IllegalArgumentException("A Geohash can only be " + MAX_BIT_PRECISION + " bits long!");
		}
		if (Math.abs(latitude) > 90.0 || Math.abs(longitude) > 180.0) {
			throw new IllegalArgumentException("Can't have lat/lon values out of (-90,90)/(-180/180)");
		}
		return new GeoHash(latitude, longitude, numberOfBits);
	}


	private GeoHash(double latitude, double longitude, int desiredPrecision) {
		desiredPrecision = Math.min(desiredPrecision, MAX_BIT_PRECISION);

		boolean isEvenBit = true;
		double[] latitudeRange = { -90, 90 };
		double[] longitudeRange = { -180, 180 };

		while (significantBits < desiredPrecision) {
			if (isEvenBit) {
				divideRangeEncode(longitude, longitudeRange);
			} else {
				divideRangeEncode(latitude, latitudeRange);
			}
			isEvenBit = !isEvenBit;
		}

		setBoundingBox(this, latitudeRange, longitudeRange);
		bits <<= (MAX_BIT_PRECISION - desiredPrecision);
	}

	private static void setBoundingBox(GeoHash hash, double[] latitudeRange, double[] longitudeRange) {
		hash.boundingBox = new BoundingBox(latitudeRange[0], latitudeRange[1], longitudeRange[0], longitudeRange[1]);
	}



	private void divideRangeEncode(double value, double[] range) {
		double mid = (range[0] + range[1]) / 2;
		if (value >= mid) {
			addOnBitToEnd();
			range[0] = mid;
		} else {
			addOffBitToEnd();
			range[1] = mid;
		}
	}


	/**
	 * get the base32 string for this {@link GeoHash}.<br>
	 * this method only makes sense, if this hash has a multiple of 5
	 * significant bits.
	 *
	 * @throws IllegalStateException
	 *             when the number of significant bits is not a multiple of 5.
	 */
	public String toBase32() {
		if (significantBits % 5 != 0) {
			throw new IllegalStateException("Cannot convert a geohash to base32 if the precision is not a multiple of 5.");
		}
		StringBuilder buf = new StringBuilder();

		long firstFiveBitsMask = 0xf800000000000000l;
		long bitsCopy = bits;
		int partialChunks = (int) Math.ceil(((double) significantBits / 5));

		for (int i = 0; i < partialChunks; i++) {
			int pointer = (int) ((bitsCopy & firstFiveBitsMask) >>> 59);
			buf.append(base32[pointer]);
			bitsCopy <<= 5;
		}
		return buf.toString();
	}


	/**
	 * find out if the given point lies within this hashes bounding box.<br>
	 * <i>Note: this operation checks the bounding boxes coordinates, i.e. does
	 * not use the {@link GeoHash}s special abilities.s</i>
	 */
	public boolean contains(GeoPoint point) {
		return boundingBox.contains(point);
	}

	public final void addOnBitToEnd() {
		significantBits++;
		bits <<= 1;
		bits = bits | 0x1;
	}

	protected final void addOffBitToEnd() {
		significantBits++;
		bits <<= 1;
	}

	@Override
	public String toString() {
		if (significantBits % 5 == 0) {
			return String.format("%s -> %s -> %s", padLeft(Long.toBinaryString(bits), 64, "0"), boundingBox, toBase32());
		} else {
			return String.format("%s -> %s, bits: %d", padLeft(Long.toBinaryString(bits), 64, "0"), boundingBox, significantBits);
		}
	}

	private static String padLeft(String s, int n, String pad) {
		return String.format("%" + n + "s", s).replace(" ", pad);
	}

	public String toBinaryString() {
		StringBuilder bui = new StringBuilder();
		long bitsCopy = bits;
		for (int i = 0; i < significantBits; i++) {
			if ((bitsCopy & FIRST_BIT_FLAGGED) == FIRST_BIT_FLAGGED) {
				bui.append('1');
			} else {
				bui.append('0');
			}
			bitsCopy <<= 1;
		}
		return bui.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof GeoHash) {
			GeoHash other = (GeoHash) obj;
			if (other.significantBits == significantBits && other.bits == bits) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int f = 17;
		f = 31 * f + (int) (bits ^ (bits >>> 32));
		f = 31 * f + significantBits;
		return f;
	}

	@Override
	public int compareTo(GeoHash o) {
		int bitsCmp = Long.compare(bits ^ FIRST_BIT_FLAGGED, o.bits ^ FIRST_BIT_FLAGGED);
		if (bitsCmp != 0) {
			return bitsCmp;
		} else {
			return Integer.compare(significantBits, o.significantBits);
		}
	}
}
