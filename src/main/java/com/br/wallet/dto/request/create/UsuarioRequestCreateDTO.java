package com.br.wallet.dto.request.create;

import com.br.wallet.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestCreateDTO(

        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String userName,

        @NotBlank
        Role role

) {
}
