package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.domain.model.Curriculo;
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
    public Curriculo atualizar(UUID usuarioId, Curriculo dadosAtualizados) {
        // Busca o currículo existente que foi criado no cadastro do usuário
        Curriculo curriculoExistente = buscarPorUsuarioId(usuarioId);


        curriculoExistente.setResumoProfissional(dadosAtualizados.getResumoProfissional());
        curriculoExistente.setCargoDesejado(dadosAtualizados.getCargoDesejado());
        curriculoExistente.setCompetencias(dadosAtualizados.getCompetencias());

        return curriculoRepository.save(curriculoExistente);
    }

    public Curriculo buscarPorUsuarioId(UUID usuarioId) {
        return curriculoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Curriculo nao encontrado para o usuario: " + usuarioId));
    }
}