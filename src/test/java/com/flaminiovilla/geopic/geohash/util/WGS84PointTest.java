
package com.flaminiovilla.geopic.geohash.util;

import com.flaminiovilla.geopic.component.geoHash.model.GeoPoint;
import com.flaminiovilla.geopic.component.geoHash.utils.VincentyGeodesy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


class GeoPointTest {
	private static final double DELTA = 0.00001;
	private GeoPoint a;
	private GeoPoint b;
	private GeoPoint c;
	private GeoPoint d;

	@Before
	public void setUp() {
		a = new GeoPoint(47.2342, 15.7465465);
		c = new GeoPoint(-47.234, b.getLongitude());
		d = new GeoPoint(-32.9687253, 12.42334242);
	}

	@Test
	public void testVincenty() {
		GeoPoint startPoint = new GeoPoint(40, 40);

		int distanceInMeters = 10000;
		GeoPoint result = VincentyGeodesy.moveInDirection(startPoint, 120,
				distanceInMeters);
		assertEquals(40.10134882, result.getLongitude(), DELTA);
		assertEquals(39.9549245, result.getLatitude(), DELTA);

		assertEquals(distanceInMeters, VincentyGeodesy.distanceInMeters(
				startPoint, result), DELTA);

		GeoPoint p1 = new GeoPoint(1, 1);
		int tenThousandKilometers = 10000000;
		GeoPoint p2 = VincentyGeodesy.moveInDirection(p1, 270, tenThousandKilometers);
		System.out.println(p2);
		assertEquals(tenThousandKilometers, VincentyGeodesy.distanceInMeters(p1, p2), DELTA);
	}

	@Test
	public void testEquals() {
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		assertNotSame(a, b);

		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(d, c);
		assertNotEquals(d, a);
		assertNotEquals(d, new Integer(10));
	}

	@Test
	public void testHashCode() {
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
		assertNotEquals(a.hashCode(), c.hashCode());
		assertNotEquals(d.hashCode(), c.hashCode());
		assertNotEquals(d.hashCode(), new Integer(10).hashCode());
	}

}