package a.progettoutente.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtConfig
{
    private String uri;
    private String refresh;
    private String header;
    private String prefix;
    private int expiration;
    private String secret;
}

