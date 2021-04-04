package com.flaminiovilla.geopic.security.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestSecurityController {

   @GetMapping("/testUser")
   public ResponseEntity<String> testUser() {
      return ResponseEntity.ok("ROLE_USER");
   }
   @GetMapping("/testSecretary")
   public ResponseEntity<String> testSecretary() {
      return ResponseEntity.ok("ROLE_SECRETARY");
   }
   @GetMapping("/testAdminSecretary")
   public ResponseEntity<String> testAdminSecretary() {
      return ResponseEntity.ok("ROLE_ADMIN_SECRETARY");
   }
   @GetMapping("/testAdmin")
   public ResponseEntity<String> testAdmin() {
      return ResponseEntity.ok("ROLE_ADMIN");
   }

}
