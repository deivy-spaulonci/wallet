package com.br.wallet.dto.response;

import com.br.wallet.entity.TipoDespesaEnum;

import java.math.BigDecimal;

public record DespesaResponseGastoTipoDTO(
        TipoDespesaEnum tipoDespesa,
        BigDecimal total
){
    public String getTipoDespesa(){
        return TipoDespesaEnum.forValues(this.tipoDespesa.getValue()).getNome();
    }
}
