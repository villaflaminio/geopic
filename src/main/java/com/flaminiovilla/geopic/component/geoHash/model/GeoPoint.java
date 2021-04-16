
package com.flaminiovilla.geopic.component.geoHash.model;

import java.io.Serializable;

/**
 * {@link GeoPoint} encapsulates coordinates on the earths surface.<br>
 * Coordinate projections might end up using this class...
 */
public class GeoPoint implements Serializable {

	private final double longitude;
	private final double latitude;

	public GeoPoint(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		if (Math.abs(latitude) > 90 || Math.abs(longitude) > 180) {
			throw new IllegalArgumentException("The supplied coordinates " + this + " are out of range.");
		}
	}

	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return String.format("(" + latitude + "," + longitude + ")");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GeoPoint) {
			GeoPoint other = (GeoPoint) obj;
			return latitude == other.latitude && longitude == other.longitude;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 42;
		long latBits = Double.doubleToLongBits(latitude);
		long lonBits = Double.doubleToLongBits(longitude);
		result = 31 * result + (int) (latBits ^ (latBits >>> 32));
		result = 31 * result + (int) (lonBits ^ (lonBits >>> 32));
		return result;
	}
}
