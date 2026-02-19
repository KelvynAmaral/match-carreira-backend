package com.elkys.matchcarreira.api.dto.perfil;

import com.elkys.matchcarreira.domain.model.perfil.FormacaoAcademica;

import java.time.LocalDate;

public record FormacaoResponse(String instituicao, String curso, LocalDate dataInicio, LocalDate dataFim) {
    public FormacaoResponse(FormacaoAcademica form) {
        this(form.getInstituicao(), form.getCurso(), form.getDataInicio(), form.getDataFim());
    }
}