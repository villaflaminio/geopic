

package com.flaminiovilla.geris.component.geocode;

import com.flaminiovilla.geris.component.geocode.kdtree.KDTree;
import com.flaminiovilla.geris.model.Place;

import java.util.ArrayList;
import java.util.List;

public class ReverseGeoCode {
    KDTree<GeoName> kdTree;

    //constructor
    public ReverseGeoCode(List<Place> places) {
        createKdTree(places);
    }

    //Converto tutti i places in noti del tree
    private void createKdTree(List<Place> places) {
        ArrayList<GeoName> arPlaceNames = new ArrayList<GeoName>();

        for(Place p : places){
            GeoName newPlace = new GeoName(p);
            arPlaceNames.add(newPlace);

        }
        kdTree = new KDTree<GeoName>(arPlaceNames);
    }

    public GeoName nearestPlace(double latitude, double longitude) {
        return kdTree.findNearest(new GeoName(latitude,longitude));
    }
}
