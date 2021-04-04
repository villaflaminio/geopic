package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.model.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findPlaceByBeginningLetters(String place);
    String ReverseGeoCoder(StructureDTO structureDTO);
}
