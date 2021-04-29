
package com.flaminiovilla.geohash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.hsr.geohash.queries.GeoHashBoundingBoxQuery;
import ch.hsr.geohash.queries.GeoHashQuery;

public class GeoHashBoundingBoxSearchTest {

	@Test
	public void testSeveralBoundingBoxes() {
		checkSearchYieldsCorrectNumberOfHashes(40.2090980098, 40.21982983232432, -22.523432424324, -22.494234232442);
		checkSearchYieldsCorrectNumberOfHashes(40.09872762, 41.23452234, 30.0113312322, 31.23432);

		checkSearchYieldsCorrectHashes(47.300200, 47.447907, 8.471276, 8.760941, "u0qj");
		checkSearchYieldsCorrectHashes(47.157502, 47.329727, 8.562244, 8.859215, "u0qj", "u0qm", "u0qh", "u0qk");

		// Testing bounding box over 180-Meridian
		checkSearchYieldsCorrectNumberOfHashes(40.2090980098, 40.21982983232432, 170.523432424324, -170.494234232442);
		checkSearchYieldsCorrectNumberOfHashes(40.2090980098, 40.21982983232432, 170.523432424324, 160.494234232442);

		checkSearchYieldsCorrectHashes(40.2090980098, 40.21982983232432, 170.523432424324, -170.494234232442, "xz", "8p");
		checkSearchYieldsCorrectBinaryHashes(47.157502, 47.329727, 179.062244, -179.859215, "1111101010101111", "010100000000010100000", "010100000000010100010");

		// Check duplicate handling
		checkSearchYieldsCorrectBinaryHashes(47.157502, 47.329727, 179.062244, 160, "");
		checkSearchYieldsCorrectBinaryHashes(47.157502, 47.329727, 179.062244, -1, "01", "1111101010101111");
	}


}
