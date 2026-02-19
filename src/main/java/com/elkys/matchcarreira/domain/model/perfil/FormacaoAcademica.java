package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "formacoes_academicas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder // <--- ESTA ANOTAÇÃO RESOLVE O ERRO VERMELHO
public class FormacaoAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String instituicao;
    private String curso;
    private String grau;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    private boolean atual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    @JsonIgnore
    private Curriculo curriculo;
}