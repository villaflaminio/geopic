
package com.flaminiovilla.geris.component.geocode;

import com.flaminiovilla.geris.component.geocode.kdtree.KDNodeComparator;
import com.flaminiovilla.geris.model.Place;

import java.util.Comparator;

import static java.lang.Math.*;


public class GeoName extends KDNodeComparator<GeoName> {
    public String name;
    public double latitude;
    public double longitude;
    public double point[] = new double[3]; // The 3D coordinates of the point

    /**
     * Costruttore per creare l'albero dai Place
     */
    GeoName(Place place) {
        name = place.getComune();
        latitude = place.getLatitude();
        longitude = place.getLongitude();
        setPoint();
    }

    /**
     * Costruttore per creare il nodo da ricercare
     */
    GeoName(Double latitude, Double longitude) {
        name = "Search";
        this.latitude = latitude;
        this.longitude = longitude;
        setPoint();
    }

    private void setPoint() {
        point[0] = cos(toRadians(latitude)) * cos(toRadians(longitude));
        point[1] = cos(toRadians(latitude)) * sin(toRadians(longitude));
        point[2] = sin(toRadians(latitude));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    protected double squaredDistance(GeoName other) {
        double x = this.point[0] - other.point[0];
        double y = this.point[1] - other.point[1];
        double z = this.point[2] - other.point[2];
        return (x*x) + (y*y) + (z*z);
    }

    @Override
    protected double axisSquaredDistance(GeoName other, int axis) {
        double distance = point[axis] - other.point[axis];
        return distance * distance;
    }

    @Override
    protected Comparator<GeoName> getComparator(int axis) {
        return GeoNameComparator.values()[axis];
    }

    protected static enum GeoNameComparator implements Comparator<GeoName> {
        x {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[0], b.point[0]);
            }
        },
        y {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[1], b.point[1]);
            }
        },
        z {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[2], b.point[2]);
            }
        };
    }
}
