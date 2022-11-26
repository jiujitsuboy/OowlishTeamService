package com.oowlish.rolesapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oowlish.rolesapi.Constants;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class AppConfig {
  @Bean
  public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
    return new ShallowEtagHeaderFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Supports other password encoding, a must for existing applications.
    // However, uses BCrypt for new passwords. This will allow to use new or future encoders
    Map<String, PasswordEncoder> encoders = Map.of(Constants.ENCODER_ID, new BCryptPasswordEncoder(),
        "pbkdf2", new Pbkdf2PasswordEncoder(), "scrypt", new SCryptPasswordEncoder());
    return new DelegatingPasswordEncoder(Constants.ENCODER_ID, encoders);
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return mapper;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
