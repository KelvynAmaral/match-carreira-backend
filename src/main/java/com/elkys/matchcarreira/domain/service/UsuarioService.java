package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.api.dto.UsuarioRequest;
import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.CurriculoRepository;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
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
    public Usuario cadastrar(UsuarioRequest dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("E-mail ja cadastrado.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(passwordEncoder.encode(dto.senha()));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Curriculo novoCurriculo = Curriculo.builder()
                .usuario(usuarioSalvo) // Aqui o MapsId entra em acao
                .resumoProfissional("Perfil recem-criado. Complete suas informacoes.")
                .build();

        curriculoRepository.save(novoCurriculo);

        return usuarioSalvo;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}