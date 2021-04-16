package com.flaminiovilla.geopic.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geopic.security.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    public String email;
    public String password;
    public String role;
    public String firstName;
    public String lastName;
    public Long regionId;
    public User callUser;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}