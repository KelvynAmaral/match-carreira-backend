package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.LoginRequest;
import com.elkys.matchcarreira.api.dto.TokenResponse;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest data) {
        Usuario usuario = repository.findByEmail(data.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(data.senha(), usuario.getSenha())) {
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenResponse(token));
        }

        return ResponseEntity.status(401).build();
    }
}