package com.flaminiovilla.geris.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
      config.addAllowedMethod("*");
      source.registerCorsConfiguration("/*/*/*", config);

      source.registerCorsConfiguration("/api/**", config);
      return new CorsFilter(source);
   }

}
