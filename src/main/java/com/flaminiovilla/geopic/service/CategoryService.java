package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.CategoryDTO;
import com.flaminiovilla.geopic.model.Category;

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
