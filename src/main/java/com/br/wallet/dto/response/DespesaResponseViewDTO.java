package com.br.wallet.dto.response;

import com.br.wallet.entity.TipoDespesaEnum;
import com.br.wallet.entity.TipoFormaPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DespesaResponseViewDTO(
        UUID uuid,
        String tipoDespesa,
        String fornecedor,
        LocalDate dataPagamento,
        BigDecimal valor,
        String formaPagamentoInstituicao,
        String formaPagamentoModalidade,
        String observacao
) {
    public String getFormaPagamento()
    {
        String forma = TipoFormaPagamentoEnum.valueOf(formaPagamentoModalidade).getDescricao();
        forma += formaPagamentoInstituicao==null ? "" : " - " + formaPagamentoInstituicao;
        return forma;
    }

    public String getTipoDespesa(){
        return TipoDespesaEnum.forValues(tipoDespesa).getNome();
    }
}
