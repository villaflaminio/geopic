package com.flaminiovilla.geopic.security.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO per ricevere le credenziali dal login
 */
public class LoginDTO {

   @NotNull
   @Size(min = 1, max = 50)
   public String email;

   @NotNull
   @Size(min = 4, max = 100)
   public String password;

   public Boolean rememberMe;

   public Boolean isRememberMe() {
      return rememberMe;
   }

}
