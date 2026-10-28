package com.br.wallet.dto.response;

public record TipoDespesaResponseDTO(
        String value,
        String nome
) {
    @Override
    public String toString() {
        return nome;
    }
}
