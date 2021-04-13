package com.flaminiovilla.geris.helper;

import com.flaminiovilla.geris.component.Distance;
import com.flaminiovilla.geris.component.geoHash.StructureDistance;
import com.flaminiovilla.geris.controller.dto.*;
import com.flaminiovilla.geris.exception.ReferralPersonException;
import com.flaminiovilla.geris.exception.StructureException;
import com.flaminiovilla.geris.model.Region;
import com.flaminiovilla.geris.model.Structure;
import com.flaminiovilla.geris.repository.RegionRepository;
import com.flaminiovilla.geris.repository.StructureRepository;
import com.flaminiovilla.geris.security.repository.AuthorityRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class StructureHelper implements Serializable {

    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private Distance distance;
    @Autowired
    private StructureCheck structureCheck;
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private ReferralPersonHelper referralPersonHelper;
    @Autowired
    private SecretaryHelper secretaryHelper;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private AuthorityRepository authorityRepository;



    public List<StructureDateDTO> findAll() {
        return StructureDateDTO.listStructureToDTO(structureRepository.findAll());
    }

    public StructureDateDTO findById(StructureDTO structureDTO) {
        Optional<Structure> ret = structureRepository.findById(structureDTO.id);
        if (ret.isPresent())
            return new StructureDateDTO(ret.get());
        return null;
    }

    public List<StructureDateDTO> findSectionByMacroCategoryAndAddress(StructureDTO structureDTO) {
        List<Structure> structures;

        if (structureDTO.location.trim().equalsIgnoreCase("ovunque"))
            structures = structureRepository.findByMacroCategoryEquals(structureDTO.macroCategory);
        else
            structures = structureRepository.findByMacroCategoryEqualsAndAddressContains(structureDTO.macroCategory, structureDTO.location);
        return StructureDateDTO.listStructureToDTO(structures);

    }

    public List<StructureDateDTO> findByCategoryNameEAddress(StructureDTO structureDTO) {
        List<Structure> structures;

        if (structureDTO.location.trim().equalsIgnoreCase("ovunque"))
            structures = structureRepository.findByCategoryName(structureDTO.category);
        else
            structures = structureRepository.findByCategoryNameAndAddressContains(structureDTO.category, structureDTO.location);
        return StructureDateDTO.listStructureToDTO(structures);

    }

    public List<StructureDateDTO> findMacroCategoryEPathVariable(String search, StructureDTO structureDTO) {
        List<Structure> structures;

        if (structureDTO.location.trim().equalsIgnoreCase("ovunque"))
            structures = structureRepository.findByNameContainsAndMacroCategoryEquals(search, structureDTO.macroCategory);
        else
            structures = structureRepository.findByNameContainsAndMacroCategoryEqualsAndAddressContains(search, structureDTO.macroCategory, structureDTO.location);

        return StructureDateDTO.listStructureToDTO(structures);

    }

    public List<StructureDateDTO> findCategoryEPathVariable(String search, StructureDTO structureDTO) {
        List<Structure> structures;

        if (structureDTO.location.trim().equalsIgnoreCase("ovunque"))
            structures = structureRepository.findByNameContainsAndCategoryName(search, structureDTO.category);
        else
            structures = structureRepository.findByNameContainsAndCategoryNameAndAddressContains(search, structureDTO.category, structureDTO.location);
        return StructureDateDTO.listStructureToDTO(structures);

    }

    public List<StructureDistanceDTO> findCityLocation(StructureDTO structureDTO) {
        return distance.getStructureDistance(structureDTO.latitude, structureDTO.longitude, structureDTO.distance, structureRepository.findAll());

    }

    public List<MarkerStructureDTO> findStructureDTO(StructureDTO structureDTO) {
        List<Structure> arr = structureRepository.findByAddressContains(structureDTO.location);

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

    public List<MarkerStructureDTO> findStructureDTOEmpty() {
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

    public List<StructureDateDTO> findStructuresNearGeoLocation(StructureDTO structureDTO) {
        List<Structure> structures = structureRepository.findAll();

        List<StructureDateDTO> structureInRange = StructureDistance.isStructureInRange(structureDTO.latitude, structureDTO.longitude, structureDTO.distance*1000 , structures);
        return structureInRange;
    }

    public String findAllStructuresByLocation(StructureDTO structureDTO) {
        JSONArray ret = new JSONArray();

        JSONObject Professionisti = new JSONObject().put("name", "Liberi Professionisti");
        Professionisti.put("structures", StructureDateDTO.listStructureToDTO(structureRepository.findByMacroCategoryEqualsAndAddressContains("Liberi Professionisti", structureDTO.location)));

        JSONObject Commerciali = new JSONObject().put("name", "Convenzioni Commerciali");
        Commerciali.put("structures", StructureDateDTO.listStructureToDTO(structureRepository.findByMacroCategoryEqualsAndAddressContains("Convenzioni Commerciali", structureDTO.location)));

        JSONObject Sanitari = new JSONObject().put("name", "Sanitaria");
        Sanitari.put("structures", StructureDateDTO.listStructureToDTO(structureRepository.findByMacroCategoryEqualsAndAddressContains("Sanitaria", structureDTO.location)));

        ret.put(Professionisti);
        ret.put(Commerciali);
        ret.put(Sanitari);

        return ret.toString();
    }
    public String findAllStructures(StructureDTO structureDTO) {
        JSONArray ret = new JSONArray();

        JSONObject Professionisti = new JSONObject().put("name", "Liberi Professionisti");
        Professionisti.put("structures", StructureDateDTO.listStructureToDTO(structureRepository.findByMacroCategoryEquals("Liberi Professionisti")));

        JSONObject Commerciali = new JSONObject().put("name", "Convenzioni Commerciali");
        Commerciali.put("structures", StructureDateDTO.listStructureToDTO(structureRepository.findByMacroCategoryEquals("Convenzioni Commerciali")));

        JSONObject Sanitari = new JSONObject().put("name", "Sanitaria");
        Sanitari.put("structures", StructureDateDTO.listStructureToDTO(structureRepository.findByMacroCategoryEquals("Sanitaria")));

        ret.put(Professionisti);
        ret.put(Commerciali);
        ret.put(Sanitari);

        return ret.toString();
    }


    public List<Structure> findStructureByContainsLetters(String search) {
        List<Structure> tempList = structureRepository.findByNameContains(search);
        if (tempList.size() > 10) {
            return tempList.subList(0, 10);
        }
        return tempList;
    }

    public List<Structure> findByCategoryNameAndNameContains(String search, StructureDTO structureDTO) {
        List<Structure> tempList = structureRepository.findByCategoryNameAndNameContains(structureDTO.category, search);
        if (tempList.size() > 10) {
            return tempList.subList(0, 10);
        }
        return tempList;
    }

    public List<StructureDateDTO> findStructureByNameELocation(String search, StructureDTO structureDTO) {
        List<Structure> structures;

        if (structureDTO.location.trim().equalsIgnoreCase("ovunque"))
            structures = structureRepository.findByNameContains(search);
        else
            structures = structureRepository.findByNameContainsAndAddressContains(search, structureDTO.location);
        return StructureDateDTO.listStructureToDTO(structures);
    }

    public Structure create(StructureDTO structureDTO) {
        if (structureCheck.structureValidating(structureDTO)) {
            String expireDateConventionString = structureDTO.expireDateConvention;
            Date expireDateConvention;
            try {
                expireDateConvention = new SimpleDateFormat("dd/MM/yyyy").parse(expireDateConventionString);
            } catch (ParseException e) {
                throw new StructureException(StructureException.structureExceptionCode.EXPIRE_DATA_CONVENTION_WRONG);
            }

            return structureRepository.save(Structure.builder()
                    .name(structureDTO.name)
                    .macroCategory(structureDTO.macroCategory)
                    .description(structureDTO.description)
                    .logo(structureDTO.logo)
                    .discount(structureDTO.discount)
                    .expireDateConvention(expireDateConvention)
                    .email(structureDTO.email)
                    .phone(structureDTO.phone)
                    .website(structureDTO.website)
                    .address(structureDTO.address)
                    .latitude(structureDTO.latitude)
                    .longitude(structureDTO.longitude)
                    .secretary(secretaryHelper.findById(structureDTO.secretaryId).get())
                    .referralPerson(referralPersonHelper.findById(structureDTO.referralPersonId).get())
                    .category(categoryHelper.findById(structureDTO.categoryId).get())
                    .region(getRegion(structureDTO))
                    .build());
        } else {
            throw new StructureException(StructureException.structureExceptionCode.ILLEGAL_ARGUMENT);
        }
    }

    public Structure update(StructureDTO structureDTO) {
        if (structureCheck.structureUpdateValidating(structureDTO)) {
            String expireDateConventionString = structureDTO.expireDateConvention;
            Date expireDateConvention;
            try {
                expireDateConvention = new SimpleDateFormat("dd/MM/yyyy").parse(expireDateConventionString);
            } catch (ParseException e) {
                throw new StructureException(StructureException.structureExceptionCode.EXPIRE_DATA_CONVENTION_WRONG);
            }

            return structureRepository.save(Structure.builder()
                    .id(structureDTO.id)
                    .name(structureDTO.name)
                    .macroCategory(structureDTO.macroCategory)
                    .description(structureDTO.description)
                    .logo(structureDTO.logo)
                    .discount(structureDTO.discount)
                    .expireDateConvention(expireDateConvention)
                    .email(structureDTO.email)
                    .phone(structureDTO.phone)
                    .website(structureDTO.website)
                    .address(structureDTO.address)
                    .latitude(structureDTO.latitude)
                    .longitude(structureDTO.longitude)
                    .secretary(secretaryHelper.findById(structureDTO.secretaryId).get())
                    .referralPerson(referralPersonHelper.findById(structureDTO.referralPersonId).get())
                    .category(categoryHelper.findById(structureDTO.categoryId).get())
                    .region(getRegion(structureDTO))
                    .build());
        } else {
            throw new StructureException(StructureException.structureExceptionCode.ILLEGAL_ARGUMENT);
        }
    }

    public Boolean delete(StructureDTO structureDTO) {
        if(deletePermit(structureDTO)) {
            try {
                structureRepository.deleteById(structureDTO.id);
                return true;
            } catch (Exception e) {
                throw new StructureException(StructureException.structureExceptionCode.STRUCTURE_DELETE_ERROR);
            }
        }
        throw new StructureException(StructureException.structureExceptionCode.ACTION_NOT_PERMITTED);

    }

    public Region getRegion(StructureDTO structureDTO) {
        //se ha come id di regione 99 si tratta di un admin, quindi vado a prendere la regione specificata
        if (structureDTO.regionId != null && structureDTO.user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN"))) {
            Optional<Region> regionToAdd = regionRepository.findById(structureDTO.regionId);
            if (regionToAdd.isPresent()) {
                return regionToAdd.get();
            } else
                throw new ReferralPersonException(ReferralPersonException.referralPersonExceptionCode.ADD_TO_REGION_FAILED);
        } else { // altrimenti se non si tratta di admin vado a prendere la stessa regione dell'utente loggato
            Optional<Region> find = regionRepository.findById(structureDTO.user.getRegion().getId());
            if (find.isPresent())
                return find.get();
        }
        return null;
    }

    public boolean deletePermit(StructureDTO structureDTO) {
        @NotNull Optional<Structure> get = structureRepository.findById(structureDTO.id);
        if (get.isPresent()) {
            if (structureDTO.user.getRegion().getId().equals(get.get().getRegion().getId())) {
                //una secretary puo' cancellare solo una referralPerson che ha lo stessa regione
                return true;
            } else if (structureDTO.user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN"))) { // altrimenti
                //se si tratta di admin puo' cancellare senza limiti
                return true;
            }
        }
        return false;
    }
}