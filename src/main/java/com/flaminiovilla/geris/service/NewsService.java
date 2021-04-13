package com.flaminiovilla.geris.service;

import com.flaminiovilla.geris.controller.dto.NewsDTO;
import com.flaminiovilla.geris.controller.dto.StructureDTO;
import com.flaminiovilla.geris.model.News;

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
