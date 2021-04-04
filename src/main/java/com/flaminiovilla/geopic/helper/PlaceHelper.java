package com.flaminiovilla.geopic.helper;

import com.flaminiovilla.geopic.component.geocode.ReverseGeoCoderExecute;
import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.model.Place;
import com.flaminiovilla.geopic.repository.PlaceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceHelper {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ReverseGeoCoderExecute reverseGeoCoderExecute;

    public List<Place> findByPlaceLetters(String place) {

        List<Place> tempList = placeRepository.findByComuneStartsWithOrderByComune(place);
        if (tempList.size() > 10) {
            return tempList.subList(0, 10);
        }
        return tempList;
    }

    public String ReverseGeoCoder(StructureDTO structureDTO) {
        String location  =  reverseGeoCoderExecute.revgeocode(structureDTO.latitude, structureDTO.longitude);
        JSONObject jsonString = new JSONObject()
                .put("name",  location);
        return jsonString.toString();

    }
}
