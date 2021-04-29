
package com.flaminiovilla.geopic.geohash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.flaminiovilla.geopic.component.geoHash.model.BoundingBox;
import com.flaminiovilla.geopic.component.geoHash.model.GeoPoint;
import org.junit.Before;
import org.junit.Test;

public class BoundingBoxTest {

	private static final double DELTA = 1e-12;
	private BoundingBox a;
	private BoundingBox b;
	private BoundingBox c;
	private BoundingBox d;
	private BoundingBox e;

	@Before
	public void setUp() {
		a = new BoundingBox(new GeoPoint(21, 20), new GeoPoint(30, 31));
		b = new BoundingBox(a);
		c = new BoundingBox(new GeoPoint(-45, -170), new GeoPoint(45, 170));
		d = new BoundingBox(new GeoPoint(-45, 170), new GeoPoint(-45, -170));
		e = new BoundingBox(d);
	}

	@Test
	public void testHashCode() {
		assertEquals(a.hashCode(), b.hashCode());
		assertFalse(a.hashCode() == c.hashCode());
	}

	@Test
	public void testEqualsObject() {
		assertEquals(a, b);
		assertEquals(b, a);
		assertFalse(a.equals(c));
		assertEquals(d, e);
		assertEquals(e, d);
		assertFalse(c.equals(d));
		assertFalse(c.equals(a));
	}

	@Test
	public void testContains() {
		BoundingBox bbox = new BoundingBox(45, 46, 120, 121);
		assertContains(bbox, new GeoPoint(45.5, 120.5));
		assertNotContains(bbox, new GeoPoint(90, 90));

		// Testing bounding box over 180-Meridian
		bbox = new BoundingBox(45, 46, 170, -170);
		assertContains(bbox, new GeoPoint(45.5, 175));
		assertContains(bbox, new GeoPoint(45.5, -175));
		assertNotContains(bbox, new GeoPoint(45.5, -165));
		assertNotContains(bbox, new GeoPoint(45.5, 165));
	}


	private void assertContains(BoundingBox box, GeoPoint p) {
		assertTrue(p + " should be in " + box, box.contains(p));
	}

	private void assertNotContains(BoundingBox box, GeoPoint p) {
		assertFalse(p + " should NOT be in " + box, box.contains(p));
	}

}
