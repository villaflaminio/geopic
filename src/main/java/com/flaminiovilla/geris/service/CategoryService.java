package com.flaminiovilla.geris.service;

import com.flaminiovilla.geris.controller.dto.CategoryDTO;
import com.flaminiovilla.geris.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    String findAllShort();
    Category create(CategoryDTO categoryDTO);
    Category update(CategoryDTO categoryDTO);
    Boolean delete(CategoryDTO categoryDTO);
    Optional<Category> findById(CategoryDTO categoryDTO);
}
