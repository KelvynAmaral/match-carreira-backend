package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "curriculos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Curriculo {

    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    private String cidade;

    private String estado;

    @Column(name = "resumo_profissional", columnDefinition = "TEXT")
    private String resumoProfissional;

    @Column(name = "cargo_desejado")
    private String cargoDesejado;

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienciaProfissional> experiencias = new ArrayList<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormacaoAcademica> formacoes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "curriculo_competencias", joinColumns = @JoinColumn(name = "curriculo_id"))
    @Column(name = "competencia")
    private List<String> competencias = new ArrayList<>();
}