package com.flaminiovilla.geris.model;

import com.flaminiovilla.geris.component.geoHash.model.GeoHash;
import com.flaminiovilla.geris.component.geoHash.model.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Structure  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String name;

    @Column(name = "macroCategory")
    private String macroCategory;
    private String description;
    private String logo;
    private Integer discount;

    @Column(name = "expireDateConvention")
    private Date expireDateConvention;
    //contatti
    private String email;
    private String phone;
    private String website;

    //posizione
    private String address;
    private Double latitude;
    private Double longitude;
    private GeoPoint geoPoint;

    @ManyToOne
    @JoinColumn(name="secretarty_ID")
    private Secretary secretary;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name="referralPerson_ID")
    private ReferralPerson referralPerson;

    @ManyToOne
    @JoinColumn(name="region_ID")
    private Region region;

    public Structure(Long id, String name, Category category, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
     * quando viene inviato il json bisogna aggiungere la distanza di questo luogo ripetto all'utente
     * aggiungere il db con tutti i comuni italiani
     *
  */

}
