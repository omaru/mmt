package com.haynespro.assessment.mmt.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST APIs that contains all the operations that can be performed for authentication like login,
 * registration etc
 */
@RequestMapping("/v1/authentication")
@RestController
@Tag(
    name = "Authentication",
    description = "The Authentication API. Currently only supports login.")
public class AuthenticationApi {

  public static class User {
    private String username;
    private String password;

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }
  }

  private final UserDetailsService userDetailsService;
  private final JwtEncoder encoder;

  public AuthenticationApi(UserDetailsService userDetailsService, JwtEncoder encoder) {
    this.userDetailsService = userDetailsService;
    this.encoder = encoder;
  }

  /**
   * API to Login
   *
   * @param user The login entity that contains username and password
   * @return Returns the JWT token
   */
  @Operation(
      summary = "User Authentication",
      description = "Authenticate the user and return a JWT token if the user is valid.")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content =
          @Content(
              mediaType = "application/json",
              examples =
                  @ExampleObject(
                      value = "{\n \"username\": \"theUser\",\n \"password\": \"hisPassword\"\n}",
                      summary = "User Authentication Example")))
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> login(@RequestBody User user) {
    var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
    if (null != userDetails && user.getPassword().equalsIgnoreCase(userDetails.getPassword())) {
      var token = generateToken(userDetails);
      var httpHeaders = new HttpHeaders();
      httpHeaders.set("X-AUTH-TOKEN", token);
      return ResponseEntity.ok()
          .headers(httpHeaders)
          .contentType(MediaType.APPLICATION_JSON)
          .body("{\"token\":\"" + token + "\"}");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .contentType(MediaType.APPLICATION_JSON)
          .body("Invalid username or password");
    }
  }

  /**
   * Generates the JWT token with claims
   *
   * @param userDetails The user details
   * @return Returns the JWT token
   */
  private String generateToken(UserDetails userDetails) {
    var now = Instant.now();
    var expiry = 36000L;
    // @formatter:off
    var scope =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    var claims =
        JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(userDetails.getUsername())
            .claim("scope", scope)
            .build();
    // @formatter:on
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
