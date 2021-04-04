package com.flaminiovilla.geopic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReferralPerson {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String phone;

    @ManyToOne
    @JoinColumn(name="secretary_ID")
    private Secretary secretary;
    @ManyToOne
    @JoinColumn(name="region_ID")
    private Region region;
}
