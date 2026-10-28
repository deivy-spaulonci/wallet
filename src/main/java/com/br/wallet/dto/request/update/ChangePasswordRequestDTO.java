package com.br.wallet.dto.request.update;

public record ChangePasswordRequestDTO(
        String email,
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {}
