package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.controller.dto.NewsDTO;
import com.flaminiovilla.geopic.model.News;
import com.flaminiovilla.geopic.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/news")
public class NewsCRUDController {

    @Autowired
    private NewsServiceImpl newsService;

//    @PostMapping("/scraper")
//    public void scraper(){
//         newsService.scraper();
//    }
    /**
     * Crea una nuova news
     *{
     *   "description" : "",
     *   "title" : "",
     *   "image" : "",
     *   "date" : "", -> Date e' in formato stringa e deve essere convertito in Obj.Date
     *   "section" : "",
     *}
     * section puo' essere -> ["News","Comunicazioni","Eventi"]
     * @return Categor
     */
    @PostMapping("/create")
    @ResponseBody
    public News create(@RequestBody NewsDTO newsDTO){
        return newsService.create(newsDTO);
    }


    /**
     * Update news
     *
     *{
     *   "id" : ,
     *   "description" : "",
     *   "title" : "",
     *   "image" : "",
     *   "date" : "", -> Date e' in formato stringa e deve essere convertito in Obj.Date
     *   "section" : "",
     *}
     * section puo' essere -> ["News","Comunicazioni","Eventi"]
     * @return Category
     */
    @PutMapping("/update")
    @ResponseBody
    public News update(@RequestBody NewsDTO newsDTO){
        return newsService.update(newsDTO);
    }

    /**
     * delete from id
     * {"id" : }
     * @return Category
     */
    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody NewsDTO newsDTO){
        return newsService.delete(newsDTO);
    }

}
