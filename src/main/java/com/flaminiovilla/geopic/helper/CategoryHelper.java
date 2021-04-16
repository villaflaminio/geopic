package com.flaminiovilla.geopic.helper;

import com.flaminiovilla.geopic.controller.dto.CategoryDTO;
import com.flaminiovilla.geopic.exception.CategoryException;
import com.flaminiovilla.geopic.model.Category;
import com.flaminiovilla.geopic.repository.CategoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryHelper {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByIdAsc();
    }

    public String findAllShort() {
        List<Category> categoriesGet = categoryRepository.findAllByOrderByIdAsc();
        JSONArray categoryShort = new JSONArray();

        for (Category c : categoriesGet) {
            JSONObject jsonSecretary = new JSONObject().put("name", c.getName());
            jsonSecretary.put("id", String.valueOf(c.getId()));
            categoryShort.put(jsonSecretary);
        }
        return categoryShort.toString();
    }

    public Category create(CategoryDTO categoryDTO) {
        if (!categoryRepository.existsByNameOrColorEquals(categoryDTO.name, categoryDTO.color)
                && !categoryDTO.name.equals("") && !categoryDTO.color.equals(""))
            return categoryRepository.save(new Category(categoryDTO.name, categoryDTO.color));
        throw new CategoryException(CategoryException.CategoryExceptionCode.CATEGORY_ALREADY_EXISTS);
    }

    public Category update(CategoryDTO categoryDTO) {
        return categoryRepository.save(new Category(categoryDTO.id, categoryDTO.name, categoryDTO.color));
    }

    public Boolean delete(Long id) {
        if(categoryRepository.existsById(id)) {
            try {
                categoryRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                throw new CategoryException(CategoryException.CategoryExceptionCode.CATEGORY_DELETE_ERROR);
            }
        }
        throw new CategoryException(CategoryException.CategoryExceptionCode.CATEGORY_ID_NOT_EXIST);

    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public boolean existById(Long id){
        return categoryRepository.existsById(id);
    }

}