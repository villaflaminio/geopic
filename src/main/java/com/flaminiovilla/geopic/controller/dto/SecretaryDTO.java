package com.flaminiovilla.geopic.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geopic.security.model.User;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecretaryDTO {
    public Long id;
    public String  name;
    public String address;
    public String  email;
    public String  phone;
    public User user;
    public Long regionId;

}
