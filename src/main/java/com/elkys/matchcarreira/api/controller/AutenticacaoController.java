package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.*;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.domain.service.RecuperacaoSenhaService; // Novo
import com.elkys.matchcarreira.infrastructure.security.TokenService;
import jakarta.validation.Valid;
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
    private final RecuperacaoSenhaService recuperacaoSenhaService; // Injeção da nova Service

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest data) {
        Usuario usuario = repository.findByEmail(data.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(data.senha(), usuario.getSenha())) {
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenResponse(token));
        }

        return ResponseEntity.status(401).build();
    }

    // --- NOVOS MÉTODOS DE RECUPERAÇÃO ---

    @PostMapping("/esqueci-senha")
    public ResponseEntity<Void> solicitarRecuperacao(@RequestBody @Valid SolicitarRecuperacaoRequest request) {
        recuperacaoSenhaService.solicitarRecuperacao(request.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestBody @Valid RedefinirSenhaRequest request) {
        recuperacaoSenhaService.redefinirSenha(request.token(), request.novaSenha());
        return ResponseEntity.ok().build();
    }
}