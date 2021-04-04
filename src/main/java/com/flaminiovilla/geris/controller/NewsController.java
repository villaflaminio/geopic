package com.flaminiovilla.geris.controller;

import com.flaminiovilla.geris.controller.dto.NewsDTO;
import com.flaminiovilla.geris.controller.dto.StructureDTO;
import com.flaminiovilla.geris.model.News;
import com.flaminiovilla.geris.service.NewsServiceImpl;
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
     * restituisce tutte le news che contengono la stringa di ricerca nel titlo,
     * se non trovo niente cerco nella descriprion
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
