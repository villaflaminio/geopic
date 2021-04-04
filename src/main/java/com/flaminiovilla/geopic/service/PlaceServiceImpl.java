package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.helper.PlaceHelper;
import com.flaminiovilla.geopic.model.Place;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceHelper placeHelper;

    @Override
    public List<Place> findPlaceByBeginningLetters(String place) {
        Preconditions.checkArgument(!Objects.isNull(place));
        return placeHelper.findByPlaceLetters(place);
    }

    @Override
    public String ReverseGeoCoder(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.latitude));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.longitude));
        return placeHelper.ReverseGeoCoder(structureDTO);
    }

}
