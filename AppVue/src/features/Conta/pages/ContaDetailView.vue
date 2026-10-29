<script lang="ts" setup>
import { DateUtil } from '@/core/util/DateUtil';
import { FormatUtil } from '@/core/util/FormatUtil';
import type { Conta } from '../model/Conta';

defineProps<{
  contaDetail: Conta;
}>();

</script>
<template>
<div class="flex flex-col gap-2 p-2 rounded-lg bg-surface-100 dark:bg-surface-800" v-if="contaDetail">
    <Divider type="dashed">
        <code class="uppercase text-xs">Detalhes</code>
    </Divider>
    <span>
        <span>Código de Barras: </span>
        <span class="italic text-green-400 break-all">{{ contaDetail.codigoBarras }}</span>
    </span>
    <span>
        <span>Tipo de Conta: </span>
        <span class="italic text-green-400 break-all">{{ contaDetail.tipoConta.nome }}</span>
    </span>
    <span class="flex flex-row gap-2">
        <span>Emissão: </span>
        <span class="italic text-green-400">{{ DateUtil.toFormDate(contaDetail.emissao+'') }}</span>
        <span>Vencimento: </span>
        <span class="italic text-green-400">{{ DateUtil.toFormDate(contaDetail.vencimento+'') }}</span>
    </span>
    <span class="flex flex-row gap-2">
        <span>Parcelas:</span>
        <span class="italic text-green-400">
            {{ contaDetail.parcela }} /{{ contaDetail.totalParcela }}
        </span>
        <span>Valor: </span>
        <span class="italic text-green-400">
            {{ FormatUtil.floatToNumberBR(contaDetail.valor)}}
        </span>
    </span>

    <Divider type="dashed" v-if="contaDetail.formaPagamento">
        <code class="uppercase text-xs">Pagamento</code>
    </Divider>
    <span class="flex flex-row gap-2" v-if="contaDetail.formaPagamento">
        <span>Forma pgto: </span>
        <span class="italic text-green-400">
            {{ contaDetail.formaPagamentoDesc }}
        </span>
    </span>
    <span class="flex flex-row gap-2" v-if="contaDetail.formaPagamento">
        <span>Valor pago: </span>
        <span class="italic text-green-400">
            {{ FormatUtil.floatToNumberBR(contaDetail.valorPago) }}
        </span>
        <span>Data pagto: </span>
        <span class="italic text-green-400">
            {{ DateUtil.toFormDate(contaDetail.dataPagamento+'') }}
        </span>
    </span>
    <Divider type="dashed" v-if="contaDetail.formaPagamento" />

    <span>
        <span>Observação :</span>
        <span class="italic text-green-400">
            {{ contaDetail.observacao }}</span
        >
    </span>
</div>
</template>
