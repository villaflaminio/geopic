package com.flaminiovilla.geris.controller;

import com.flaminiovilla.geris.controller.dto.CategoryDTO;
import com.flaminiovilla.geris.model.Category;
import com.flaminiovilla.geris.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryCRUDController {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * restituisce un obj di tipo categoria:
     * { "id": 1 , "name" : "auto"}
     * @return List<Category>
     */
    @GetMapping("/findAllShort")
    String findAllShort(){
        return categoryService.findAllShort();
    }

    /**
     * Dati nome e colore creo un nuovo obj, se l'inserimento riesce
     * restituisco il nuovo obj
     *{
     *   "color" : "",
     *   "name" : ""
     *}
     * @return Category
     */
    @PostMapping("/create")
    @ResponseBody
    public Category create(@RequestBody CategoryDTO categoryDTO){
        return categoryService.create(categoryDTO);
    }

    /**
     * Dati nome e colore creo un nuovo obj, se l'inserimento riesce
     * restituisco il nuovo obj
     * {
     *   "id" : ,
     *   "color" : "",
     *   "name" : ""
     * }
     * @return Category
     */
    @PutMapping("/update")
    @ResponseBody
    public Category update(@RequestBody CategoryDTO categoryDTO){
        return categoryService.update(categoryDTO);
    }

    /**
     * search from id:
     * {"id" : }
     *
     * @return Category
     * oppure restituisce null se non trova niente
     */
    @PostMapping("/findById")
    Optional<Category> findById(@RequestBody CategoryDTO categoryDTO){
        return categoryService.findById(categoryDTO);
    }

    /**
     * delete from id:
     * {"id" : }
     * @return boolean
     */
    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody CategoryDTO categoryDTO){
        return categoryService.delete(categoryDTO);
    }

}
