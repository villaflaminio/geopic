package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.CategoryDTO;
import com.flaminiovilla.geopic.helper.CategoryHelper;
import com.flaminiovilla.geopic.model.Category;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryHelper categoryHelper;

    @Override
    public List<Category> findAll() {
        return categoryHelper.findAll();
    }

    @Override
    public String findAllShort() {
        return categoryHelper.findAllShort();
    }

    /**
     * non deve esistere una categoria con lo stesso nome
     * oppure con un colore gia' usato
     *
     * @return Category
     */
    @Override
    public Category create(CategoryDTO categoryDTO) {
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.color));
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.name));
        return categoryHelper.create(categoryDTO);

    }

    @Override
    public Category update(CategoryDTO categoryDTO) {
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.id));
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.color));
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.name));
        return categoryHelper.update(categoryDTO);

    }

    @Override
    public Optional<Category> findById(CategoryDTO categoryDTO) {
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.id));

        return categoryHelper.findById(categoryDTO.id);
    }

    @Override
    public Boolean delete(CategoryDTO categoryDTO) {
        Preconditions.checkArgument(!Objects.isNull(categoryDTO.id));
        return categoryHelper.delete(categoryDTO.id);
    }
}