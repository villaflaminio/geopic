
package com.flaminiovilla.geopic.geohash.util;


import com.flaminiovilla.geopic.component.geoHash.model.GeoHash;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GeoHashTest {
	private GeoHash hash;

	@Before
	public void setUp() {
		hash = new GeoHash();
	}

	@Test
	public void testEqualsAndHashCode() {
		GeoHash hash1 = GeoHash.withBitPrecision(30, 30, 24);
		GeoHash hash2 = GeoHash.withBitPrecision(30, 30, 24);
		GeoHash hash3 = GeoHash.withBitPrecision(30, 30, 10);

		Assert.assertTrue(hash1.equals(hash2) && hash2.equals(hash1));
		assertFalse(hash1.equals(hash3) && hash3.equals(hash1));

		assertEquals(hash1.hashCode(), hash2.hashCode());
		assertNotEquals(hash1.hashCode(), hash3.hashCode());
	}


}
