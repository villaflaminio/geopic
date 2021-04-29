package com.flaminiovilla.geohash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ch.hsr.geohash.util.VincentyGeodesy;
import org.junit.Test;

import ch.hsr.geohash.queries.GeoHashCircleQuery;

public class GeoHashCircleQueryTest {
	@Test
	public void testIssue3WithCircleQuery() throws Exception {
		WGS84Point center = new WGS84Point(39.86391280373075, 116.37356590048701);
		GeoHashCircleQuery query = new GeoHashCircleQuery(center, 500);

		// the distance between center and test1 is about 430 meters
		WGS84Point test1 = new WGS84Point(39.8648866576058, 116.378465869303);
		System.out.println("distanza di test1 " + VincentyGeodesy.distanceInMeters(test1, center)  + "km");
		// the distance between center and test2 is about 510 meters
		WGS84Point test2 = new WGS84Point(39.8664787092599, 116.378552856158);
		System.out.println("distanza di test2 " + VincentyGeodesy.distanceInMeters(test2, center)  + "km");

		// the distance between center and test2 is about 600 meters
		WGS84Point test3 = new WGS84Point(39.8786787092599, 116.378552856158);
		System.out.println("distanza di test3 " + VincentyGeodesy.distanceInMeters(test3, center)  + "km");

		assertTrue(query.contains(test1));
		assertTrue(query.contains(test2));
		assertFalse(query.contains(test3));

	}

	@Test
	public void test180MeridianCircleQuery() throws Exception {
		// Test query over 180-Meridian
		WGS84Point center = new WGS84Point(39.86391280373075, 179.98356590048701);
		GeoHashCircleQuery query = new GeoHashCircleQuery(center, 3000);

		WGS84Point test1 = new WGS84Point(39.8648866576058, 180);
		WGS84Point test2 = new WGS84Point(39.8664787092599, -180);
		WGS84Point test3 = new WGS84Point(39.8686787092599, -179.9957861565146);
		WGS84Point test4 = new WGS84Point(39.8686787092599, 179.0057861565146);
		WGS84Point test5 = new WGS84Point(39.8686787092599, -179.0);

		assertTrue(query.contains(test1));
		assertTrue(query.contains(test2));
		assertTrue(query.contains(test3));
		assertFalse(query.contains(test4));
		assertFalse(query.contains(test5));
	}
}
