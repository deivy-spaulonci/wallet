package com.br.wallet.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PessoaJuridicaRequestCreateDTO(
    @NotBlank(message = "Nome inválido!")
    String nome,
    String razaoSocial,
    @Size(min=14, max = 14)
    String cnpj,
    @NotBlank(message = "Código da cidade inválido!")
    String codigoIbge
){}
