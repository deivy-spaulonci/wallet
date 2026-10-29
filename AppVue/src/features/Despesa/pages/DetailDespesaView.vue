<script lang="ts" setup>
import { DateUtil } from '@/core/util/DateUtil';
import { FormatUtil } from '@/core/util/FormatUtil';
import type { Despesa } from '../model/Despesa';

defineProps<{
  despesaDetail: Despesa;
}>();
</script>

<template>
<div class="flex flex-col gap-3 p-3 rounded-lg bg-surface-100 dark:bg-surface-800" v-if="despesaDetail">
  <Divider type="dashed">
    <code class="uppercase text-xs">Detalhes</code>
  </Divider>
  <span>Tipo Despesa :
    <span class="italic text-green-400">{{ despesaDetail.tipoDespesa }}</span>
  </span>
  <span>Fornecedor :
    <span class="italic text-green-400">{{ despesaDetail.fornecedor }}</span>
  </span>
  <span>Data Pagamento :
    <span class="italic text-green-400">{{ DateUtil.toFormDate(despesaDetail.dataPagamento+'') }}</span>
  </span>
  <span>Forma Pagamento :
    <span class="italic text-green-400">{{ despesaDetail.formaPagamento }}</span>
  </span>
  <span>Valor :
    <span class="italic text-green-400">{{ FormatUtil.floatToNumberBR(despesaDetail.valor) }}</span>
  </span>
  <span>Observação :
    <span class="italic text-green-400">{{ despesaDetail.observacao }}</span>
  </span>
</div>
</template>
