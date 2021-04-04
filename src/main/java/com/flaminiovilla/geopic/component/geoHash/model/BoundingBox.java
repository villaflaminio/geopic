
package com.flaminiovilla.geopic.component.geoHash.model;

import java.io.Serializable;

public class BoundingBox implements Serializable {
	private static final long serialVersionUID = -7145192134410261076L;
	private double southLatitude;
	private double northLatitude;
	private double westLongitude;
	private double eastLongitude;
	private boolean intersects180Meridian;

	/** ------------
	 * create a bounding box defined by two coordinates
	 */
	public BoundingBox(GeoPoint southWestCorner, GeoPoint northEastCorner) {
		this(southWestCorner.getLatitude(), northEastCorner.getLatitude(), southWestCorner.getLongitude(), northEastCorner.getLongitude());
	}

	/**
	 * Create a bounding box with the specified latitudes and longitudes. This constructor takes the order of the points into account.
	 *
	 * @param northLatitude
	 * @param southLatitude
	 * @param westLongitude
	 * @param eastLongitude
	 *
	 * @throws IllegalArgumentException
	 *             When the defined BoundingBox would go over one of the poles. This kind of box is not supported.
	 */
	public BoundingBox(double southLatitude, double northLatitude, double westLongitude, double eastLongitude) {
		if (southLatitude > northLatitude)
			throw new IllegalArgumentException("The southLatitude must not be greater than the northLatitude");

		if (Math.abs(southLatitude) > 90 || Math.abs(northLatitude) > 90 || Math.abs(westLongitude) > 180 || Math.abs(eastLongitude) > 180) {
			throw new IllegalArgumentException("The supplied coordinates are out of range.");
		}

		this.northLatitude = northLatitude;
		this.westLongitude = westLongitude;

		this.southLatitude = southLatitude;
		this.eastLongitude = eastLongitude;

		intersects180Meridian = eastLongitude < westLongitude;
	}

	/**
	 * Clone constructor
	 *
	 * @param that
	 */
	public BoundingBox(BoundingBox that) {
		this(that.southLatitude, that.northLatitude, that.westLongitude, that.eastLongitude);
	}

	/**
	 * Returns the NorthWestCorner of this BoundingBox as a new Point.
	 *
	 * @return
	 */
	public GeoPoint getNorthWestCorner() {
		return new GeoPoint(northLatitude, westLongitude);
	}


	/**
	 * Returns the SouthEastCorner of this BoundingBox as a new Point.
	 *
	 * @return
	 */
	public GeoPoint getSouthEastCorner() {
		return new GeoPoint(southLatitude, eastLongitude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof BoundingBox) {
			BoundingBox that = (BoundingBox) obj;
			return southLatitude == that.southLatitude && westLongitude == that.westLongitude && northLatitude == that.northLatitude && eastLongitude == that.eastLongitude;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + hashCode(southLatitude);
		result = 37 * result + hashCode(northLatitude);
		result = 37 * result + hashCode(westLongitude);
		result = 37 * result + hashCode(eastLongitude);
		return result;
	}

	private static int hashCode(double x) {
		long f = Double.doubleToLongBits(x);
		return (int) (f ^ (f >>> 32));
	}

	public boolean contains(GeoPoint point) {
		return containsLatitude(point.getLatitude()) && containsLongitude(point.getLongitude());
	}


	@Override
	public String toString() {
		return getNorthWestCorner() + " -> " + getSouthEastCorner();
	}

	public GeoPoint getCenter() {
		double centerLatitude = (southLatitude + northLatitude) / 2;
		double centerLongitude = (westLongitude + eastLongitude) / 2;

		// This can happen if the bBox crosses the 180-Meridian
		if (centerLongitude > 180)
			centerLongitude -= 360;

		return new GeoPoint(centerLatitude, centerLongitude);
	}

	private boolean containsLatitude(double latitude) {
		return latitude >= southLatitude && latitude <= northLatitude;
	}

	private boolean containsLongitude(double longitude) {
		if (intersects180Meridian) {
			return longitude <= eastLongitude || longitude >= westLongitude;
		} else {
			return longitude >= westLongitude && longitude <= eastLongitude;
		}
	}

}
