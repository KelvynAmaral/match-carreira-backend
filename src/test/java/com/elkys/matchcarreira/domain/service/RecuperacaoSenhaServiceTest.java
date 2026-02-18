package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.domain.model.TokenRecuperacaoSenha;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.TokenRecuperacaoSenhaRepository;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.infrastructure.exception.RegraNegocioException;
import com.elkys.matchcarreira.infrastructure.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecuperacaoSenhaServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private TokenRecuperacaoSenhaRepository tokenRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @Test
    @DisplayName("Deve disparar erro ao solicitar recuperação para e-mail inexistente")
    void solicitarRecuperacao_EmailInexistente() {
        // GIVEN (Dado que...)
        String email = "fake@elkys.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // WHEN & THEN (Quando/Então...)
        assertThrows(RegraNegocioException.class, () ->
                recuperacaoSenhaService.solicitarRecuperacao(email)
        );

        // Garante que o e-mail NUNCA foi enviado
        verify(emailService, never()).enviarEmailRecuperacao(anyString(), anyString());
    }

    @Test
    @DisplayName("Deve gerar token e enviar e-mail com sucesso")
    void solicitarRecuperacao_Sucesso() {
        // GIVEN
        String email = "kelvyn@elkys.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // WHEN
        recuperacaoSenhaService.solicitarRecuperacao(email);

        // THEN
        verify(tokenRepository, times(1)).deleteByUsuario(usuario); // Limpou antigos?
        verify(tokenRepository, times(1)).save(any()); // Salvou novo token?
        verify(emailService, times(1)).enviarEmailRecuperacao(eq(email), anyString()); // Enviou?
    }
    @Test
    @DisplayName("Deve disparar erro ao redefinir senha com token expirado")
    void redefinirSenha_TokenExpirado() {
        // GIVEN
        String token = "token-velho";
        TokenRecuperacaoSenha tokenEntidade = new TokenRecuperacaoSenha();
        tokenEntidade.setToken(token);
        // Define expiração para 1 hora atrás
        tokenEntidade.setDataExpiracao(java.time.LocalDateTime.now().minusHours(1));

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntidade));

        // WHEN & THEN
        assertThrows(RegraNegocioException.class, () ->
                recuperacaoSenhaService.redefinirSenha(token, "novaSenha123")
        );

        // Garante que a senha do usuário NUNCA foi alterada
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve redefinir senha com sucesso")
    void redefinirSenha_Sucesso() {
        // GIVEN
        String token = "token-valido";
        String novaSenhaPlana = "novaSenha123";
        String senhaCriptografada = "hash-senha-123";

        Usuario usuario = new Usuario();
        TokenRecuperacaoSenha tokenEntidade = new TokenRecuperacaoSenha();
        tokenEntidade.setToken(token);
        tokenEntidade.setUsuario(usuario);
        tokenEntidade.setDataExpiracao(java.time.LocalDateTime.now().plusMinutes(15));

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(tokenEntidade));
        when(passwordEncoder.encode(novaSenhaPlana)).thenReturn(senhaCriptografada);

        // WHEN
        recuperacaoSenhaService.redefinirSenha(token, novaSenhaPlana);

        // THEN
        verify(passwordEncoder).encode(novaSenhaPlana); // Criptografou?
        verify(usuarioRepository).save(usuario); // Salvou o usuário?
        verify(tokenRepository).delete(tokenEntidade); // Deletou o token usado?
    }
}