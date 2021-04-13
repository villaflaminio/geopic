package com.flaminiovilla.geris.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geris.model.Category;
import com.flaminiovilla.geris.model.ReferralPerson;
import com.flaminiovilla.geris.model.Secretary;
import com.flaminiovilla.geris.model.Structure;
import lombok.Data;

import java.util.Date;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StructureDistanceDTO {
    private Long id;
    private String name;
    private String macroCategory;
    private Category category;
    private String description;
    private String logo;
    private Integer discount;
    private Date expireDateConvention;
    //contatti
    private String email;
    private String phone;
    private String website;
    //posizione
    private String address;
    private Double latitude;
    private Double longitude;
    private Secretary secretary;
    private ReferralPerson referralPerson;
    private Double distance;

    public StructureDistanceDTO(Structure s, Double distance) {
        this.id = s.getId();
        this.name = s.getName();
        this.macroCategory = s.getMacroCategory();
        this.category = s.getCategory();
        this.description = s.getDescription();
        this.logo =s.getLogo();
        this.discount = s.getDiscount();
        this.expireDateConvention = s.getExpireDateConvention();
        this.email = s.getEmail();
        this.phone = s.getPhone();
        this.website = s.getWebsite();
        this.address = s.getAddress();
        this.latitude = s.getLatitude();
        this.longitude = s.getLongitude();
        this.secretary = s.getSecretary();
        this.referralPerson = s.getReferralPerson();
        this.distance = distance;
    }
}
