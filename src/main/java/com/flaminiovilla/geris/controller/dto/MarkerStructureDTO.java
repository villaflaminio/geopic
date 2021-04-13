package com.flaminiovilla.geris.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkerStructureDTO {
//    id,name,latitude, longitude,category[name,id,color]
    public Long id;
    public String name;
    public Double latitude;
    public Double longitude;
    public CategoryDTO category;
}
