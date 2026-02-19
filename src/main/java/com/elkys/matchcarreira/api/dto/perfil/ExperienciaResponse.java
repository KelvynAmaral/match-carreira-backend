package com.elkys.matchcarreira.api.dto.perfil;

import com.elkys.matchcarreira.domain.model.perfil.ExperienciaProfissional;

import java.time.LocalDate;

public record ExperienciaResponse(String empresa, String cargo, LocalDate dataInicio, LocalDate dataFim, String descricao) {
    public ExperienciaResponse(ExperienciaProfissional exp) {
        this(exp.getEmpresa(), exp.getCargo(), exp.getDataInicio(), exp.getDataFim(), exp.getDescricao());
    }
}