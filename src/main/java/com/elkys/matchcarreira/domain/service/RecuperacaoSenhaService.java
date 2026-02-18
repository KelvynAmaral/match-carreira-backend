package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.domain.model.TokenRecuperacaoSenha;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.TokenRecuperacaoSenhaRepository;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.infrastructure.exception.RegraNegocioException;
import com.elkys.matchcarreira.infrastructure.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecuperacaoSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacaoSenhaRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void solicitarRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("E-mail não encontrado."));

        // Limpa tokens antigos para evitar lixo no banco
        tokenRepository.deleteByUsuario(usuario);

        // Gera um token aleatório e expira em 15 minutos
        String token = UUID.randomUUID().toString();
        TokenRecuperacaoSenha recuperacaoToken = TokenRecuperacaoSenha.builder()
                .token(token)
                .usuario(usuario)
                .dataExpiracao(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(recuperacaoToken);

        // Envia o e-mail (O Mago: automação invisível)
        emailService.enviarEmailRecuperacao(usuario.getEmail(), token);
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        TokenRecuperacaoSenha recuperacaoToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RegraNegocioException("Token inválido ou inexistente."));

        if (recuperacaoToken.estaExpirado()) {
            throw new RegraNegocioException("Token expirado. Solicite uma nova recuperação.");
        }

        Usuario usuario = recuperacaoToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));

        usuarioRepository.save(usuario);
        tokenRepository.delete(recuperacaoToken); // Limpa o token após o uso
    }
}