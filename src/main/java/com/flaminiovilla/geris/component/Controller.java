package com.flaminiovilla.geris.component;

import com.flaminiovilla.geris.component.geoHash.StructureDistance;
import com.flaminiovilla.geris.component.geoHash.model.GeoPoint;
import com.flaminiovilla.geris.controller.dto.*;
import com.flaminiovilla.geris.repository.CategoryRepository;
import com.flaminiovilla.geris.repository.PlaceRepository;
import com.flaminiovilla.geris.repository.StructureRepository;
import com.flaminiovilla.geris.component.geocode.ReverseGeoCoderExecute;
import com.flaminiovilla.geris.model.Category;
import com.flaminiovilla.geris.model.Place;
import com.flaminiovilla.geris.model.Structure;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    // collegamento con le repository
    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    ReverseGeoCoderExecute reverseGeoCoderExecute;


    @PostMapping("/test/testCircle")
    @ResponseBody
    public List<StructureDateDTO> circle(@RequestBody StructureDTO structureDTO) {
        List<Structure> structures = structureRepository.findAll();

        List<StructureDateDTO> structureInRange = StructureDistance.isStructureInRange(structureDTO.latitude, structureDTO.longitude, structureDTO.distance*1000 , structures);
        return structureInRange;

    }

    @RequestMapping("/testgeocode")
    public List<String> testgeocode() {
        List<Structure> structures = structureRepository.findAll();
        List<String> ret = new ArrayList<>();
        for (Structure s : structures) {
            ret.add(" " + s.getName() + " --> " + reverseGeoCoderExecute.revgeocode(s.getLatitude(), s.getLongitude()));
        }
        return ret;
    }

    @RequestMapping("/settingsdesctiption")
    public String settingsdesctiption() {
        JSONObject desc = new JSONObject()
                .put("description", "Il Ge.Ri.S. (Gestione Risorse e Salute), consiste nel creare una rete di informazioni utili per i nostri tesserati, che faccia risparmiare danaro, offrendo servizi di natura commerciale e Sanitaria.");
        return desc.toString();
    }

    //METODI DEPRECATED

    /**
     * ok
     *
     * @deprecated new endpoint at http://localhost:8080/structure/findAll
     * @return Structure
     */
    @Deprecated
    @GetMapping("/structures")
    public List<Structure> getStructures() {
        return structureRepository.findAll();
    }

    @GetMapping("/structuresdate")
    public List<StructureDateDTO> getStructuresdate() {
        return StructureDateDTO.listStructureToDTO(structureRepository.findAll());
    }

    /**
     * ok
     *
     * @deprecated new endpoint at http://localhost:8080/place/{initStr}
     */
    //Mi servono i primi 10 nomi di comuni che contengono l'input di ricerca utente
    @GetMapping("/location/{place}")
    public List<Place> getPlaceContains(@PathVariable("place") String place) {
        List<Place> subArr = placeRepository.findByComuneStartsWithOrderByComune(place);

        if (subArr.size() > 10)
            return subArr.subList(0, 10);
        return subArr;

    }

    /**
     * ok
     * restituisco tutte le strutture che sono di una determinata macro categoria
     * e contengono la posizione cercata nell ' indirizzo
     * esempio..
     * macroCategory -> Sanitaria && address -> via .... catanzaro
     * http://localhost:8080/section?section=sanitaria&location=catanzaro
     *
     * @deprecated new endpoint at http://localhost:8080/structure/findSection
     */
    @GetMapping("/section")
    @ResponseBody
    public List<Structure> section(@RequestParam(name = "section") String section, @RequestParam(name = "location") String location) {

        return structureRepository.findByMacroCategoryEqualsAndAddressContains(section, location);
    }

    /**
     * ok
     * data la posizione latitude , longitude dell'utente trovo tutte le strutture che sono
     * ad una distanza di (distance) km rispetto a ( latitude , longitude )
     * http://localhost:8080/citylocation?lat=38.8188265&long=16.6100482&distance=100
     *
     * @param lat      indicates the latitude
     * @param lng      indicates the longitude
     * @param distance indicates the distance between two points
     * @return List<Strucutre>
     * @deprecated new endpoint at http://localhost:8080/structure/findCityLocation
     */
    @GetMapping("/test/citylocation")
    @ResponseBody
    public List<StructureDistanceDTO> citylocation(@RequestParam(name = "lat") Double lat, @RequestParam(name = "long") Double lng, @RequestParam(name = "distance") Double distance) {
        // ottengo il nome della posizione , dopo effettuo il controllo della distanza solo su
        // strutture che hanno la stessa regionei

        // JSONObject jsonString = ReverseGeoCoderUtil.nameCity(latitude, longitude);
        //List<Structure> subArr = structureRepository.findByAddressContains(jsonString.get("name").toString());
        List<Structure> subArr = structureRepository.findAll();

        return Distance.getStructureDistance(lat, lng, distance, subArr);
    }


    /**
     * ok
     * data la posizione per cui si vogliono avere le strutture
     * restituisce una lista di strutture divise per macro categorie
     * http://localhost:8080/sections?location=catanzaro
     *
     * @deprecated new endpoint at http://localhost:8080/structure/findAllByLocation
     */
    @GetMapping("/sections")
    @ResponseBody
    public String sections(@RequestParam(name = "location") String location) {
        JSONArray rett = new JSONArray();

        JSONObject Professionisti = new JSONObject()
                .put("name", " Liberi Professionisti");
        Professionisti.put("structures", structureRepository.findByMacroCategoryEqualsAndAddressContains("Liberi Professionisti", location));

        JSONObject Commerciali = new JSONObject();
        Commerciali.put(" name", "Convenzioni Commerciali");

        Commerciali.put("structures", structureRepository.findByMacroCategoryEqualsAndAddressContains("Convenzioni Commerciali", location));
        JSONObject Sanitari = new JSONObject();

        Sanitari.put("name", " Sanitari");
        Sanitari.put("structures", structureRepository.findByMacroCategoryEqualsAndAddressContains("Sanitaria", location));

        rett.put(Professionisti);
        rett.put(Commerciali);
        rett.put(Sanitari);

        return rett.toString();
    }

    /**
     * ok
     * restituisce un obj di tipo categoria:
     * {id: 1 , name : auto , "color": "#EB4B85"}
     * http://localhost:8080/category
     *
     * @return List<Category>
     * @deprecated new endpoint at http://localhost:8080/category/findAll
     */
    @GetMapping("/category")
    @ResponseBody
    public List<Category> category() {
        return categoryRepository.findAll();
    }

    /**
     * Data la posizione, la distanza di ricerca ed il tipo di categoria
     * restituisce una lista di strutture
     * <p>
     * http://localhost:8080/positionstructures?address=catanzaro&category=auto
     *
     * @return Structure
     * @deprecated new endpoint at http://localhost:8080/structure/findAllByCategory
     */
    @GetMapping("/positionstructures")
    @ResponseBody
    public List<Structure> positionstructures(@RequestParam(name = "address") String address, @RequestParam(name = "category") String category) {
        //prendo tutte le strutture che hanno una determinata categoria
        return structureRepository.findByCategoryNameAndAddressContains(category, address);
    }

    /**
     * Restituisce i marker su tutto il db delle strutture
     * http://localhost:8080/positionstructures?address=catanzaro&category=auto
     * BISOGNA AGGIUNGERE LE RICERCA PER LOCALITA'
     *
     * @return MarkerStructureDTO
     * @deprecated new endpoint at http://localhost:8080/structure/markers
     */
    @GetMapping("/markers")
    @ResponseBody
    public List<MarkerStructureDTO> marker() {
        List<Structure> arr = structureRepository.findAll();
        List<MarkerStructureDTO> structures = new ArrayList<>();

        arr.forEach(elem -> structures.add(MarkerStructureDTO.builder()
                .id(elem.getId())
                .name(elem.getName())
                .latitude(elem.getLatitude())
                .longitude(elem.getLongitude())
                .category(CategoryDTO.builder()
                        .id(elem.getCategory().getId())
                        .color(elem.getCategory().getColor())
                        .name(elem.getCategory().getName())
                        .build())
                .build()));
        return structures;
    }

/*
    servono per riempire il db

        @RequestMapping("/places")
        public List<Place> getPlaces() {
            return placeRepository.findAll();
        }

        @RequestMapping(method = RequestMethod.POST, value = "/addPlace")
        public void addPlace(@RequestBody Place p) {
            placeRepository.save(p);
        }

        @RequestMapping(method = RequestMethod.POST, value = "/addStructure")
        public void addPlace(@RequestBody Structure s) {
            structureRepository.save(s);
        }
    /*

    /*** @return*/

/*
    @GetMapping("/test/hashing")
    @ResponseBody
    public void hashing() {
        List<Place> places = placeRepository.findAll();

        for (Place p : places){
           String hash = StructureDistance.getHash(p.getLatitude(),p.getLongitude());
           p.setPositionHash(hash);
            placeRepository.save(p);
            System.out.println("modifiy "+p+ " hash -> "+ hash);
        }
    }
*/
    @GetMapping("/test/hashing")
    @ResponseBody
    public void hashing() {
        List<Structure> structures = structureRepository.findAll();

        for (Structure s : structures ){
             GeoPoint point = new GeoPoint(s.getLatitude(),s.getLongitude());
             s.setGeoPoint(point);

            structureRepository.save(s);
            System.out.println("modifiy "+s+ " point -> "+ point);
        }
    }

}
