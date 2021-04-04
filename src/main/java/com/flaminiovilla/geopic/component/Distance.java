package com.flaminiovilla.geopic.component;

import com.flaminiovilla.geopic.controller.dto.StructureDistanceDTO;
import com.flaminiovilla.geopic.model.Structure;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * per calcolare la distanza date due coordinate geografiche
 * grazie al metodo riportato di sotto si ha una precisione del 84%
 * Vengono sfruttate funzioni non elementari che tendono a rallentare
 * l'esecuzione , per questo motivo si e' passati ad usare l'algoritmo di
 * Vincenty Geodesy
 */
@Component
@Deprecated
public class Distance {
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }

    public static List<StructureDistanceDTO> getStructureDistance(double lat1, double lon1, Double range, List<Structure> structuresNear) {

        List<StructureDistanceDTO> structuresRange = new ArrayList<>();

        for(Structure s : structuresNear) {
            double lat2 = s.getLatitude();
            double lon2 = s.getLongitude();
            double distance = distance(lat1,lon1,lat2,lon2);

            if(distance <= range){
                structuresRange.add(new StructureDistanceDTO(s , distance));
                System.out.println(distance);
            }
        }

        return structuresRange;
    }


}