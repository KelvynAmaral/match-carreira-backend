package com.elkys.matchcarreira.domain.service;

import com.elkys.matchcarreira.api.dto.perfil.*;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.model.perfil.Curriculo;
import com.elkys.matchcarreira.domain.model.perfil.ExperienciaProfissional;
import com.elkys.matchcarreira.domain.model.perfil.FormacaoAcademica;
import com.elkys.matchcarreira.domain.repository.CurriculoRepository;
import com.elkys.matchcarreira.domain.repository.UsuarioRepository;
import com.elkys.matchcarreira.infrastructure.exception.RegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final CurriculoRepository curriculoRepository;
    private final UsuarioRepository usuarioRepository;

    @Cacheable(value = "perfil", key = "#usuarioId")
    @Transactional(readOnly = true)
    public PerfilResponse buscarPorUsuario(UUID usuarioId) {
        return curriculoRepository.findById(usuarioId)
                .map(PerfilResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado no ecossistema Elkys."));
    }

    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public PerfilResponse atualizarCompleto(UUID usuarioId, PerfilAtualizacaoRequest dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        usuario.setNome(dto.nome());
        usuario.setTelefone(dto.telefone());
        usuario.setDataNascimento(dto.dataNascimento());

        Curriculo curriculo = usuario.getCurriculo();
        curriculo.setCidade(dto.cidade());
        curriculo.setEstado(dto.estado());
        curriculo.setResumoProfissional(dto.resumoProfissional());
        curriculo.setCargoDesejado(dto.cargoDesejado());

        return new PerfilResponse(curriculoRepository.save(curriculo));
    }

    // --- GESTÃO DE EXPERIÊNCIAS ---

    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public PerfilResponse adicionarExperiencia(UUID usuarioId, ExperienciaRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

        ExperienciaProfissional novaExp = ExperienciaProfissional.builder()
                .empresa(dto.empresa()).cargo(dto.cargo()).descricao(dto.descricao())
                .dataInicio(dto.dataInicio()).dataFim(dto.dataFim()).atual(dto.atual())
                .curriculo(curriculo).build();

        curriculo.getExperiencias().add(novaExp);
        return new PerfilResponse(curriculoRepository.save(curriculo));
    }

    // MODO NOVO: Sincronizado com PerfilController.atualizarExperiencia
    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public PerfilResponse atualizarExperiencia(UUID usuarioId, UUID experienciaId, ExperienciaRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

        ExperienciaProfissional exp = curriculo.getExperiencias().stream()
                .filter(e -> e.getId().equals(experienciaId)).findFirst()
                .orElseThrow(() -> new RegraNegocioException("Experiência profissional não encontrada."));

        exp.setEmpresa(dto.empresa());
        exp.setCargo(dto.cargo());
        exp.setDescricao(dto.descricao());
        exp.setDataInicio(dto.dataInicio());
        exp.setDataFim(dto.dataFim());
        exp.setAtual(dto.atual());

        return new PerfilResponse(curriculoRepository.save(curriculo));
    }

    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public void removerExperiencia(UUID usuarioId, UUID experienciaId) {
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

        if (!curriculo.getExperiencias().removeIf(exp -> exp.getId().equals(experienciaId))) {
            throw new RegraNegocioException("Experiência não encontrada.");
        }
        curriculoRepository.save(curriculo);
    }

    // --- GESTÃO DE FORMAÇÕES ---

    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public PerfilResponse adicionarFormacao(UUID usuarioId, FormacaoRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

        FormacaoAcademica novaFormacao = FormacaoAcademica.builder()
                .instituicao(dto.instituicao()).curso(dto.curso()).grau(dto.grau())
                .dataInicio(dto.dataInicio()).dataFim(dto.dataFim()).atual(dto.atual())
                .curriculo(curriculo).build();

        curriculo.getFormacoes().add(novaFormacao);
        return new PerfilResponse(curriculoRepository.save(curriculo));
    }

    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public PerfilResponse atualizarFormacao(UUID usuarioId, UUID formacaoId, FormacaoRequest dto) {
        validarIntervaloDatas(dto.dataInicio(), dto.dataFim());
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

        FormacaoAcademica form = curriculo.getFormacoes().stream()
                .filter(f -> f.getId().equals(formacaoId)).findFirst()
                .orElseThrow(() -> new RegraNegocioException("Formação acadêmica não encontrada."));

        form.setInstituicao(dto.instituicao());
        form.setCurso(dto.curso());
        form.setGrau(dto.grau());
        form.setDataInicio(dto.dataInicio());
        form.setDataFim(dto.dataFim());
        form.setAtual(dto.atual());

        return new PerfilResponse(curriculoRepository.save(curriculo));
    }

    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public void removerFormacao(UUID usuarioId, UUID formacaoId) {
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

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
    @Transactional
    @CacheEvict(value = "perfil", key = "#usuarioId")
    public PerfilResponse atualizarCompetencias(UUID usuarioId, List<String> competencias) {
        Curriculo curriculo = curriculoRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Currículo não encontrado"));

        curriculo.setCompetencias(competencias);

        return new PerfilResponse(curriculoRepository.save(curriculo));
    }
}