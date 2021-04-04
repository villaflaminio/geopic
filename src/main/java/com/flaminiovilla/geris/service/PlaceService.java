package com.flaminiovilla.geris.service;

import com.flaminiovilla.geris.controller.dto.StructureDTO;
import com.flaminiovilla.geris.model.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findPlaceByBeginningLetters(String place);
    String ReverseGeoCoder(StructureDTO structureDTO);
}
