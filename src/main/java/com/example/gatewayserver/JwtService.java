package com.example.gatewayserver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private static final String SECRET_KEY =
      "pejFQ7VibeTQXuMs0T/XiNFUHz5ZrITrzG9LBTDujsCEOqE6iR6+X8R8rP0V88Gc\n";

  public boolean validateToken(String token) {
    try {
      Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
