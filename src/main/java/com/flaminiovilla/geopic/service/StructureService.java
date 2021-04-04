package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.MarkerStructureDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDateDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDistanceDTO;
import com.flaminiovilla.geopic.model.Structure;

import java.util.List;

public interface StructureService {
    List<StructureDateDTO> findAll();
    List<StructureDistanceDTO> findCityLocation(StructureDTO structureDTO);
    String findAllStructuresByLocation(StructureDTO structureDTO);
    List<StructureDateDTO> findByCategory(StructureDTO structureDTO);
    List<MarkerStructureDTO> findStructureDTO(StructureDTO structureDTO);
    StructureDateDTO findById(StructureDTO structureDTO);
    List<StructureDateDTO> findSection(StructureDTO structureDTO);
    List<StructureDateDTO> findSectionEPath(String search, StructureDTO structureDTO);
    List<StructureDateDTO> findStructureByContainsLetters(String search, StructureDTO structureDTO);
    //crud
    Structure create(StructureDTO structureDTO);
    Structure update(StructureDTO structureDTO);
    Boolean delete(StructureDTO structureDTO);
}
