
package com.flaminiovilla.geohash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		a = new BoundingBox(new WGS84Point(21, 20), new WGS84Point(30, 31));
		b = new BoundingBox(a);
		c = new BoundingBox(new WGS84Point(-45, -170), new WGS84Point(45, 170));
		d = new BoundingBox(new WGS84Point(-45, 170), new WGS84Point(-45, -170));
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
		assertContains(bbox, new WGS84Point(45.5, 120.5));
		assertNotContains(bbox, new WGS84Point(90, 90));

		// Testing bounding box over 180-Meridian
		bbox = new BoundingBox(45, 46, 170, -170);
		assertContains(bbox, new WGS84Point(45.5, 175));
		assertContains(bbox, new WGS84Point(45.5, -175));
		assertNotContains(bbox, new WGS84Point(45.5, -165));
		assertNotContains(bbox, new WGS84Point(45.5, 165));
	}

	@Test
	public void testSize() {
		BoundingBox bbox = new BoundingBox(45, 90, 0, 30);
		assertHeightIs(bbox, 45);
		assertWidthIs(bbox, 30);
		bbox = new BoundingBox(-45, 45, -22.5, 30);
		assertHeightIs(bbox, 90);
		assertWidthIs(bbox, 52.5);
		bbox = new BoundingBox(-46.1, -44, -128, -127.2);
		assertHeightIs(bbox, 2.1);
		assertWidthIs(bbox, 0.8);

		// Testing bounding box over 180-Meridian
		bbox = new BoundingBox(45, 90, 170, -170);
		assertHeightIs(bbox, 45);
		assertWidthIs(bbox, 20);
	}

}
