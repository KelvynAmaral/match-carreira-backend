package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "curriculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Facilita a criação automática no UsuarioService
public class Curriculo {

    @Id
    private UUID id; // Removido o @GeneratedValue, pois usaremos o ID do Usuario

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // O ID desta entidade será o mesmo ID da entidade Usuario
    @JoinColumn(name = "usuario_id")
    @JsonIgnore // Essencial para não travar o Jackson no loop infinito
    private Usuario usuario;

    @Column(name = "resumo_profissional", columnDefinition = "TEXT")
    private String resumoProfissional;

    private String cargoDesejado; // Adicionado para facilitar o "Match" com vagas

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienciaProfissional> experiencias = new ArrayList<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormacaoAcademica> formacoes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "curriculo_competencias", joinColumns = @JoinColumn(name = "curriculo_id"))
    @Column(name = "competencia")
    private List<String> competencias = new ArrayList<>();
}