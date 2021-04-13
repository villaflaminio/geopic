package com.flaminiovilla.geris.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geris.security.model.User;


@JsonIgnoreProperties(ignoreUnknown = true)
public class StructureDTO {
    public String section;
    public String location;
    public Double latitude;
    public Double longitude;
    public Double distance;
    public String category;
    public String macroCategory;
    public Long id;
    public String name;
    public String expireDateConvention;
    public String description;
    public String logo;
    public Integer discount;
    public String email;
    public String phone;
    public String website;
    public String address;
    public User user;

    public Long regionId;

    public Long categoryId;
    public Long referralPersonId;
    public Long secretaryId;

}