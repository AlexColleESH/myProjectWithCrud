package a.progettoutente.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -3301605591108950415L;
    private final Clock clock = DefaultClock.INSTANCE;

    private final JwtConfig jwtConfig;

    public JwtTokenUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("Token expired: " + e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            System.err.println("Token not supported: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.err.println("Token invalid: " + e.getMessage());
            throw e;
        } catch (SignatureException e) {
            System.err.println("Invalid token signature: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            System.err.println("Token JWT empty: " + e.getMessage());
            throw e;
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // Specifica i token per cui l'expiration è ignorata
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails);
    }

    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .setIssuer("TestJwt")
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
    }
}
