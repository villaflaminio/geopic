
package com.flaminiovilla.geohash.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.geohash.util.BoundingBoxGeoHashIterator;
import ch.hsr.geohash.util.RandomGeohashes;
import ch.hsr.geohash.util.TwoGeoHashBoundingBox;

public class GeoHashTest {
	private GeoHash hash;

	@Before
	public void setUp() {
		hash = new GeoHash();
	}

	@Test
	public void testAddingOnes() {
		hash.addOnBitToEnd();
		assertEquals(0x1l, hash.bits);
		assertEquals(1, hash.significantBits());
		hash.addOnBitToEnd();
		hash.addOnBitToEnd();
		hash.addOnBitToEnd();
		assertEquals(0xfl, hash.bits);
		assertEquals(4, hash.significantBits());
	}

	@Test
	public void testAddingZeroes() {
		hash.addOnBitToEnd();
		assertEquals(0x1l, hash.bits);

		hash.addOffBitToEnd();
		hash.addOffBitToEnd();
		hash.addOffBitToEnd();
		hash.addOffBitToEnd();
		assertEquals(0x10l, hash.bits);
		assertEquals(5, hash.significantBits());
	}

	@Test
	public void testToBase32() {
		hash.bits = 0x6ff0414000000000l;
		hash.significantBits = 25;

		String base32 = hash.toBase32();
		assertEquals("ezs42", base32);
	}

	@Test(expected = IllegalStateException.class)
	public void toBase32ShouldThrowWhenPrecisionIsNotAMultipleOf5() {
		hash.bits = 0x6ff0413000000000l;
		hash.significantBits = 24;
		hash.toBase32();
	}


	@Test
	public void testToAndFromBinaryString() {
		for (GeoHash gh : RandomGeohashes.fullRange()) {
			String binaryString = gh.toBinaryString();
			GeoHash readBack = GeoHash.fromBinaryString(binaryString);
			assertEquals(gh, readBack);
		}
	}

	@Test
	public void testGetLatitudeBits() {
		hash = GeoHash.withBitPrecision(30, 30, 16);
		long[] latitudeBits = hash.getRightAlignedLatitudeBits();
		assertEquals(0xaal, latitudeBits[0]);
		assertEquals(8, latitudeBits[1]);
	}

	@Test
	public void testGetLongitudeBits() {
		hash = GeoHash.withBitPrecision(30, 30, 16);
		long[] longitudeBits = hash.getRightAlignedLongitudeBits();
		assertEquals(0x95l, longitudeBits[0]);
		assertEquals(8, longitudeBits[1]);
	}


	@Test
	public void testEqualsAndHashCode() {
		GeoHash hash1 = GeoHash.withBitPrecision(30, 30, 24);
		GeoHash hash2 = GeoHash.withBitPrecision(30, 30, 24);
		GeoHash hash3 = GeoHash.withBitPrecision(30, 30, 10);

		assertTrue(hash1.equals(hash2) && hash2.equals(hash1));
		assertFalse(hash1.equals(hash3) && hash3.equals(hash1));

		assertEquals(hash1.hashCode(), hash2.hashCode());
		assertFalse(hash1.hashCode() == hash3.hashCode());
	}


}
