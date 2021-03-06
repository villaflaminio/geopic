package com.flaminiovilla.geopic.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geopic.component.Utils;
import com.flaminiovilla.geopic.component.geoHash.model.GeoPoint;
import com.flaminiovilla.geopic.model.Category;
import com.flaminiovilla.geopic.model.ReferralPerson;
import com.flaminiovilla.geopic.model.Secretary;
import com.flaminiovilla.geopic.model.Structure;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StructureDateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String macroCategory;
    private Category category;
    private String description;
    private String logo;
    private Integer discount;
    private String expireDateConvention;
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
    private GeoPoint geoPoint;

    public StructureDateDTO(Structure s) {
        this.id = s.getId();
        this.name = s.getName();
        this.macroCategory = s.getMacroCategory();
        this.category = s.getCategory();
        this.description = s.getDescription();
        this.logo =s.getLogo();
        this.discount = s.getDiscount();

        Date dateTime = s.getExpireDateConvention();
        if(dateTime != null)
            this.expireDateConvention = Utils.dateToStringFormatted(dateTime);
        this.email = s.getEmail();
        this.phone = s.getPhone();
        this.website = s.getWebsite();
        this.address = s.getAddress();
        this.latitude = s.getLatitude();
        this.longitude = s.getLongitude();
        this.secretary = s.getSecretary();
        this.referralPerson = s.getReferralPerson();
        this.geoPoint = s.getGeoPoint();
    }

    public StructureDateDTO(Structure s , Double distance) {
        this(s);
        this.distance = distance;
    }

    public static List<StructureDateDTO> listStructureToDTO (List<Structure> structure){
        List<StructureDateDTO> structureDate = new ArrayList<>();

        for(Structure s : structure){
            structureDate.add(new StructureDateDTO(s));
        }
        return structureDate;
    }

}
