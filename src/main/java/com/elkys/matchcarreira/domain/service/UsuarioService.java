package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.api.dto.usuario.UsuarioRequest;
import com.elkys.matchcarreira.api.dto.usuario.UsuarioResponse;
import com.elkys.matchcarreira.domain.model.perfil.Curriculo;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.CurriculoRepository;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.infrastructure.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CurriculoRepository curriculoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse cadastrar(UsuarioRequest dto) {
        if (!dto.senhasConferem()) {
            throw new RegraNegocioException("As senhas digitadas não coincidem.");
        }

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RegraNegocioException("E-mail já cadastrado no MatchCarreira.");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Curriculo novoCurriculo = Curriculo.builder()
                .usuario(usuarioSalvo)
                .resumoProfissional("Perfil recém-criado na Elkys. Complete suas informações!")
                .build();

        curriculoRepository.save(novoCurriculo);

        return new UsuarioResponse(usuarioSalvo);
    }
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}