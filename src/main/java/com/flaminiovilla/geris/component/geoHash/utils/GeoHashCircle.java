
package com.flaminiovilla.geris.component.geoHash.utils;



import com.flaminiovilla.geris.component.geoHash.model.BoundingBox;
import com.flaminiovilla.geris.component.geoHash.model.GeoPoint;


/**
 * represents a radius search around a specific point via geohashes.
 * Approximates the circle with a square!
 */
public class GeoHashCircle  {
	private double radius;
	private BoundingBox query;
	private GeoPoint center;

	/**
	 * create a {@link GeoHashCircle} with the given center point and a radius in meters.
	 */
	public GeoHashCircle(GeoPoint center, double radius) {
		this.radius = radius;
		this.center = center;
		GeoPoint northEastCorner = VincentyGeodesy.moveInDirection(VincentyGeodesy.moveInDirection(center, 0, radius), 90, radius);
		GeoPoint southWestCorner = VincentyGeodesy.moveInDirection(VincentyGeodesy.moveInDirection(center, 180, radius), 270, radius);
		query = new BoundingBox(southWestCorner, northEastCorner);
	}


	public boolean contains(GeoPoint point) {
		return query.contains(point);
	}
	public String toString() {
		return "Cicle Query [center=" + center + ", radius=" + getRadiusString() + "]";
	}

	private String getRadiusString() {
		if (radius > 1000) {
			return radius / 1000 + "km";
		} else {
			return radius + "m";
		}
	}

}
