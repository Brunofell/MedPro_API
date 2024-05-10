package com.example.MedPro_api.infra.security.authPaciente;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.MedPro_api.entity.paciente.Paciente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenServicePaciente {

    private Paciente paciente;
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Paciente paciente){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API MEDPRO")
                    .withSubject(paciente.getEmail())
                    .withClaim("id", paciente.getId())   // passamos as infos que queremos para guardar dentro do token
                    .withExpiresAt(validade())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token JWT", exception);
        }
    }

    private Instant validade() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // dura 2 horas o token
    }

}
