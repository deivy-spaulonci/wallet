<script lang="ts" setup>
import { onMounted } from 'vue';
import { formContaView } from './FormContaView';
import { Form, FormField } from '@primevue/forms'
import InputDate from '@/components/shared/InputDate.vue';
import InputCurrency from '@/components/shared/InputCurrency.vue';
import SelectFormaPagamento from '@/components/shared/SelectFormaPagamento.vue';
import Save from "@primeicons/vue/save";

const props = defineProps<{
    idEdicao?: string
}>()

const emit = defineEmits<{
  closeDialog: []
}>()

const { carregarTipos, initialValues, resolver, salvar, carregando, errorValid, tipos, formRef, checkEdicao } = formContaView(props.idEdicao, emit);

onMounted(() => {
    carregarTipos(), checkEdicao()
});
</script>
<template>
    <Form ref="formRef" v-slot="$form" :resolver="resolver" :initialValues="initialValues" @submit="salvar"
        :key="props.idEdicao ?? 'novo'" v-if="!carregando">
        <div class="flex gap-5 pl-7 pt-3">
            <div class="flex flex-col gap-3 w-220 rounded-lg bg-surface-100 dark:bg-surface-800 p-5">

                <div class="flex flex-row gap-3">
                    <Label class="basis-1/2">Código de Barras:</Label>
                    <Label class="basis-1/2">Tipo Conta:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <InputText v-mask="'999999999999999999999999999999999999999999999999'" name="codigoBarras"  class="basis-1/2"/>
                    <Select name="tipoConta" :options="tipos" optionLabel="nome" :filter="true"
                        placeholder="selecione uma conta" class="basis-1/2" scrollHeight="40rem"  />
                </div>
                <div class="flex flex-row gap-3">
                    <Message v-if="$form?.codigoBarras?.invalid" severity="error" size="small" variant="simple">
                        {{ $form.codigoBarras.error?.message }}
                    </Message>
                    <Message v-if="$form?.tipoConta?.invalid" severity="error" size="small" variant="simple">
                        {{ $form.tipoConta.error?.message }}
                    </Message>
                </div>


                <div class="flex flex-row gap-3">
                    <Label class="basis-1/4">Emissão:</Label>
                    <Label class="basis-1/4">Vencimento:</Label>
                    <Label class="basis-1/4">Parcelas:</Label>
                    <Label class="basis-1/4">Valor:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <InputDate name="emissao" class="w-full" />
                    <InputDate name="vencimento" class="w-full" />
                    <InputText v-mask="'99/999'" name="parcelas" class="w-full"/>
                    <InputCurrency  name="valor" class="w-full"/>
                </div>

                <div class="flex flex-row gap-3">
                    <Message v-if="$form?.emissao?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.emissao.error?.message }}
                    </Message>
                    <Message v-if="$form?.vencimento?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.vencimento.error?.message }}
                    </Message>
                    <div class="w-full" ></div>
                    <Message v-if="$form?.valor?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.valor.error?.message }}
                    </Message>
                </div>

                <Divider type="dashed">
                    <code class="uppercase text-xs">Pagamento</code>
                </Divider>

                <div class="flex flex-row gap-3">
                    <Label class="basis-2/4">Forma Pgto:</Label>
                    <Label class="basis-1/4">Data pgto:</Label>
                    <Label class="basis-1/4">Valor pago:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <SelectFormaPagamento name="formaPagamento" class="basis-2/4" />
                    <InputDate name="dataPagamento" class="basis-1/4" />
                    <InputCurrency  name="valorPago" class="basis-1/4" />
                </div>

                <Divider type="dashed"></Divider>
                <Label class="w-25">Observação:</Label>
                <FormField name="observacao" v-slot="$field">
                    <Textarea rows="4" cols="41" class="bg-black w-1/2" v-bind="$field.props"
                        inputId="observacao"></Textarea>
                </FormField>

                <Message v-if="errorValid" severity="error" size="small" variant="simple" class="w-full">
                        {{ errorValid }}
                </Message>

                <Button type="submit" class="w-3xs m-auto p-0">
                    <Save />Salvar
                </Button>

            </div>

        </div>
    </Form>
    <Toast />
</template>
