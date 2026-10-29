<script setup lang="ts">
import { formDespesaView } from './FormDespesaView';
import { Form, FormField } from '@primevue/forms'
import Save from "@primeicons/vue/save";
import InputCurrency from "@/components/shared/InputCurrency.vue";
import InputDate from "@/components/shared/InputDate.vue";
import SelectFormaPagamento from "@/components/shared/SelectFormaPagamento.vue";

const props = defineProps<{
  idEdicao?: string
}>()

const emit = defineEmits<{
  closeDialog: []
}>()

const { selectedTipoDespesa, tipos, fornecedores, initialValues, carregando, formRef,
  resolver, salvar, search, carregarTipos, onMounted, checkEdicao } = formDespesaView(props.idEdicao, emit);

onMounted(() => {
  carregarTipos(), checkEdicao()
});
</script>

<template>
  <Form ref="formRef" v-slot="$form" :resolver="resolver" :initialValues="initialValues" @submit="salvar"
    :key="props.idEdicao ?? 'novo'" v-if="!carregando" class=" ">

    <div class="flex gap-5 p-10 rounded-lg bg-surface-100 dark:bg-surface-800 w-120">
      <div class="flex flex-col gap-3">

        <Label class="w-25">Fornecedor:</Label>
        <AutoComplete :suggestions="fornecedores" @complete="search" optionLabel="nome" name="fornecedor"
          placeholder="fornecedor" inputClass="w-full" class="w-100" emptySearchMessage="nenhum fornecedor encontrado"
          emptySelectionMessage="nenhum fornecedor encontrado" scrollHeight="40rem" />
        <Message v-if="$form?.fornecedor?.invalid" severity="error" size="small" variant="simple">
          {{ $form.fornecedor.error?.message }}
        </Message>


        <Label>Tipo Despesa:</Label>
        <Select v-model="selectedTipoDespesa" name="tipoDespesa" :options="tipos" optionLabel="nome"
          placeholder="selecione uma despesa" class="w-100" scrollHeight="40rem" />
        <Message v-if="$form?.tipoDespesa?.invalid" severity="error" size="small" variant="simple">
          {{ $form.tipoDespesa.error?.message }}
        </Message>

        <Label>Data Pagamento:</Label>
        <InputDate name="dataPagamento" class="w-full" />
        <Message v-if="$form?.dataPagamento?.invalid" severity="error" size="small" variant="simple">
          {{ $form.dataPagamento.error?.message }}
        </Message>

        <Label>Valor:</Label>
        <InputCurrency class="w-full" name="valor" />
        <Message v-if="$form?.valor?.invalid" severity="error" size="small" variant="simple">
          {{ $form.valor.error?.message }}
        </Message>

        <Label>Forma Pagamento:</Label>
        <SelectFormaPagamento name="formaPagamento" />
        <Message v-if="$form?.formaPagamento?.invalid" severity="error" size="small" variant="simple">
          {{ $form.formaPagamento.error?.message }}
        </Message>

        <Label>Observação:</Label>
        <FormField name="observacao" v-slot="$field">
          <Textarea rows="4" cols="41" class="bg-black w-full" v-bind="$field.props" inputId="observacao"></Textarea>
        </FormField>

        <Button type="submit" class="w-full mt-5">
          <Save />Salvar
        </Button>

      </div>
    </div>
  </Form>
  <Toast />

</template>

<style scoped></style>
