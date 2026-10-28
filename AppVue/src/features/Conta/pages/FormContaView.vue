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

const { carregarTipos, initialValues, resolver, salvar, carregando, errorValid, tipos, formRef } = formContaView();

onMounted(() => {
    carregarTipos()
});
</script>
<template>
    <Form ref="formRef" v-slot="$form" :resolver="resolver" :initialValues="initialValues" @submit="salvar"
        :key="props.idEdicao ?? 'novo'" v-if="!carregando">
        <div class="flex gap-5 pl-15 pt-3">
            <div class="flex flex-col gap-3">
                <Label class="w-30">Código de Barras:</Label>
                <InputText v-mask="'999999999999999999999999999999999999999999999999'" name="codigoBarras" />
                <Message v-if="$form?.codigoBarras?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.codigoBarras.error?.message }}
                </Message>


                <Label class="w-25">Tipo Conta:</Label>
                <Select name="tipoConta" :options="tipos" optionLabel="nome" :filter="true"
                    placeholder="selecione uma conta" class="w-full" scrollHeight="40rem" />
                <Message v-if="$form?.tipoConta?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.tipoConta.error?.message }}
                </Message>

                <div class="flex flex-row gap-3">
                    <Label class="basis-1/2">Emissão:</Label>
                    <Label class="basis-1/2">Vencimento:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <InputDate name="emissao" class="w-full" />
                    <InputDate name="vencimento" class="w-full" />
                </div>
                <div class="flex flex-row gap-3">
                    <Message v-if="$form?.emissao?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.emissao.error?.message }}
                    </Message>
                    <Message v-if="$form?.vencimento?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.vencimento.error?.message }}
                    </Message>
                </div>

                <div class="flex flex-row gap-3">
                    <Label class="basis-1/2">Parcelas:</Label>
                    <Label class="basis-1/2">Valor:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <InputText v-mask="'99/999'" name="parcelas" />
                    <InputCurrency class="w-full" name="valor" />
                </div>
                <div class="flex flex-row gap-3">
                    <Message v-if="$form?.parcelas?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.parcelas.error?.message }}
                    </Message>
                    <Message v-if="$form?.valor?.invalid" severity="error" size="small" variant="simple" class="w-full">
                        {{ $form.valor.error?.message }}
                    </Message>
                </div>

                <Divider type="dashed">
                    <code class="uppercase text-xs">Pagamento</code>
                </Divider>

                <Label class="w-25">Forma Pgto:</Label>
                <SelectFormaPagamento name="formaPagamento" class="w-full" />

                <div class="flex flex-row gap-3">
                    <Label class="basis-1/2">Data pgto:</Label>
                    <Label class="basis-1/2">Valor pago:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <InputDate name="dataPagamento" class="w-full" />
                    <InputCurrency class="w-full" name="valorPago" />
                </div>

                <Divider type="dashed"></Divider>
                <Label class="w-25">Observação:</Label>
                <FormField name="observacao" v-slot="$field">
                    <Textarea rows="4" cols="41" class="bg-black w-full" v-bind="$field.props"
                        inputId="observacao"></Textarea>
                </FormField>

                <Message v-if="errorValid" severity="error" size="small" variant="simple" class="w-full">
                        {{ errorValid }}
                </Message>

                <Button type="submit" class="w-full mt-5">
                    <Save />Salvar
                </Button>

            </div>

        </div>
    </Form>
    <Toast />
</template>
