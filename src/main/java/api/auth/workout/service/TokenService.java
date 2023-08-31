package api.auth.workout.service;


import api.auth.workout.entitys.acesso.Usuario;
import api.auth.workout.repositorys.RepositoryUsuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth.workout")
                    .withClaim("Role", String.valueOf(usuario.getRole()))
                    .withExpiresAt(timeExpires())
                    .withSubject(usuario.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException ex){
            throw new RuntimeException("erro ao gerar o token");
        }
    }
    public String validationToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth.workout")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException ex) {
            throw new RuntimeException("Erro ao validar o token");
        }
    }
    public String gerarRefreshToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth.workout")
                    .withExpiresAt(timeExpiresRefresh())
                    .withSubject(usuario.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException ex){
            throw new RuntimeException("erro ao gerar o Refreshtoken");
        }
    }


    private Instant timeExpires() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    private Instant timeExpiresRefresh() {
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }


}
