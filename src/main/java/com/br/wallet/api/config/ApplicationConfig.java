package com.br.wallet.api.config;

import com.br.wallet.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioService usuarioService;

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> usuarioService.findByEmail(username).get();

//        return username -> (org.springframework.security.core.userdetails.UserDetails) usuarioService.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
