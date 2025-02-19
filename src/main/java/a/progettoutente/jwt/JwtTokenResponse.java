package a.progettoutente.jwt;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtTokenResponse implements Serializable 
{

	private static final long serialVersionUID = 8317676219297719109L;

	private final String token;

    public JwtTokenResponse(String token) {
        this.token = token;
    }
}