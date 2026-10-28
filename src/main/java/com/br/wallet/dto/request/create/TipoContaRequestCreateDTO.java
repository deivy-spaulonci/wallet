package com.br.wallet.dto.request.create;

public record TipoContaRequestCreateDTO(
        String nome,
        Boolean cartaoCredito,
        Boolean ativo
) {
}
