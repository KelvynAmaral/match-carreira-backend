package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "formacoes_academicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormacaoAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    @JsonBackReference
    private Curriculo curriculo;

    private String instituicao;
    private String curso;
    private String grau;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;
}