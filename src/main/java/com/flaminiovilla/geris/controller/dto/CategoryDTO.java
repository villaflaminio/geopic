package com.flaminiovilla.geris.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geris.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

//serve per eliminare i campi null nelle richieste
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO {
    public Long id;
    public String color;
    public String name;

    public CategoryDTO(Category c) {
        this.id = c.getId() ;
        this.name = c.getName();
    }

    public CategoryDTO(Long id , String name) {
        this.name = name;
        this.id = id;
    }
}
