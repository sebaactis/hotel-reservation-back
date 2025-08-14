package group.com.hotel_reservation.services.auth;

import group.com.hotel_reservation.models.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String email, Role role, String name, String lastName) {
        long expirationTime = expiration;

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.getName())
                .claim("name", name)
                .claim("lastName", lastName)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean IsValidToken(String token, String email) {
        return extractEmail(token).equals(email);
    }

    public boolean IsTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }
}
