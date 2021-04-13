package com.flaminiovilla.geris.component.geoHash;

import com.flaminiovilla.geris.component.geoHash.model.GeoHash;
import com.flaminiovilla.geris.component.geoHash.utils.GeoHashCircle;
import com.flaminiovilla.geris.component.geoHash.model.GeoPoint;
import com.flaminiovilla.geris.component.geoHash.utils.VincentyGeodesy;
import com.flaminiovilla.geris.controller.dto.StructureDateDTO;
import com.flaminiovilla.geris.model.Structure;

import java.util.ArrayList;
import java.util.List;


public class StructureDistance {

    public static String getHash(double latitude , double longitude){
        GeoHash point = GeoHash.withBitPrecision(latitude, longitude, 64);
        return point.toBinaryString();
    }
    public static double getDistance(GeoPoint a, GeoPoint b){
        return VincentyGeodesy.distanceInMeters(a,b);
    }

    public static List<StructureDateDTO> isStructureInRange(double latitude , double longitude , double distance , List<Structure> structures){
        List<StructureDateDTO> structuresInRange = new ArrayList<>();

        GeoPoint center = new GeoPoint(latitude,longitude);
        GeoHashCircle circle = new GeoHashCircle(center, distance);
        System.out.println("circle" + circle);

        for(Structure struct : structures){
            if(circle.contains(struct.getGeoPoint())) {
                Double distanceKm =   StructureDistance.getDistance(struct.getGeoPoint(), center) / 1000;
                structuresInRange.add(new StructureDateDTO(struct , distanceKm));
            }
        }

        return  structuresInRange;
    }

}
