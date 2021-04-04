package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.model.Place;
import com.flaminiovilla.geopic.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceServiceImpl placeService;


    /**
     *Aiuto al completamento del nome della località , mostra solo i primi 10
    **/
    @GetMapping("/{initStr}")
    List<Place> findPlaceByNameBeginning(@PathVariable("initStr") String initialString){
        return placeService.findPlaceByBeginningLetters(initialString);
    }

    /**
     * data latitude e longitude
     * mi restituisce { name : catanzaro}
     * {"latitude" : , "longiitude" :  }
     *
     */
    @PostMapping("/cityname")
    public String cityname(@RequestBody StructureDTO structureDTO) {
        return placeService.ReverseGeoCoder(structureDTO);


    }
}
