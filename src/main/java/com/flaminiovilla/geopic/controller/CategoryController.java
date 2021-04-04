package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.model.Category;
import com.flaminiovilla.geopic.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;
    /**
     * restituisce un obj di tipo categoria:
     * { "id": 1 , "name" : "auto" , "color": "#EB4B85"}
     * @return List<Category>
     */
    @GetMapping("/findAll")
    List<Category> findAll(){
        return categoryService.findAll();
    }

}
