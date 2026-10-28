package com.br.wallet.dto.response;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public record DespesaResponseGastoMensalDTO (BigDecimal total,String mes) {
    public DespesaResponseGastoMensalDTO(Integer mes, BigDecimal total) {
        this(total,Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
    }
}
