package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.api.dto.ExperienciaRequest;
import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.model.ExperienciaProfissional;
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
    @Transactional
    public Curriculo atualizarPerfilLogado(UUID usuarioId, Curriculo dadosNovos) {
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);

        curriculo.setResumoProfissional(dadosNovos.getResumoProfissional());
        curriculo.setCargoDesejado(dadosNovos.getCargoDesejado());
        curriculo.setCompetencias(dadosNovos.getCompetencias());

        return curriculoRepository.save(curriculo);
    }
    @Transactional
    public Curriculo adicionarExperiencia(UUID usuarioId, ExperienciaRequest dto) {
        // 1. Busca o currículo do usuário logado
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);

        // 2. Mapeia DTO para Entity
        ExperienciaProfissional novaExp = ExperienciaProfissional.builder()
                .empresa(dto.empresa())
                .cargo(dto.cargo())
                .descricao(dto.descricao())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .atual(dto.atual())
                .curriculo(curriculo) // Vínculo importante!
                .build();

        // 3. Adiciona na lista do currículo
        curriculo.getExperiencias().add(novaExp);

        // 4. Salva o currículo (O CascadeType.ALL salvará a experiência automaticamente)
        return curriculoRepository.save(curriculo);
    }
}