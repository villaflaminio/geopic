package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.NewsDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<NewsDTO> search(String search, StructureDTO structureDTO);
    List<NewsDTO> findAllNewsBySection(StructureDTO structureDTO);
    News create(NewsDTO newsDTO);
    News update(NewsDTO newsDTO);
    Optional<News> findById(NewsDTO newsDTO);
    Boolean delete(NewsDTO newsDTO);
}
