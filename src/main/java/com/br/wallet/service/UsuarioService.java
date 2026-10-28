package com.br.wallet.service;

import com.br.wallet.dto.request.create.UsuarioRequestCreateDTO;
import com.br.wallet.dto.request.update.ChangePasswordRequestDTO;
import com.br.wallet.entity.Usuario;
import com.br.wallet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findByEmail(String email) {

        this.usuarioRepository.findByEmail(email);
        return Optional.ofNullable(usuarioRepository.findByEmail(email))
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o email: " + email));

    }

    public void create(UsuarioRequestCreateDTO usuarioRequestCreateDTO){

    }

    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO,
                               String encodePassword) {
        Usuario usuario = findByEmail(changePasswordRequestDTO.email()).get();
        usuario.setPassword(encodePassword);
        usuario.setPasswordChangedAt(LocalDateTime.now());
        log.warn("mudando a senha do usuario: " + usuario.getUsername());
        usuarioRepository.save(usuario);
    }
}
