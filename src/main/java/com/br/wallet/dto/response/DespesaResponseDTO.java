
package com.br.wallet.dto.response;

import com.br.wallet.entity.FormaPagamento;
import com.br.wallet.entity.Pessoa;
import com.br.wallet.entity.TipoDespesaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DespesaResponseDTO(
        UUID uuid,
        TipoDespesaEnum tipoDespesa,
        PessoaResponseDTO fornecedor,
        LocalDate dataPagamento,
        FormaPagamentoResponseDTO formaPagamento,
        BigDecimal valor,
        String observacao

){

    public String getFormaPagamentoDescricao()
    {
        String forma = formaPagamento.modalidade().descricao();
        forma += formaPagamento.instituicao()==null ? "" : " - " + formaPagamento.instituicao();
        return forma;
    }
}
