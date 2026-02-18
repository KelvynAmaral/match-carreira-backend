package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails { // Implementação crucial

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String senha;

    private String nome;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Curriculo curriculo;

    // --- MÉTODOS DO USERDETAILS ---

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define o nível de acesso. Como Business Owner, todos por enquanto são ROLE_USER.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email; // O email é o nosso login/username
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; // Conta nunca bloqueia
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true; // Senha nunca expira
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true; // Usuário sempre ativo
    }
}