package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.controller.dto.NewsDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.model.News;
import com.flaminiovilla.geopic.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService;


    /**
     * restituisce tutte le news che contengono la stringa di ricerca nel titolo,
     * se non trovo niente cerco {search} all'interno della description.
     * @return List<NewsDTO>
     * se null restituisce []
     */
    @PostMapping("/search/{search}")
    List<NewsDTO> search(@PathVariable("search") String search, @RequestBody StructureDTO structureDTO){
        return newsService.search(search,structureDTO);
    }

    /**
     * restituisce tutte le News di una section
     * @return List<Category>
     * se null restituisce []
     */
    @PostMapping("/section")
    List<NewsDTO> section(@RequestBody StructureDTO structureDTO){
        return newsService.findAllNewsBySection(structureDTO);
    }

    /**
     * search from id:
     * {"id" : }

     * @return News
     */
    @PostMapping("/findById")
    Optional<News> findById(@RequestBody NewsDTO newsDTO){
        return newsService.findById(newsDTO);
    }

}
