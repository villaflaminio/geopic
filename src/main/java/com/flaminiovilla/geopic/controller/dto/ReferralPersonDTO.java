package com.flaminiovilla.geopic.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geopic.security.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferralPersonDTO {
    public Long id;
    public String email;
    public String name;
    public String surname;
    public String phone;
    public Long regionId;
    public Long secretaryId;
    public User user;
}
