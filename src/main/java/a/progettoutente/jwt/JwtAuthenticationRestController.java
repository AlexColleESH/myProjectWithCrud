package a.progettoutente.jwt;


import a.progettoutente.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    @Qualifier("customUserDetailsService")
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping(value = "${jwt.uri}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest jwtTokenRequest,
                                                       HttpServletResponse response)
            throws Exception {
        log.info("Autenticazione e Generazione Token");

        authenticate(jwtTokenRequest.getUsername(), jwtTokenRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        log.info(String.format("Token %s", token));


        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(false)  // Deve essere false per permettere l'accesso dal frontend (se necessario)
                .secure(false)    // Imposta a true in produzione con HTTPS
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")  // Imposta SameSite=Lax
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok(new JwtTokenResponse(token));
    }


    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String email, String password) throws Exception {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            log.warn("USER DISABLED");
            throw new Exception("USER DISABLED", e);
        } catch (BadCredentialsException e) {
            log.warn("CREDENTIALS INVALID");
            throw new Exception("CREDENTIALS INVALID", e);
        }
    }
}
