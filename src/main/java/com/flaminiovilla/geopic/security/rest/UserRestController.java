package com.flaminiovilla.geopic.security.rest;

import com.flaminiovilla.geopic.security.exception.UserException;
import com.flaminiovilla.geopic.security.helper.UserHelper;
import com.flaminiovilla.geopic.security.model.User;
import com.flaminiovilla.geopic.security.rest.dto.LoginDTO;
import com.flaminiovilla.geopic.security.rest.dto.UserDTO;
import com.flaminiovilla.geopic.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.flaminiovilla.geopic.security.exception.UserException.userExceptionCode.USER_NOT_LOGGED_IN;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserRestController {
//TODO prevedere il cambio password

   @Autowired
   UserHelper userHelper;
   private final UserService userService;

   public UserRestController(UserService userService) {
      this.userService = userService;
   }

   /** Dati per il login nel seguente formato
    * {
    *     "email" : "viflaadminsecretary@gmail.com",
    *     "password" : "flaminio"
    * }
    * */
   @CrossOrigin(origins = "*")
   @PostMapping("/authenticate")
   public ResponseEntity<UserHelper.JWTToken> authorize(@Valid @RequestBody LoginDTO loginDto) {
      return userHelper.authorize(loginDto);
   }

   /** Dati per register nel seguente formato
    *{
    *     "email" : "viflaadminsecretary@gmail.com",
    *     "password" : "flaminio",
    *     "firstName" : "flaminio",
    *     "lastName" : "villa",
    *     "region" : 3
    * }
    * */
   @PostMapping("/register/user")
   public User registerUser(@Valid @RequestBody UserDTO userDTO) {
      return userHelper.registerUser(userDTO);
   }

   /** Dati per register nel seguente formato
    *{
    *     "email" : "viflaadminsecretary@gmail.com",
    *     "password" : "flaminio",
    *     "firstName" : "flaminio",
    *     "lastName" : "villa",
    *
    * }
    * */
   @PostMapping("/register/admin")
   public User registerAdmin(@Valid @RequestBody UserDTO userDTO) {
      setUser(userDTO);

      return userHelper.registerAdmin(userDTO);
   }

   /** Dati per register nel seguente formato
    * Admin secretary puo' essere registrato solo da admin , deve quindi specificare la regione
    *{i
    *     "email" : "viflaadminsecretary@gmail.com",
    *     "password" : "flaminio",
    *     "firstName" : "flaminio",
    *     "lastName" : "villa",
    *     "regionId" : 3
    * }
    * */
   @PostMapping("/register/adminSecretary")
   public User registerAdminSecretary(@Valid @RequestBody UserDTO userDTO) {
      setUser(userDTO);

      return userHelper.registerAdminSecretary(userDTO);
   }

   /** Dati per register nel seguente formato
    *{
    *     "email" : "viflaadminsecretary@gmail.com",
    *     "password" : "flaminio",
    *     "role" : "USER" ,
    *     "firstName" : "flaminio",
    *     "lastName" : "villa",
    *     "region" : 3
    * }
    * */
   @PostMapping("/register/secretary")
   public User registerSecretary(@Valid @RequestBody UserDTO userDTO) {
      setUser(userDTO);
      return userHelper.registerSecretary(userDTO);
   }

   private void setUser(UserDTO userDTO){
      Optional<User> userLogged = userService.getUserWithAuthorities();
      if (userLogged.isPresent())
         userDTO.callUser = userLogged.get();
      else
         throw new UserException(USER_NOT_LOGGED_IN);
   }
}
