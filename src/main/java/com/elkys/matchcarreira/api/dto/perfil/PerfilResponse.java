package com.elkys.matchcarreira.api.dto.perfil;

import com.elkys.matchcarreira.domain.model.Curriculo;
import java.time.LocalDate;
import java.util.List;

public record PerfilResponse(
        String nome,
        String email,
        String telefone,
        LocalDate dataNascimento,
        String cidade,
        String estado,
        String resumoProfissional,
        String cargoDesejado,
        List<ExperienciaResponse> experiencias,
        List<FormacaoResponse> formacoes
) {
    public PerfilResponse(Curriculo curriculo) {
        this(
                curriculo.getUsuario().getNome(),
                curriculo.getUsuario().getEmail(),
                curriculo.getUsuario().getTelefone(),
                curriculo.getUsuario().getDataNascimento(),
                curriculo.getCidade(),
                curriculo.getEstado(),
                curriculo.getResumoProfissional(),
                curriculo.getCargoDesejado(),
                curriculo.getExperiencias().stream().map(ExperienciaResponse::new).toList(),
                curriculo.getFormacoes().stream().map(FormacaoResponse::new).toList()
        );
    }
}