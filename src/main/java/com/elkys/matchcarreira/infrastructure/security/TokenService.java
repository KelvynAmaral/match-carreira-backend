package com.elkys.matchcarreira.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.elkys.matchcarreira.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    // Se não houver variável de ambiente, usa o valor padrão (mude em produção!)
    @Value("${api.security.token.secret:match-carreira-secret-key-pelo-menos-32-caracteres}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Instant agora = Instant.now();
        Instant expiracao = agora.plus(24, ChronoUnit.HOURS);

        return Jwts.builder()
                .issuer("match-carreira-api")
                .subject(usuario.getEmail())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .signWith(key)
                .compact();
    }

    public String validarToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}