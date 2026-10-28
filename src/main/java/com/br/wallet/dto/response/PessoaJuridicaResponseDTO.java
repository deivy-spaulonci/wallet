package com.br.wallet.dto.response;

public record PessoaJuridicaResponseDTO(
    String nome,
    String razaoSocial,
    String cnpj,
    String codigoIbge
){}
