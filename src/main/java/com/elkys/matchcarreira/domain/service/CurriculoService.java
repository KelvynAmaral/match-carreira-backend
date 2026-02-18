package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.CurriculoRepository;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Curriculo salvar(UUID usuarioId, Curriculo curriculo) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Vincula as entidades para o JPA salvar em cascata
        curriculo.setUsuario(usuario);

        // Garantir que as experiências e formações conheçam o currículo pai
        if (curriculo.getExperiencias() != null) {
            curriculo.getExperiencias().forEach(exp -> exp.setCurriculo(curriculo));
        }
        if (curriculo.getFormacoes() != null) {
            curriculo.getFormacoes().forEach(form -> form.setCurriculo(curriculo));
        }

        return curriculoRepository.save(curriculo);
    }

    public Curriculo buscarPorUsuario(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return curriculoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Currículo não encontrado para este usuário"));
    }
}