package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.auth.LoginRequest;
import com.elkys.matchcarreira.api.dto.auth.RedefinirSenhaRequest;
import com.elkys.matchcarreira.api.dto.auth.SolicitarRecuperacaoRequest;
import com.elkys.matchcarreira.api.dto.auth.TokenResponse;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.domain.service.RecuperacaoSenhaService;
import com.elkys.matchcarreira.infrastructure.exception.RegraNegocioException; // Importante
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
    private final RecuperacaoSenhaService recuperacaoSenhaService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest data) {

        Usuario usuario = repository.findByEmail(data.email())
                .orElseThrow(() -> new RegraNegocioException("E-mail ou senha inválidos"));

        if (passwordEncoder.matches(data.senha(), usuario.getSenha())) {
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenResponse(token));
        }

        throw new RegraNegocioException("E-mail ou senha inválidos");
    }

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