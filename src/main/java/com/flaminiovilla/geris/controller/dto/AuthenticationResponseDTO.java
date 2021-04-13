package com.flaminiovilla.geris.controller.dto;

import com.flaminiovilla.geris.model.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    public String token;
    public String role;
    public String name;
    public String surname;
    public String email;
    public Region region;

}
