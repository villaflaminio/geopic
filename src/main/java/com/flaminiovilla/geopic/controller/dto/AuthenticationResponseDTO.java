package com.flaminiovilla.geopic.controller.dto;

import com.flaminiovilla.geopic.model.Region;
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
