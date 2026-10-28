package com.br.wallet.dto.response;

import java.math.BigDecimal;

public record ContaResponseGastoTipoDTO(
        String tipoConta,
        BigDecimal total
){}
