package com.flaminiovilla.geopic.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Il Cross-Origin Resource Sharing (CORS) è un meccanismo che usa header HTTP addizionali
 * per indicare che un dominio dispone dell'autorizzazione per accedere alle risorse selezionate
 * **/
@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOrigin("https://geris.flaminiovilla.it"); // e.g. http://domain1.com
      config.addAllowedHeader("http://flaminiovilla.it");
      config.setAllowCredentials(true);
      config.addAllowedOrigin("*");
      config.addAllowedHeader("*");
      config.addAllowedMethod("OPTIONS");
      config.addAllowedMethod("HEAD");
      config.addAllowedMethod("GET");
      config.addAllowedMethod("PUT");
      config.addAllowedMethod("POST");
      config.addAllowedMethod("DELETE");
      config.addAllowedMethod("PATCH");
      source.registerCorsConfiguration("/**", config);

      source.registerCorsConfiguration("/api/**", config);
      return new CorsFilter(source);
   }

}
