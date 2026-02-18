package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.api.dto.perfil.ExperienciaRequest;
import com.elkys.matchcarreira.api.dto.perfil.FormacaoRequest;
import com.elkys.matchcarreira.api.dto.perfil.PerfilAtualizacaoRequest;
import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.model.ExperienciaProfissional;
import com.elkys.matchcarreira.domain.model.FormacaoAcademica;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.repository.CurriculoRepository;
import com.elkys.matchcarreira.infrastructure.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;

    public Curriculo buscarPorUsuarioId(UUID usuarioId) {
        return curriculoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RegraNegocioException("Currículo não encontrado para o usuário: " + usuarioId));
    }

    // --- GESTÃO DE PERFIL ---

    @Transactional
    public Curriculo atualizarPerfilCompleto(UUID usuarioId, PerfilAtualizacaoRequest dto) {
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);
        Usuario usuario = curriculo.getUsuario();

        // Dados de Identidade (Usuario)
        usuario.setNome(dto.nome());
        usuario.setTelefone(dto.telefone());
        usuario.setDataNascimento(dto.dataNascimento());

        // Dados Profissionais e Localização (Curriculo)
        curriculo.setCidade(dto.cidade());
        curriculo.setEstado(dto.estado());
        curriculo.setResumoProfissional(dto.resumoProfissional());
        curriculo.setCargoDesejado(dto.cargoDesejado());

        return curriculoRepository.save(curriculo);
    }

    // --- GESTÃO DE EXPERIÊNCIAS ---

    @Transactional
    public Curriculo adicionarExperiencia(UUID usuarioId, ExperienciaRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);

        ExperienciaProfissional novaExp = ExperienciaProfissional.builder()
                .empresa(dto.empresa()).cargo(dto.cargo()).descricao(dto.descricao())
                .dataInicio(dto.dataInicio()).dataFim(dto.dataFim()).atual(dto.atual())
                .curriculo(curriculo).build();

        curriculo.getExperiencias().add(novaExp);
        return curriculoRepository.save(curriculo);
    }

    @Transactional
    public Curriculo atualizarExperiencia(UUID usuarioId, UUID experienciaId, ExperienciaRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);

        ExperienciaProfissional exp = curriculo.getExperiencias().stream()
                .filter(e -> e.getId().equals(experienciaId)).findFirst()
                .orElseThrow(() -> new RegraNegocioException("Experiência não encontrada."));

        exp.setEmpresa(dto.empresa()); exp.setCargo(dto.cargo()); exp.setDescricao(dto.descricao());
        exp.setDataInicio(dto.dataInicio()); exp.setDataFim(dto.dataFim()); exp.setAtual(dto.atual());

        return curriculoRepository.save(curriculo);
    }

    @Transactional
    public void removerExperiencia(UUID usuarioId, UUID experienciaId) {
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);
        if (!curriculo.getExperiencias().removeIf(exp -> exp.getId().equals(experienciaId))) {
            throw new RegraNegocioException("Experiência não encontrada.");
        }
        curriculoRepository.save(curriculo);
    }

    // --- GESTÃO DE FORMAÇÕES ---

    @Transactional
    public Curriculo adicionarFormacao(UUID usuarioId, FormacaoRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);

        FormacaoAcademica novaFormacao = FormacaoAcademica.builder()
                .instituicao(dto.instituicao()).curso(dto.curso()).grau(dto.grau())
                .dataInicio(dto.dataInicio()).dataFim(dto.dataFim()).atual(dto.atual())
                .curriculo(curriculo).build();

        curriculo.getFormacoes().add(novaFormacao);
        return curriculoRepository.save(curriculo);
    }

    @Transactional
    public Curriculo atualizarFormacao(UUID usuarioId, UUID formacaoId, FormacaoRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);

        FormacaoAcademica form = curriculo.getFormacoes().stream()
                .filter(f -> f.getId().equals(formacaoId)).findFirst()
                .orElseThrow(() -> new RegraNegocioException("Formação não encontrada."));

        form.setInstituicao(dto.instituicao()); form.setCurso(dto.curso()); form.setGrau(dto.grau());
        form.setDataInicio(dto.dataInicio()); form.setDataFim(dto.dataFim()); form.setAtual(dto.atual());

        return curriculoRepository.save(curriculo);
    }

    @Transactional
    public void removerFormacao(UUID usuarioId, UUID formacaoId) {
        Curriculo curriculo = buscarPorUsuarioId(usuarioId);
        if (!curriculo.getFormacoes().removeIf(f -> f.getId().equals(formacaoId))) {
            throw new RegraNegocioException("Formação não encontrada.");
        }
        curriculoRepository.save(curriculo);
    }

    private void validarIntervaloDatas(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null && fim.isBefore(inicio)) {
            throw new RegraNegocioException("A data de término não pode ser anterior à data de início.");
        }
    }
}