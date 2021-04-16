package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.controller.dto.MarkerStructureDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDateDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDistanceDTO;
import com.flaminiovilla.geopic.service.StructureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/structure")
public class StructureController {

    @Autowired
    private StructureServiceImpl structureService;

    @GetMapping("/findAll")
    List<StructureDateDTO> findAll() {
        return structureService.findAll();
    }

    @PostMapping("/findById")
    StructureDateDTO findById(@RequestBody StructureDTO structureDTO) {
        return structureService.findById(structureDTO);
    }

    /**
     * restituisco tutte le strutture che sono di una determinata macro categoria
     * e contengono la posizione (ESEMPIO catazaro ) all'interno del campo indirizzo
     * dopo / la path e variabili e se presente indica la ricerca
     * esempio..
     * http://localhost:5000/structure/findSection/{ricerca}
     * {
     * "macroCategory" oppure "category" :"Convenzioni Commerciali",
     * "location":"catanzaro"
     * }
     * macroCategory -> Sanitaria && address -> via .... catanzaro
     * http://localhost:8080/section?section=sanitaria&location=catanzaro
     *
     * @return StructureDateDTO
     */
    @PostMapping("/findSection/{search}")
    List<StructureDateDTO> findSection(@PathVariable("search") String search, @RequestBody StructureDTO structureDTO) {
        return structureService.findSectionEPath(search, structureDTO);
    }

    /**
     * restituisco tutte le strutture che sono di una determinata {macro categoria} oppure {categoria}
     * e contengono la posizione (ESEMPIO catazaro ) all'interno del campo indirizzo
     * <p>
     * esempio...
     * http://localhost:5000/structure/findSection
     * {
     * "macroCategory" oppure "category" :"Convenzioni Commerciali",
     * "location":"catanzaro"
     * }
     * macroCategory -> Sanitaria && address -> via .... catanzaro
     * http://localhost:8080/section?section=sanitaria&location=catanzaro
     *
     * @return StructureDateDTO
     */
    @PostMapping("/findSection")
    List<StructureDateDTO> findSection(@RequestBody StructureDTO structureDTO) {
        return structureService.findSection(structureDTO);
    }

    /**
     * {
     *     "location":"catanzaro"
     * }
     * @return StructureDistanceDTO
     * @apiNote structureDTO havo to have latitude, longitude and distance values setted
     */
    @PostMapping("/findCityLocation")
    List<StructureDistanceDTO> findCityLocation(@RequestBody StructureDTO structureDTO) {
        return structureService.findCityLocation(structureDTO);
    }

    /**
     * {
     *     "location":"catanzaro"
     * }
     * @apiNote structureDTO havo to have latitude, longitude and distance values setted
     */
    @PostMapping("/findAllStructuresByLocation")
    String findAllStructuresByLocation(@RequestBody StructureDTO structureDTO) {
        return structureService.findAllStructuresByLocation(structureDTO);
    }

    /**
     * restituisce una lista contenente le strutture presenti in un certo raggio di km
     * ed aggiunge questo parametro nel ritorno della lista.
     {
     "latitude": 38.82072919121749,
     "longitude":16.613545039609015,
     "distance":2000 (espressa in km)
     }
     * @apiNote structureDTO havo to have latitude, longitude and distance values setted
     * @return
     */
    @PostMapping("/findStructuresNearGeoLocation")
    List<StructureDateDTO> findStructuresNearGeoLocation(@RequestBody StructureDTO structureDTO) {
        return structureService.findStructuresNearGeoLocation(structureDTO);
    }

    /**
     * data la posizione per cui
     * si vogliono avere le strutture
     * restituisce una lista di strutture divise per macro categorie
     * {
     *     "category":"Alimentari",
     *     "location":"catanzaro"
     * }
     *
     * @return StructureDateDTO
     */
    @PostMapping("/findAllByCategory")
    List<StructureDateDTO> findAllByCategory(@RequestBody StructureDTO structureDTO) {
        return structureService.findByCategory(structureDTO);
    }

    /**
     * {
     *     "location":"catanzaro"
     * }
     * oppure { }
     * Data la location restituisce i customMarker di quella località
     */
    @PostMapping("/markers")
    @ResponseBody
    public List<MarkerStructureDTO> markers(@RequestBody StructureDTO structureDTO) {
        return structureService.findStructureDTO(structureDTO);
    }

    /**
     * Ricerca dentro il db le strutture che contengono le parole cercate nel nome, data una localita'
     * http://localhost:5000/structure/vo
     * {
     * "location":"catanzaro"
     * }
     *
     * @return StructureDateDTO
     * @apiNote /structure/{search}
     */
    @PostMapping("/search/{search}")
    List<StructureDateDTO> findStructureByContainsLetters(@PathVariable("search") String search, @RequestBody StructureDTO structureDTO) {
        return structureService.findStructureByContainsLetters(search, structureDTO);
    }
}
