package com.flaminiovilla.geopic.geohash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.flaminiovilla.geopic.component.geoHash.model.GeoPoint;
import com.flaminiovilla.geopic.component.geoHash.utils.GeoHashCircle;
import com.flaminiovilla.geopic.component.geoHash.utils.VincentyGeodesy;
import org.junit.Test;


public class GeoHashCircleQueryTest {
	@Test
	public void testIssue3WithCircleQuery() throws Exception {
		GeoPoint center = new GeoPoint(39.86391280373075, 116.37356590048701);
		GeoHashCircle query = new GeoHashCircle(center, 500);

		// the distance between center and test1 is about 430 meters
		GeoPoint test1 = new GeoPoint(39.8648866576058, 116.378465869303);
		System.out.println("distanza di test1 " + VincentyGeodesy.distanceInMeters(test1, center)  + "km");
		// the distance between center and test2 is about 510 meters
		GeoPoint test2 = new GeoPoint(39.8664787092599, 116.378552856158);
		System.out.println("distanza di test2 " + VincentyGeodesy.distanceInMeters(test2, center)  + "km");

		// the distance between center and test2 is about 600 meters
		GeoPoint test3 = new GeoPoint(39.8786787092599, 116.378552856158);
		System.out.println("distanza di test3 " + VincentyGeodesy.distanceInMeters(test3, center)  + "km");

		assertTrue(query.contains(test1));
		assertTrue(query.contains(test2));
		assertFalse(query.contains(test3));

	}

	@Test
	public void test180MeridianCircleQuery() throws Exception {
		// Test query over 180-Meridian
		GeoPoint center = new GeoPoint(39.86391280373075, 179.98356590048701);
		GeoHashCircle query = new GeoHashCircle(center, 3000);

		GeoPoint test1 = new GeoPoint(39.8648866576058, 180);
		GeoPoint test2 = new GeoPoint(39.8664787092599, -180);
		GeoPoint test3 = new GeoPoint(39.8686787092599, -179.9957861565146);
		GeoPoint test4 = new GeoPoint(39.8686787092599, 179.0057861565146);
		GeoPoint test5 = new GeoPoint(39.8686787092599, -179.0);

		assertTrue(query.contains(test1));
		assertTrue(query.contains(test2));
		assertTrue(query.contains(test3));
		assertFalse(query.contains(test4));
		assertFalse(query.contains(test5));
	}
}
