package com.br.wallet.entity.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "list_despesas_view", schema = "public")
@Immutable
public class ListDespesasView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "tipo_despesa")
    private String tipoDespesa;
    @Column(name = "fornecedor_uuid")
    private UUID fornecedorUuid;
    private String fornecedor;
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
    @Column(name = "fp_uuid")
    private UUID formaPagamentoUuid;
    @Column(name = "fp_instituicao")
    private String formaPagamentoInstituicao;
    @Column(name = "fp_modalidade")
    private String formaPagamentoModalidade;
    private BigDecimal valor;
    private String observacao;
}
