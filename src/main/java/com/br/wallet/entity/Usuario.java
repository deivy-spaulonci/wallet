package com.br.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID; // Importação do UUID

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Estratégia nativa para UUID
    private UUID uuid; // Mudou de Long para UUID

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, name = "username")
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column (unique = true, nullable = false)
    private String ip;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}