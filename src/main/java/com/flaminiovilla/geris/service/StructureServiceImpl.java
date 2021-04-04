package com.flaminiovilla.geris.service;

import com.flaminiovilla.geris.controller.dto.MarkerStructureDTO;
import com.flaminiovilla.geris.controller.dto.StructureDTO;
import com.flaminiovilla.geris.controller.dto.StructureDateDTO;
import com.flaminiovilla.geris.controller.dto.StructureDistanceDTO;
import com.flaminiovilla.geris.helper.StructureHelper;
import com.flaminiovilla.geris.model.Structure;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StructureServiceImpl implements StructureService {

    @Autowired
    private StructureHelper structureHelper;

    @Override
    public List<StructureDateDTO> findAll() {
        return structureHelper.findAll();
    }

    @Override
    public StructureDateDTO findById(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.id));

        return structureHelper.findById(structureDTO);
    }

    /*
        Se ricevo una category devo cercare tra le category altrimenti devo verificare che sia una macroCategory
     */
    @Override
    public List<StructureDateDTO> findSection(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.location));
        if (!Objects.isNull(structureDTO.category))
            return structureHelper.findByCategoryNameEAddress(structureDTO);
        else {
            Preconditions.checkArgument(!Objects.isNull(structureDTO.macroCategory));
            return structureHelper.findSectionByMacroCategoryAndAddress(structureDTO);
        }
    }

    @Override
    public List<StructureDateDTO> findSectionEPath(String search, StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.location));

        if (!Objects.isNull(structureDTO.macroCategory) && !Objects.isNull(search))
            return structureHelper.findMacroCategoryEPathVariable(search, structureDTO);
        else {
            Preconditions.checkArgument(!Objects.isNull(structureDTO.category));
            return structureHelper.findCategoryEPathVariable(search, structureDTO);
        }
    }

    @Override
    public List<StructureDistanceDTO> findCityLocation(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.latitude));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.longitude));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.distance));
        return structureHelper.findCityLocation(structureDTO);
    }

    @Override
    public String findAllStructuresByLocation(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.location));
        if(structureDTO.location.trim().equalsIgnoreCase("ovunque"))
            return structureHelper.findAllStructures(structureDTO);
        else
            return structureHelper.findAllStructuresByLocation(structureDTO);
    }

    public List<StructureDateDTO> findStructuresNearGeoLocation(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.distance));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.latitude));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.longitude));
        return structureHelper.findStructuresNearGeoLocation(structureDTO);
    }

    @Override
    public List<MarkerStructureDTO> findStructureDTO(StructureDTO structureDTO) {
        if (!Objects.isNull(structureDTO.location)) {
            return structureHelper.findStructureDTO(structureDTO);
        } else {
            return structureHelper.findStructureDTOEmpty();
        }
    }

    @Override
    public List<StructureDateDTO> findByCategory(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.category));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.location));
        return structureHelper.findByCategoryNameEAddress(structureDTO);
    }

    @Override
    public List<StructureDateDTO> findStructureByContainsLetters(String search, StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.location));
        Preconditions.checkArgument(!Objects.isNull(search));
        return structureHelper.findStructureByNameELocation(search,structureDTO);

    }
    //------------------CRUD------------------------//
    /**
     * Crea un nuovo obj di tipo structure
     *{
     *    "name" puo' essere duplicato
     *    "macroCategory" : deve appartenere a ["Liberi Professionisti","Convenzioni Commerciali","Sanitaria"]
     *    "expireDateConvention" : Arriva in formato string ,deve essere convertita in obj Data,
     *    "email" : deve essere corretta
     *    Bisogna controllare che l'id esista, poi prendere la category con getById(id)
     *    "categoryId" : ,
     *    "referralPersonId" : ,
     *    "secretaryId" : ,
     *}
     * @return Category
     */
    @Override
    public Structure create(StructureDTO structureDTO) {
        preconditionCreateUpdate(structureDTO);
        return structureHelper.create(structureDTO);
    }

    /**
     *  Modifica la structure, deve rispettare tutte le condizioni di:
     *  Structure create(StructureDTO structureDTO)
     *  -Bisogna controllare che id non sia null
     * @return Category
     */
    @Override
    public Structure update(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.id));
        preconditionCreateUpdate(structureDTO);
        return structureHelper.update(structureDTO);

    }

    @Override
    public Boolean delete(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.id));
        return structureHelper.delete(structureDTO);
    }

    private void preconditionCreateUpdate(StructureDTO structureDTO){
        Preconditions.checkArgument(!Objects.isNull(structureDTO.name));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.macroCategory));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.description));

        //devo controllare che sia https e prenderla da google , o accettare un link e poi controllare
        Preconditions.checkArgument(!Objects.isNull(structureDTO.logo));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.discount));

        Preconditions.checkArgument(!Objects.isNull(structureDTO.expireDateConvention));//in formato stringa

        //la mail deve esistere
        Preconditions.checkArgument(!Objects.isNull(structureDTO.email));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.phone));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.website));

        Preconditions.checkArgument(!Objects.isNull(structureDTO.address));

        //Bisogna controllare che non siano gia' usate
        Preconditions.checkArgument(!Objects.isNull(structureDTO.latitude));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.longitude));

        //devo controllare se esistono
        Preconditions.checkArgument(!Objects.isNull(structureDTO.categoryId));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.referralPersonId));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.secretaryId));
        Preconditions.checkArgument(!Objects.isNull(structureDTO.regionId));

        Preconditions.checkArgument(!Objects.isNull(structureDTO.user));
    }
}