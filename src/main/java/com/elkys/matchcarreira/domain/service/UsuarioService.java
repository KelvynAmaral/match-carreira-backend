package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.api.dto.usuario.UsuarioRequest;
import com.elkys.matchcarreira.api.dto.usuario.UsuarioResponse;
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
    public UsuarioResponse cadastrar(UsuarioRequest dto) {
        // 1. Validação de Negócio (Combate ao Clutter/Dados Duplicados)
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado no MatchCarreira.");
        }

        // 2. Mapeamento e Persistência do Usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(passwordEncoder.encode(dto.senha()));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 3. Automação Invisível: Criação do Currículo vinculado via @MapsId
        Curriculo novoCurriculo = Curriculo.builder()
                .usuario(usuarioSalvo)
                .resumoProfissional("Perfil recém-criado. Complete suas informações para aumentar seu alcance.")
                .build();

        curriculoRepository.save(novoCurriculo);

        // 4. Retorno Seguro via Record/DTO
        return new UsuarioResponse(usuarioSalvo);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}